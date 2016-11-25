package com.wolf.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by wolf on 16/11/22.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConf.class);
        BusinessService bs = applicationContext.getBean(BusinessService.class);
//        bs.printAnnotationAopLog();
        bs.demoMethon();
        applicationContext.close();
    }
}
