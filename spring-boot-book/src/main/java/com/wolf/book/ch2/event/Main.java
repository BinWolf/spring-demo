package com.wolf.book.ch2.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by wolf on 16/12/23.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        DemoPublisher publisher = context.getBean(DemoPublisher.class);
        publisher.publish("hello spring application");
        context.close();
    }
}
