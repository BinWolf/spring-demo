package com.wolf.redis.subscriber;

import java.io.Serializable;

/**
 * Created by zhenai on 2016/11/25.
 */
public interface IMessageDelegateListenter {

    void handleMessage(Serializable message);
}
