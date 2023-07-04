package com.czf.custom_config.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.czf.custom_config.annotation.CustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　 ┗┻┛　┗┻┛
 *
 * @author 1916
 * @create 2023-06-20 14:06
 * @desc 在bean初始化前后调用BeanPostProcessor的 前后置初始化方法
 */
@Component
@Slf4j
public class ConfigPostProcess2 implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//  1、    查找bean中有没使用@CustomConfig自定义注解
        CustomConfig annotation = AnnotationUtils.findAnnotation(bean.getClass(), CustomConfig.class);
        if(annotation != null){
            log.info("当前操作的类:{}", beanName);
//   2、加载配置文件
            Map<String ,String> map = getConfigPropertiesFromFile(annotation);
//   3、反射映射配置文件的值到bean中（属性赋值）
            bindBeanValue(bean,map);
        }
        return bean;
    }

    /**
     * 遍历配置信息，为bean属性赋值
     * @param bean
     * @param map
     */
    private void bindBeanValue(Object bean, Map<String, String> map) {
        map.forEach((key,value)->{
            setFieldValueByFieldName(bean,key,value);
        });
    }

    private void setFieldValueByFieldName(Object bean, String fieldName, String value){
        Class<?> beanClass = bean.getClass();
        Field field =  getClassField(beanClass,fieldName);
        if(field !=  null){
            //开启安全操作
            field.setAccessible(true);
            //如果不是String，那么就是int。其它类型不支持
            try {
                if(field.getType().equals(String.class)){
                    field.set(bean,value);
                }else{
                    field.set(bean,Integer.valueOf(value));
                }
            } catch (IllegalAccessException e) {
                log.info(fieldName+" 字段属性赋值（参数设置）出错");
            }
        }

    }

    /**
     * 检查字段是否存在于bean中
     * @param beanClass
     * @param fieldName
     * @return
     */
    private Field getClassField(Class<?> beanClass, String fieldName) {
        Field field = null;
        try {
            field = beanClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            //字段不存在
         log.info(fieldName+" 字段不存在");
        }
        return field;
    }


    /**
     *  从配置文件中读取配置好的键值对，并放入到Map中
     * @param annotation
     * @return
     */
    private Map<String, String> getConfigPropertiesFromFile(CustomConfig annotation) {
//       获取@annotation注解中的prefix和file参数
        String prefix = annotation.prefix();
        String fileName = annotation.file();
        Map<String, Object> map  = null;

        String defaultFileName = "application";
        String defaultProperties = "properties";
        String defaultYaml = "yml";
        String gap = ".";

        if(StrUtil.isBlank(fileName)){
            // default loading
            map = getPropertiesFromResource(defaultFileName  + gap + defaultProperties);
            if(MapUtil.isEmpty(map)){
                // default2 loading application.yml
                map = getYamlFromResource(defaultFileName  + gap + defaultYaml);
                map.put("$loadFileType",defaultYaml);
            }else{
                map.put("$loadFileType",defaultProperties);
            }
        }else{
            //1、properties
            if(StrUtil.endWith(fileName,gap + defaultProperties)){
                map = getPropertiesFromResource(fileName);
                map.put("$loadFileType",defaultProperties);
            }else if(StrUtil.endWith(fileName,gap + defaultYaml)){
            //2、yaml
                map = getYamlFromResource(fileName);
                map.put("$loadFileType",defaultYaml);
            }else{
                log.error("不支持该类型文件");
                System.exit(0);
            }
        }

        Map<String, String> resultMap  = null;
        String $loadFileType = MapUtil.getStr(map, "$loadFileType");
        if(StrUtil.equals($loadFileType,defaultProperties)){
            resultMap = configDataCleanProperties(map,prefix);
            resultMap.remove($loadFileType);
        }else{
            resultMap = configDataCleanYaml(map,prefix);
        }
        return resultMap;
    }


    /**
     * 加载properties配置文件
     * @param fileName
     * @return
     */
    private Map<String,Object> getPropertiesFromResource(String fileName) {
        Properties properties = new Properties();
        try {
            InputStream resourceAsStream = ConfigPostProcess2.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(resourceAsStream);
        } catch (Exception e) {
            log.info(fileName+"，配置文件不存在");
        }
        Map<String,Object> map = new HashMap<>();
        properties.forEach((key,value)->map.put((String)key,value));
        return  map;
    }

    /**
     * 加载yaml配置文件
     * @param fileName
     * @return
     */
    private  Map<String,Object> getYamlFromResource(String fileName) {
        Map<String,Object> map = null;
        try {
            InputStream resourceAsStream = ConfigPostProcess2.class.getClassLoader().getResourceAsStream(fileName);
            map = YamlUtil.load(resourceAsStream, Map.class);
        } catch (Exception e) {
            log.info(fileName+"，配置文件不存在");
        }
        return  map;
    }


    /**
     * Properties 配置数据清洗，截取掉key的 prefix
     * @param map
     * @param prefix
     * @return
     */
    private Map<String, String> configDataCleanProperties(Map<String, Object> map, String prefix) {
        Map<String, String> resultMap = new HashMap<>();
        map.forEach((key,value)->{
            if(key.startsWith(prefix)){
                key = key.substring(prefix.length()+1);
            }
            resultMap.put(key,(String)value);
        });
        return resultMap;
    }


    /**
     * Yaml 配置数据清洗，截取掉prefix
     * @param map
     * @param prefix
     * @return
     */
    private Map<String, String> configDataCleanYaml(Map<String, Object> map, String prefix) {
        String[] split = prefix.split("\\.");//多层级
        Map<String, Object> convertMap = map;
        for (String pre : split) {
            convertMap = MapUtil.get(convertMap, pre, Map.class,MapUtil.newHashMap());
        }
        Map<String, String> resultMap = new HashMap<>();
        convertMap.forEach((key,value)->{
            //支持Integer/String,转成String
            String val = String.valueOf(value);
            resultMap.put(key,val);
        });
        return resultMap;
    }

}
