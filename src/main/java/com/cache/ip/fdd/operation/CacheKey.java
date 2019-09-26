package com.cache.ip.fdd.operation;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis key 操作接口
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheKey {

    /**
     * 确认key是否存在
     * @param key
     * @return
     */
    Boolean exists(Object key);

    /**
     * 删除key
     * @param key
     * @return
     */
    Boolean del(Object key);

    /**
     * 设置过期时间
     * @param key
     * @param expire
     * @return
     */
    Boolean expire(Object key, Long expire, TimeUnit unit);

    /**
     * 时间戳设置过期时间
     * @param key
     * @param expire
     * @return
     */
    Boolean expireAt(Object key, Date expire);

}
