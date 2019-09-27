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
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        redisTemplate.opsForValue().set("redis:list1", "中文中你问啊……&*（^&*()");
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
        redisService.set("redis:user","");
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
        Long lpush = redisService.lpush("redis:list", "6666",9999,"这里是中文（（（**……%");
        System.out.println(lpush);
    }

    @Test
    public void lindexTest(){
        Object lindex = redisService.lindex("redis:list", 0L);
        System.out.println(lindex);

    }

    @Test
    public void lsetTest(){
        redisService.lset("redis:list",0L,"中文^&*(#￥%……&*（");
    }

    @Test
    public void lrangeTest(){
        List<String> lrange = redisService.lrange("redis:list", 0L, 3L);
        System.out.println(1);
    }

    @Test
    public void ltrimTest(){
        redisService.ltrim("redis:list",0L,1L);
    }

    @Test
    public void saddTest(){
        Long a = redisService.sadd("redis:set", new User(), "好啊", "asd88^^%……&*（&￥");
        System.out.println(a);
    }

    @Test
    public void sismemberTest(){
        Boolean sismember = redisService.sismember("redis:set", "好啊");
        Assert.assertTrue(sismember);
    }

    @Test
    public void spopTest(){
        Object spop = redisService.spop("redis:set");
        System.out.println(spop);
    }

    @Test
    public void sremTest(){
        Long a = redisService.srem("redis:set", "好啊");
        System.out.println(a);
    }

    @Test
    public void scardTest(){
        Long scard = redisService.scard("redis:set");
        System.out.println(scard);
    }

    @Test
    public void smembersTest(){
        Set<String> smembers = redisService.smembers("redis:set");
        System.out.println(smembers);
    }

    @Test
    public void setnxTest(){
        Boolean setnx = redisService.setnx("redis:value", "中文&*（）（（（");
        System.out.println(setnx);
    }

    @Test
    public void getTest(){
        Object o = redisService.get("redis:value");
        System.out.println(o);
    }

    @Test
    public void appendTest(){
        Integer a = redisService.append("redis:value", "qwqwqwqw这是添加的文字");
        System.out.println(1);
    }

    @Test
    public void incrByTest(){
        Long aLong = redisService.incrBy("redis:value", 79L);
        System.out.println(aLong);
    }

    @Test
    public void zaddTest(){
        Boolean zadd = redisService.zadd("redis:zset", "小明", 79.6);
        System.out.println(zadd);
    }

    @Test
    public void zaddmoreTest(){

        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-5",9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("zset-6",19.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        Long zadd = redisService.zadd("redis:zset", tuples);
        System.out.println(zadd);
    }

    @Test
    public void zscoreTest(){
        Double zscore = redisService.zscore("redis:zset", "小明");
        System.out.println(1);
    }

    @Test
    public void zincrbyTest(){
        Double zincrby = redisService.zincrby("redis:zset", "zset-6", 10.1);
        System.out.println(zincrby);
    }

    @Test
    public void zcardTest(){
        Long zcard = redisService.zcard("redis:zset");
        System.out.println(zcard);
    }

    @Test
    public void zcountTest(){
        Long zcount = redisService.zcount("redis:zset", 11, 1000);
        System.out.println(zcount);
    }

    @Test
    public void zrangeTest(){
        Set<String> zrange = redisService.zrange("redis:zset", 0, 2);
        System.out.println(zrange);
    }

    @Test
    public void zrevrangeTest(){
        Set<String> zrange = redisService.zrevrange("redis:zset", 0, 2);
        System.out.println(zrange);
    }

    @Test
    public void zrangeByScoreTest(){
        Set<String> strings = redisService.zrangeByScore("redis:zset", 10, 100);
        System.out.println(strings);
    }

    @Test
    public void zrevrangeByScoreTest(){
        Set<String> strings = redisService.zrevrangeByScore("redis:zset", 0, 200);
        System.out.println(strings);
    }

    @Test
    public void zrankTest(){
        Long zrank = redisService.zrank("redis:zset", "zset-5");
        System.out.println(zrank);
    }

    @Test
    public void zrevrankTest(){
        Long zrevrank = redisService.zrevrank("redis:zset", "zset-5");
        System.out.println(zrevrank);
    }

    @Test
    public void zremTest(){
        Long zrem = redisService.zrem("redis:zset", "zset-6");
        System.out.println(zrem);
    }

    @Test
    public void zremrangeByRankTest(){
        Long aLong = redisService.zremrangeByRank("redis:zset", 0, 3);
        System.out.println(aLong);
    }

    @Test
    public void zremrangeByScoreTest(){
        Long aLong = redisService.zremrangeByScore("redis:zset", 0, 10);
        System.out.println(aLong);
    }

    @Test
    public void zrangeByScoreWithScoresTest(){
        Set<ZSetOperations.TypedTuple> typedTuples = redisService.zrangeByScoreWithScores("redis:zset", 0, 100);
        System.out.println(typedTuples);
    }

    @Test
    public void zreverseRangeByScoreWithScoresTest(){
        Set<ZSetOperations.TypedTuple> typedTuples = redisService.zreverseRangeByScoreWithScores("redis:zset", 0, 100);
        System.out.println(typedTuples);
    }


}
