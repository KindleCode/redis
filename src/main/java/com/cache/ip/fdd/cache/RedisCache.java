package com.cache.ip.fdd.cache;

import com.alibaba.fastjson.JSON;
import com.cache.ip.fdd.cache.support.NullValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis实现的二级缓存
 *
 * @author yuhao.wang
 */
public class RedisCache extends AbstractValueAdaptingCache {
    protected static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    /**
     * redis 客户端
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存有效时间,毫秒
     */
    private long expiration;

    /**
     * 前缀
     */
    private String cachePrefix = "";

    /**
     * @param name            缓存名称
     * @param redisTemplate   redis客户端   redis 客户端
     * @param expiration      key的有效时间
     * @param stats           是否开启统计模式
     */
    public RedisCache(String name, String cachePrefix, RedisTemplate<String, Object> redisTemplate, long expiration, boolean stats) {
        super(stats, name);

        Assert.notNull(redisTemplate, "RedisTemplate 不能为NULL");
        this.redisTemplate = redisTemplate;
        this.expiration = expiration;
        this.cachePrefix = cachePrefix;
    }

    @Override
    public RedisTemplate<String, Object> getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public Object get(Object key) {
        if (isStats()) {
            getCacheStats().addCacheRequestCount(1);
        }

        RedisCacheKey redisCacheKey = getRedisCacheKey(key);
        logger.debug("redis缓存 key= {} 查询redis缓存", redisCacheKey.getKey());
        return redisTemplate.opsForValue().get(redisCacheKey.getKey());
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        if (isStats()) {
            getCacheStats().addCacheRequestCount(1);
        }

        RedisCacheKey redisCacheKey = getRedisCacheKey(key);
        logger.debug("redis缓存 key= {} 查询redis缓存如果没有命中，从数据库获取数据", redisCacheKey.getKey());
        // 先获取缓存，如果有直接返回
        Object result = redisTemplate.opsForValue().get(redisCacheKey.getKey());
        if (result != null || redisTemplate.hasKey(redisCacheKey.getKey())) {
            return (T) fromStoreValue(result);
        }
        // 执行缓存方法
        return loaderAndPutValue(redisCacheKey, valueLoader, true);
    }

    @Override
    public void put(Object key, Object value) {
        RedisCacheKey redisCacheKey = getRedisCacheKey(key);
        logger.debug("redis缓存 key= {} put缓存，缓存值：{}", redisCacheKey.getKey(), JSON.toJSONString(value));
        putValue(redisCacheKey, value);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        logger.debug("redis缓存 key= {} putIfAbsent缓存，缓存值：{}", getRedisCacheKey(key).getKey(), JSON.toJSONString(value));
        Object reult = get(key);
        if (reult != null) {
            return reult;
        }
        put(key, value);
        return null;
    }

    @Override
    public void evict(Object key) {
        RedisCacheKey redisCacheKey = getRedisCacheKey(key);
        logger.info("清除redis缓存 key= {} ", redisCacheKey.getKey());
        redisTemplate.delete(redisCacheKey.getKey());
    }

    @Override
    public void clear() {
        // 必须开启了使用缓存名称作为前缀，clear才有效
        logger.info("清空redis缓存 ，缓存前缀为{}", getName());
        Set<String> keys = redisTemplate.keys(getName() + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 获取 RedisCacheKey
     *
     * @param key 缓存key
     * @return RedisCacheKey
     */
    public RedisCacheKey getRedisCacheKey(Object key) {
        return new RedisCacheKey(key, redisTemplate.getKeySerializer())
                .cacheName(getName()).cachePrefix(cachePrefix);
    }

    /**
     * 加载并将数据放到redis缓存
     */
    private <T> T loaderAndPutValue(RedisCacheKey key, Callable<T> valueLoader, boolean isLoad) {
        long start = System.currentTimeMillis();
        if (isLoad && isStats()) {
            getCacheStats().addCachedMethodRequestCount(1);
        }

        try {
            // 加载数据
            Object result = putValue(key, valueLoader.call());
            logger.debug("redis缓存 key={} 执行被缓存的方法，并将其放入缓存, 耗时：{}。数据:{}", key.getKey(), System.currentTimeMillis() - start, JSON.toJSONString(result));

            if (isLoad && isStats()) {
                getCacheStats().addCachedMethodRequestTime(System.currentTimeMillis() - start);
            }
            return (T) fromStoreValue(result);
        } catch (Exception e) {
            throw new LoaderCacheValueException(key.getKey(), e);
        }
    }

    private Object putValue(RedisCacheKey key, Object value) {
        Object result = toStoreValue(value);
        // redis 缓存不允许直接存NULL，如果结果返回NULL需要删除缓存
        if (result == null) {
            redisTemplate.delete(key.getKey());
            return result;
        }
        // 不允许缓存NULL值，删除缓存
        if (result instanceof NullValue) {
            redisTemplate.delete(key.getKey());
            return result;
        }

        // 允许缓存NULL值
        long expirationTime = this.expiration;
        // 将数据放到缓存
        redisTemplate.opsForValue().set(key.getKey(), result, expirationTime, TimeUnit.MILLISECONDS);
        return result;
    }

}
