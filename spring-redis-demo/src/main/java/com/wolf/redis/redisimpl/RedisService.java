package com.wolf.redis.redisimpl;

import com.wolf.entity.User;
import com.wolf.redis.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhenai on 2016/11/23.
 */

@Service(value = "redisService")
public class RedisService implements IRedisService {

    private static Logger log = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ListOperations<String, User> listOpt;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, Object> valOpt;

    private static final String USER_KEY = "all:user";

    private static final String SINGLE_USER = "user:id:";

    @Override
    public void saveObj2Redis(User user) {
        if (user == null) {
            return ;
        }
        String key = SINGLE_USER + user.getId();
        valOpt.set(key, user);
    }

    @Override
    public User qryObjFromRedis(int id) {
        String key = SINGLE_USER + id;
        return (User)valOpt.get(key);
    }

    /**
     * 直接保存list到缓存
     *
     * @param list
     */
    @Override
    public void addUser2Redis(List<User> list) {
        long count = redisTemplate.opsForList().rightPushAll(USER_KEY, list);
        System.out.println(count);
    }

    /**
     * 获取一个list
     *
     * @return
     */
    @Override
    public List<User> getUsersFromRedis() {
        List<User> list = redisTemplate.opsForList().range(USER_KEY, 0, redisTemplate.opsForList().size(USER_KEY));
        log.debug("size : "+list.size());
        return list;
    }

    @Override
    public List<User> qryUserForList(){
        List<User> list = listOpt.range(USER_KEY, 0, listOpt.size(USER_KEY));
        log.error("listOpt : "+list.size());
        return list;
    }
}
