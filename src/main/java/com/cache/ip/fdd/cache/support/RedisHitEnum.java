package com.cache.ip.fdd.cache.support;

/**
 * @author MrCai
 * @date 2019/08/13
 */
public enum RedisHitEnum {

    /**cacheable注解*/
    CACHEABLE("cacheable"),
    /**gey操作*/
    GET("get");

    private String operation;

    RedisHitEnum(String operation){
        this.operation = operation;
    }
}
