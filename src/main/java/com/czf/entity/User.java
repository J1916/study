package com.czf.entity;

import lombok.*;
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
@Component
public class User implements InitializingBean ,BeanPostProcessor{

    private String name;
    private Integer age;
    private String phone;
    private String address;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.name = "czf";
        this.age = 18;
        System.out.println("User  ……  执行了InitializingBean接口的afterPropertiesSet方法");
    }


    @PostConstruct
    public void init() {
        this.phone = "18888888888";
        this.address = "北京市天安門";
        System.out.println("执行了@PostConstruct检擦方法");


    }

}
