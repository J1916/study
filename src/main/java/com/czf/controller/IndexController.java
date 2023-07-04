package com.czf.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.czf.custom_config.annotation.EasyExcel;
import com.czf.entity.BigStudent;
import com.czf.entity.Person;
import com.czf.entity.Student;
import com.czf.entity.User;
import com.czf.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
 * @create 2023-06-30 10:41
 */
@RestController
public class IndexController {

    @Autowired
    User user;

    @Autowired
    Student student;

    @Autowired
    BigStudent bigStudent;


    @EasyExcel(value = "学生名单.xlsx",isReadTemplate = true,templateFileName = "template/studentTemplate.xlsx")
    @GetMapping("/export")
    public List<Person> export(){
        List<Person> list = new ArrayList<Person>(){};
        Person person = new Person();
        person.setAge(180);
        person.setName("张三丰");
        person.setId(1);

        Person person2 = new Person();
        person2.setAge(20);
        person2.setName("王阳明");
        person2.setId(2);

        Person person3 = new Person();
        person3.setAge(30);
        person3.setName("寒武纪");
        person3.setId(3);

        list.add(person);
        list.add(person2);
        list.add(person3);
        return list;
    }


    private AtomicInteger atomicInteger = new AtomicInteger();

    @GetMapping("/")
    public void index(){
        System.out.println("================================");
        Person user = UserUtil.getUser();
        System.out.println(user);
        Person person = Person.builder().id(atomicInteger.getAndIncrement()).name("徐凤年").age(20).build();

        UserUtil.setUser(person);
        Person user1 = UserUtil.getUser();
        System.out.println(user1);

        //ThreadLocal 用完一定要清除 否则会有线程安全问题， 因为线程池中的线程是复用的
        UserUtil.removeUser(); //todo ThreadLocal 用完一定要清除 否则会有线程安全问题， 因为线程池中的线程是复用的

        System.out.println("================================");
    }
}
