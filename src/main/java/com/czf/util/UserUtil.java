package com.czf.util;

import com.czf.entity.Person;

public class UserUtil {
    private final static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void setUser(Person person){
        threadLocal.set(person);
    }


    public static Person getUser(){
        return threadLocal.get();
    }

    public static void removeUser(){
        threadLocal.remove();



    }


}
