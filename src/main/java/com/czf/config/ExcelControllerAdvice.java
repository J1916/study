package com.czf.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.czf.custom_config.annotation.EasyExcel;
import com.czf.util.EasyExcelUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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
 * @create 2023-06-29 20:54
 */
@RestControllerAdvice
public class ExcelControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        EasyExcel methodAnnotation = methodParameter.getMethodAnnotation(EasyExcel.class);
        return methodAnnotation == null ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 只支持list、Map、对象实体类导出
     *
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //获取方法返回值
        EasyExcel methodAnnotation = methodParameter.getMethodAnnotation(EasyExcel.class);
        String fileName = methodAnnotation.fileName();
        Assert.hasText(fileName, "导出文件名不能为空");
        boolean needHead = methodAnnotation.isNeedHead();
        String sheetName = methodAnnotation.sheetName();

        if (methodAnnotation.isReadTemplate()) {
            String templateFileName = methodAnnotation.templateFileName();
            Assert.hasText(templateFileName, "模版文件名不能为空");
        }


//        只支持list集合导出
        if (!(body instanceof List)) {
            return body;
        }

        SpringUtil.getBean(ExcelControllerAdvice.class);

        Type type = body.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = TypeUtil.toParameterizedType(type);

        //得到泛型里的class类型对象
        Class<?> beanClazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];

        //从上下文 获取请求/响应
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();


        try {
            if (methodAnnotation.isReadTemplate()) {
                String templateFileName = methodAnnotation.templateFileName();
                EasyExcelUtil.readTemplateExportExcel((List) body,beanClazz,fileName,templateFileName,response);
            }

            EasyExcelUtil.exportExcel((List) body, beanClazz, fileName, needHead, sheetName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }
}

