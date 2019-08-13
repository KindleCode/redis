package com.cache.ip.fdd.cache.support;

/**
 * @author MrCai
 * @date 2019/08/13
 */
public enum RedisHitEnum {

    /**缓存命中类型: Cacheable + 手动缓存获取*/
    CACHEABLE(1);

    private Integer code;

    RedisHitEnum(Integer code){
        this.code = code;
    }
}
