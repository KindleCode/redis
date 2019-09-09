package com.cache.ip.fdd.cache.support;

/**
 * @author MrCai
 * @date 2019/08/13
 */
public enum RedisHitEnum {

    /***/
    CACHEABLE(1);

    private Integer code;

    RedisHitEnum(Integer code){
        this.code = code;
    }
}
