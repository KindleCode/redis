package com.cache.ip.fdd.cache.manager;

import com.cache.ip.fdd.cache.RedisCache;
import com.cache.ip.fdd.cache.setting.CacheSetting;
import com.cache.ip.fdd.cache.support.Cache;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yuhao.wang
 */
public class RedisCacheManager extends AbstractCacheManager {
    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        cacheManagers.add(this);
    }

    @Override
    protected Cache getMissingCache(String name, CacheSetting cacheSetting) {

        //过期毫秒值
        long expiration = cacheSetting.getTimeUnit().toMillis(cacheSetting.getExpireTime());
        return new RedisCache(name, cachePrefix, redisTemplate, expiration, getStats());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
