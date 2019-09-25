package com.cache.ip.fdd.operation;

import com.cache.ip.fdd.cache.RedisCacheKey;
import com.cache.ip.fdd.cache.manager.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author MrCai
 * @date 2019/05/26
 */
public abstract class CacheBaseCommand {

    private RedisCacheManager redisCacheManager;

    protected RedisCacheKey getRedisCacheKey(Object key) {
        return new RedisCacheKey(key, redisCacheManager.getRedisTemplate().getKeySerializer())
                .cachePrefix(redisCacheManager.getCachePrefix());
    }

    protected RedisTemplate getRedisTemplate(){
        return redisCacheManager.getRedisTemplate();
    }

    public RedisCacheManager getRedisCacheManager() {
        return redisCacheManager;
    }

    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }
}
