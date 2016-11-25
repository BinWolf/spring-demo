package com.wolf.redis;

import com.wolf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhenai on 2016/11/23.
 */

@Service(value = "redisService")
public class UserRedisService implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String USER_KEY = "all:user";

    public void addUser2Redis(List<User> list) {
        long count = redisTemplate.opsForList().rightPushAll(USER_KEY, list);
        System.out.println(count);
    }

    public List<User> getUsersFromRedis() {
        List<User> list = redisTemplate.opsForList().range(USER_KEY, -1, 0);
        System.out.println(list.size());
        return list;
    }

}
