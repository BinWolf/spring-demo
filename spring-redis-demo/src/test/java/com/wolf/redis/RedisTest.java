package com.wolf.redis;

import com.wolf.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenai on 2016/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-redis.xml"})
public class RedisTest {

    @Resource(name = "redisService")
    private RedisService redisService;

    private static List<User> users = new ArrayList<User>();

//    @Before
    public void initUsers(){
        User user1 = new User(1, "wolf1", 21);
        User user2 = new User(2, "wolf2", 22);
        users.add(user1);
        users.add(user2);
    }

    @Test
    public void test_addUser() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-redis.xml");
        UserRedisService redisService = (UserRedisService)context.getBean("redisService");
        redisService.addUser2Redis(users);
        System.out.println("end");
    }

    @Test
    public void test_getUsers() {
        List<User> list = redisService.getUsersFromRedis();
        System.out.println(list);
    }

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, Object> stringRedisTemplate;

    @Test
    public void test_StrRedis(){
        User user1 = new User(1, "wolf1", 21);
        stringRedisTemplate.set("user:key",user1);
    }
}
