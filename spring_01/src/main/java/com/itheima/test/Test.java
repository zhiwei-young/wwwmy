package com.itheima.test;

import com.itheima.factory.BeanFactory;
import com.itheima.service.UserService;

public class Test {

    public static void main(String[] args) {

        BeanFactory factory = new BeanFactory();
        UserService service = (UserService) factory.getBean("userService");
        service.findAll();
    }

}
