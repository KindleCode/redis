package com.cache.ip.fdd;

import com.alibaba.fastjson.JSON;
import com.cache.ip.fdd.aspect.LayeringAspect;
import com.cache.ip.fdd.cache.support.User;
import com.cache.ip.fdd.config.CacheConfig;
import com.cache.ip.fdd.service.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author MrCai
 * @date 2019/08/09
 */
// SpringJUnit4ClassRunner再Junit环境下提供Spring TestContext Framework的功能。
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration用来加载配置ApplicationContext，其中classes用来加载配置类
@ContextConfiguration(classes = {CacheConfig.class})
public class InintTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private LayeringAspect layeringAspect;

    @Test
    public void initTest(){
        User cacheService01 = cacheService.get01(19230L);
        System.out.println(JSON.toJSONString(cacheService01));
    }

    @Test
    public void cachePut01(){
        User cacheService02 = cacheService.get02(19230L);
        System.out.println(1);
    }

    @Test
    public void cacheEvit01(){
        cacheService.deleteUser(19230L);
    }
}
