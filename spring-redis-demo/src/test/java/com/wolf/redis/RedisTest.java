package com.wolf.redis;

import com.wolf.entity.User;
import com.wolf.redis.redisimpl.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private IRedisService redisService;

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
        RedisService redisService = (RedisService)context.getBean("redisService");
        redisService.addUser2Redis(users);
        System.out.println("end");
    }

    @Test
    public void test_getUsers() {
        List<User> list = redisService.getUsersFromRedis();
        System.out.println(list);
        users = redisService.qryUserForList();
        System.out.println(users);
    }

    @Test
    public void test_saveObj2Redis(){
        User user1 = new User(1, "wolf1", 21);
        redisService.saveObj2Redis(user1);
    }

    @Test
    public void test_qryObjFromRedis(){
        User u = redisService.qryObjFromRedis(1);
        System.out.println(u);
    }
}
