package com.czf.custom_config.annotation;

import java.lang.annotation.*;

/**
 * 自定义加载配置文件注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomConfig {
    String prefix()default  "";

//    default loading applicaiotn.properties
    String file()default  "";

}
