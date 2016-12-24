package com.wolf.book.ch2.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by wolf on 16/12/23.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ElConfig.class);
        ElConfig elConfig = applicationContext.getBean(ElConfig.class);
        elConfig.outputResource();
        applicationContext.close();
    }
}
