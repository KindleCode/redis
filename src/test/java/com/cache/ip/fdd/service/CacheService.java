package com.cache.ip.fdd.service;

import com.cache.ip.fdd.annotation.CacheEvict;
import com.cache.ip.fdd.annotation.CachePut;
import com.cache.ip.fdd.annotation.Cacheable;
import com.cache.ip.fdd.cache.support.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author MrCai
 * @date 2019/07/23
 */
@Component
public class CacheService {

    @Cacheable(value = "user:info", key = "#userId", expireTime = 10, timeUnit = TimeUnit.HOURS,depict = "这是描述")
    public User get01(Long userId){
        User user = new User();
        user.setUserId(userId);
        user.setAge(31);
        user.setLastName(new String[]{"w", "y", "h"});
        return user;
    }

    @CachePut(value = "user:info", key = "#userId", expireTime = 10, timeUnit = TimeUnit.HOURS, depict = "put值")
    public User get02(Long userId){
        User user = new User();
        user.setUserId(userId);
        user.setAge(32);
        user.setLastName(new String[]{"w", "y", "h"});
        return user;
    }

    @CacheEvict(value = "user:info", key = "#userId")
    public void deleteUser(Long userId){
    }

}
