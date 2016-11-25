package com.wolf.redis.subcriber;

import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhenai on 2016/11/25.
 */
public class TestRedisConsumer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:/spring-redis.xml");
        System.out.println("消费者1");
        while (true) { //这里是一个死循环,目的就是让进程不退出,用于接收发布的消息
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
