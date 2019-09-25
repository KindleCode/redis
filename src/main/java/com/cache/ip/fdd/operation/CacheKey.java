package com.cache.ip.fdd.operation;

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
    Boolean exists(String key);

    /**
     * 删除key
     * @param key
     * @return
     */
    Boolean del(String key);

    /**
     * 设置过期时间
     * @param key
     * @param expire
     * @return
     */
    Boolean expire(String key, Integer expire);

    /**
     * 时间戳设置过期时间
     * @param key
     * @param expire
     * @return
     */
    Boolean expireAt(String key, Long expire);

    /**
     * 获取key的过期时间
     * @param key
     * @return
     */
    Long ttl(String key);

}
