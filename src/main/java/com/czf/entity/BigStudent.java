package com.czf.entity;

import lombok.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 类初始化执行顺序->BeanPostProcessor->@PostConstruct->InitializingBean
 * 这个项目中必须实现InitializingBean接口，@PostConstruct注解才生效，我也不知为啥
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BigStudent{


    @Builder.Default //@Builder.Default注解，对参数赋值可以初始化参数
    private String name="雪中悍刀行";

    private Integer age;

    @Singular("tag")
    List<String> tagList;


}
