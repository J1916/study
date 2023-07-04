package com.czf.custom_config.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyExcel {

    @AliasFor("fileName")
    String value() default "";

    @AliasFor("value")
    String fileName() default "";

//    是否读取模版导出
    boolean isReadTemplate() default false;

//  当isReadTemplate = true 时必须设置
//  模版文件的名称（有路径的带上路径）
    String templateFileName() default "";

//  是否需要表头
    boolean isNeedHead() default true;

//  sheetName
    String sheetName() default "";

}
