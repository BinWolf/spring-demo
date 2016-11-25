package com.wolf.redis.publish;

import java.io.Serializable;

/**
 * Created by zhenai on 2016/11/25.
 */
public interface IPubRedisDao {

    void sendMessage(String channel, Serializable message);
}
