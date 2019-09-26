package com.cache.ip.fdd;

import com.alibaba.fastjson.JSON;
import com.cache.ip.fdd.cache.support.User;
import com.cache.ip.fdd.config.CacheConfig;
import com.cache.ip.fdd.operation.RedisService;
import com.cache.ip.fdd.service.CacheService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author MrCai
 * @date 2019/08/09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CacheConfig.class})
public class InintTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private RedisService redisService;

    @Test
    public void cacheable01(){
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

    @Test
    public void redisTemplateTest(){
        redisTemplate.opsForValue().set("redis:list1", "哈哈哈￥%……&*（%^&*(");
        Object o = redisTemplate.opsForValue().get("redis:list1");
        System.out.println(1);
    }


    @Test
    public void existsTest(){
        Boolean asd = redisService.exists("redis:user");
        System.out.println(1);
    }

    @Test
    public void setTest(){
        User user = new User();
        redisService.set("redis:user","中文中%^&*()(");
    }

    @Test
    public void delTest(){
        Boolean del = redisService.del("redis:user");
        Assert.assertTrue(del);
    }

    @Test
    public void expireTest(){
        Boolean expire = redisService.expire("redis:user", 1L, TimeUnit.HOURS);
        Assert.assertTrue(expire);
    }

    @Test
    public void expireAtTest(){
        Boolean aBoolean = redisService.expireAt("redis:user", new Date(1569509428000L));
        Assert.assertTrue(aBoolean);
    }

    @Test
    public void lpushTest(){
        Long lpush = redisService.lpush("redis:list", 12);
        System.out.println(lpush);
    }

    @Test
    public void rpushTest(){
        Long rpush = redisService.rpush("redis:list", "33");
        System.out.println(rpush);
    }

    @Test
    public void lpopTest(){
        Object lpop = redisService.lpop("redis:list");
        System.out.println(lpop);
    }

    @Test
    public void rpopTest(){
        Object rpop = redisService.rpop("redis:list");
        System.out.println(rpop);
    }

    @Test
    public void lpushmoreTest(){
        Long lpush = redisService.lpush("redis:list", "11",22,"@#$%^&*(中文");
        System.out.println(lpush);
    }

    @Test
    public void lindexTest(){
        Object lindex = redisService.lindex("redis:list", 0L);
        System.out.println(lindex);

    }



}
