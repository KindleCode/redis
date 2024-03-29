package com.cache.ip.fdd.cache;

import com.cache.ip.fdd.cache.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * redis key 生成
 *
 * @author yuhao.wang3
 */
public class RedisCacheKey {

    /**
     * 前缀序列化器
     */
    private final RedisSerializer prefixSerializer1 = new StringRedisSerializer();

    /**
     * 缓存key
     */
    private final Object keyElement;

    /**
     * 缓存名称
     */
    private String cacheName = "";

    /**
     * key前缀
     */
    private String cachePrefix;

    /**
     * 是否使用缓存前缀
     */
    private boolean usePrefix = true;

    /**
     * RedisTemplate 的key序列化器
     */
    private final RedisSerializer serializer;

    /**
     * @param keyElement 缓存key
     * @param serializer RedisSerializer
     */
    public RedisCacheKey(Object keyElement, RedisSerializer serializer) {

        Assert.notNull(keyElement, "缓存key不能为NULL");
        Assert.notNull(serializer, "key的序列化器不能为NULL");
        this.keyElement = keyElement;
        this.serializer = serializer;
    }

    /**
     * 获取缓存key
     *
     * @return String
     */
    public String getKey() {

        return new String(getKeyBytes());
    }

    /**
     * 获取key的byte数组
     *
     * @return byte[]
     */
    public byte[] getKeyBytes() {

        byte[] rawKey = serializeKeyElement();
        byte[] prefix = getPrefix();
        byte[] prefixedKey = Arrays.copyOf(prefix, prefix.length + rawKey.length);
        System.arraycopy(rawKey, 0, prefixedKey, prefix.length, rawKey.length);

        return prefixedKey;
    }

    private byte[] serializeKeyElement() {

        if (serializer == null && keyElement instanceof byte[]) {
            return (byte[]) keyElement;
        }

        return serializer.serialize(keyElement.toString());
    }



    /**
     * 获取缓存前缀，默认缓存前缀是":"
     *
     * @return byte[]
     */
    public byte[] getPrefix() {
        if (StringUtils.isEmpty(cachePrefix)){
            //设置前缀异常
        }

        String concat = cachePrefix.concat(":");
        if (!StringUtils.isEmpty(cacheName)){
            concat = concat.concat(cacheName).concat(":");
        }
        return prefixSerializer1.serialize(concat);
    }

    /**
     * 设置缓存名称
     *
     * @param cacheName cacheName
     * @return RedisCacheKey
     */
    public RedisCacheKey cacheName(String cacheName) {
        this.cacheName = cacheName;
        return this;
    }

    /**
     * 设置是否使用缓存前缀，默认使用
     *
     * @param usePrefix usePrefix
     * @return RedisCacheKey
     */
    public RedisCacheKey usePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
        return this;
    }

    public RedisCacheKey cachePrefix(String cachePrefix){
        this.cachePrefix = cachePrefix;
        return this;
    }

}