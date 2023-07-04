package com.czf.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 类初始化执行顺序->BeanPostProcessor->@PostConstruct->InitializingBean
 * 这个项目中必须实现InitializingBean接口，@PostConstruct注解才生效，我也不知为啥
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements InitializingBean ,BeanPostProcessor{


    private String name;
    private Integer age;

    @PostConstruct
    public void init(){
        setName("拒北城外-徐凤年");
        setAge(300);
        System.out.println("Student @PostConstruct init ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setName("李淳罡-王仙芝");
        setAge(180);
        System.out.println("Student …… 执行了InitializingBean接口的afterPropertiesSet方法");
    }
}
