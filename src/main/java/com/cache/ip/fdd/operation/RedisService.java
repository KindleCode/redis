package com.cache.ip.fdd.operation;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MrCai
 * @date 2019/09/24
 */
public class RedisService extends CacheBaseCommand implements CacheString, CacheList, CacheSet, CacheZSet{

    @Override
    public Boolean exists(String key) {

        return null;
    }

    @Override
    public Boolean del(String key) {
        return getRedisTemplate().delete(getRedisCacheKey(key).getKey());
    }

    @Override
    public Boolean expire(String key, Integer expire) {
        return null;
    }

    @Override
    public Boolean expireAt(String key, Long expire) {
        return null;
    }

    @Override
    public Long ttl(String key) {
        return null;
    }


    @Override
    public Long lpush(String key, String... values) {
        return null;
    }

    @Override
    public Long rpush(String key, String... values) {
        return null;
    }

    @Override
    public String lpop(String key) {
        return null;
    }

    @Override
    public String rpop(String key) {
        return null;
    }

    @Override
    public Long llen(String key) {
        return null;
    }

    @Override
    public String lindex(String key, Long index) {
        return null;
    }

    @Override
    public String lset(String key, Long index, String value) {
        return null;
    }

    @Override
    public List<String> lrange(String key, Long start, Long stop) {
        return null;
    }

    @Override
    public String ltrim(String key, Long start, Long stop) {
        return null;
    }

    @Override
    public Long sadd(String key, String... members) {
        return null;
    }

    @Override
    public Boolean sismember(String key, String member) {
        return null;
    }

    @Override
    public String spop(String key) {
        return null;
    }

    @Override
    public Long srem(String key, String... members) {
        return null;
    }

    @Override
    public Long scard(String key) {
        return null;
    }

    @Override
    public Set<String> smembers(String key) {
        return null;
    }

    @Override
    public String set(String key, String value) {
        return null;
    }

    @Override
    public Long setnx(String key, String value) {
        return null;
    }

    @Override
    public String setex(String key, Integer seconds, String value) {
        return null;
    }

    @Override
    public Object get(String key) {
        return getRedisTemplate().opsForValue().get(getRedisCacheKey(key).getKey());
    }

    @Override
    public Long strlen(String key) {
        return null;
    }

    @Override
    public Long append(String key, String value) {
        return null;
    }

    @Override
    public Long decr(String key) {
        return null;
    }

    @Override
    public Long decrBy(String key, Long count) {
        return null;
    }

    @Override
    public Long incrBy(String key, Long count) {
        return null;
    }

    @Override
    public Long incr(String key) {
        return null;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        return null;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return null;
    }

    @Override
    public Double zscore(String key, String member) {
        return null;
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        return null;
    }

    @Override
    public Long zcard(String key) {
        return null;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return null;
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return null;
    }

    @Override
    public Long zrank(String key, String member) {
        return null;
    }

    @Override
    public Long zrevrank(String key, String member) {
        return null;
    }

    @Override
    public Long zrem(String key, String... member) {
        return null;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        return null;
    }

}
