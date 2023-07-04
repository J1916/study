package com.czf.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

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
 * 系统配置类
 *
 * @author 1916
 * @create 2023-06-17 23:01
 */
@Configuration
@ConfigurationProperties(prefix="sysconfig")
//Spring Boot 默认不支持@PropertySource读取yaml 文件
@PropertySource(value = {"classpath:sysconfig.properties"},encoding = "UTF-8")//加载指定的配置文件,
@Data
@ToString
public class SysConfig {

    private List<String> ignoreUrl;

}
