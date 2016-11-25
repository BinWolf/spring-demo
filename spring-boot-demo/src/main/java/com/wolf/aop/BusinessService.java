package com.wolf.aop;

import org.springframework.stereotype.Service;

/**
 * Created by wolf on 16/11/22.
 */
@Service
public class BusinessService {

    @AopLog(print = "注解拦截嘎嘎.....")
    public void printAnnotationAopLog() {
        System.out.println("In method print ....");
    }

    public void demoMethon() {
        System.out.println("calling demo methon...");
    }

}
