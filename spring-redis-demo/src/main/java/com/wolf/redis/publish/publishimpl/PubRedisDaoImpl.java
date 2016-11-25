package com.wolf.redis.publish.publishimpl;

import com.wolf.redis.publish.IPubRedisDao;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by zhenai on 2016/11/25.
 */
@Repository("pubRedisDao")
public class PubRedisDaoImpl implements IPubRedisDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void sendMessage(String channel, Serializable message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
