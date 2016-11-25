package com.wolf.redis.publish;

import com.wolf.redis.publish.publishimpl.PubRedisDaoImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhenai on 2016/11/25.
 */
public class TestRedisProduce {

    private IPubRedisDao pubRedisDao=null;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis.xml");
        pubRedisDao = (PubRedisDaoImpl) applicationContext.getBean("pubRedisDao");
    }


    @Test
    public void testPublishMessage() throws Exception {
        String msg = "Hello, Redis!";
        pubRedisDao.sendMessage("java", msg); //发布字符串消息

        Integer[] values = new Integer[]{21341,123123,12323};
        pubRedisDao.sendMessage("java", values);  //发布一个数组消息
    }
}
