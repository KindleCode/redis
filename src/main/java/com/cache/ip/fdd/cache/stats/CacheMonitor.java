package com.cache.ip.fdd.cache.stats;

import com.cache.ip.fdd.cache.support.RedisHitEnum;

/**
 * redis 监控接口
 *
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheMonitor {

    /**
     * 缓存监控
     * @param key
     * @param value
     */
    void monitor(String key, Object value, RedisHitEnum redisHitEnum);

}
