package com.wolf.book.ch2.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by wolf on 16/12/23.
 */

@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("我(applicationListener-DemoListener)接收到bean-publisher发布的消息:" + msg);
    }
}