package com.lzh.test.springaop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * @Date 2016/6/2
 * @Auther lzh
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/01")
    public void test01(){
        SynchronousQueue a = new SynchronousQueue();
        System.out.println("test01");
        List<Integer> aa = new LinkedList<Integer>();
        List<Integer> ab = new LinkedList<Integer>();
        aa.addAll(ab);
    }

    public int testInt(){
        return 1;
    }

    public static void main(String... args) throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Method method = TestController.class.getMethod("testInt");
        Class clazz = method.getReturnType();
    }
}
