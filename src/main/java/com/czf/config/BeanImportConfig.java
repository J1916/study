package com.czf.config;

import com.czf.entity.BigStudent;
import com.czf.entity.Student;
import com.czf.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Student.class})
public class BeanImportConfig {

    @Bean
    public BigStudent bigStudent(){
        return BigStudent.builder().age(99)
                .tag("拒北城外-徐凤年").tag("武帝城头-王仙芝").tag("离阳城外-曹长卿")
                .tag("芦苇荡-李淳罡").tag("天门外-邓太阿").build();
    }

}
