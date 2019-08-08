package com.cache.ip.fdd.cache.manager;

import com.cache.ip.fdd.cache.RedisCache;
import com.cache.ip.fdd.cache.setting.LayeringCacheSetting;
import com.cache.ip.fdd.cache.support.Cache;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yuhao.wang
 */
public class LayeringCacheManager extends AbstractCacheManager {
    public LayeringCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        cacheManagers.add(this);
    }

    @Override
    protected Cache getMissingCache(String name, LayeringCacheSetting layeringCacheSetting) {

        //过期毫秒值
        long expiration = layeringCacheSetting.getTimeUnit().toMillis(layeringCacheSetting.getExpireTime());
        return new RedisCache(name, cachePrefix, redisTemplate, expiration, false);
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
