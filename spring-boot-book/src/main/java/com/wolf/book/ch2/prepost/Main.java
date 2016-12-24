package com.wolf.book.ch2.prepost;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by wolf on 16/12/23.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrePostConfig.class); // 这里调构造初始化
        context.getBean(BeanWayService.class);
        context.getBean(JSR250WayService.class);
        context.close();
    }
}
