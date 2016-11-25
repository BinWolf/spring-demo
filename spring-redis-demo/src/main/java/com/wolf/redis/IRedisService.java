package com.wolf.redis;

import com.wolf.entity.User;

import java.util.List;

/**
 * Created by zhenai on 2016/11/23.
 */
public interface IRedisService {

    void addUser2Redis(List<User> list);

    List<User> getUsersFromRedis();

    List<User> qryUserForList();

    void saveObj2Redis(User user);

    User qryObjFromRedis(int id);
}
