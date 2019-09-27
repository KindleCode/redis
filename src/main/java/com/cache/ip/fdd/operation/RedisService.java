package com.cache.ip.fdd.operation;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author MrCai
 * @date 2019/09/24
 */
public class RedisService extends CacheBaseCommand implements CacheString, CacheList, CacheSet, CacheZSet,CacheHash{

    @Override
    public Boolean exists(Object key) {
        return getRedisTemplate().hasKey(getRedisCacheKey(key).getKey());
    }

    @Override
    public Boolean del(Object key) {
        return getRedisTemplate().delete(getRedisCacheKey(key).getKey());
    }

    @Override
    public Boolean expire(Object key, Long expire, TimeUnit unit) {
        return getRedisTemplate().expire(getRedisCacheKey(key).getKey(), expire, unit);
    }

    @Override
    public Boolean expireAt(Object key, Date expire) {
        return getRedisTemplate().expireAt(getRedisCacheKey(key).getKey(), expire);
    }

    @Override
    public Long lpush(Object key, Object value) {
        return getRedisTemplate().opsForList().leftPush(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Long lpush(Object key, Object... values) {
        return  getRedisTemplate().opsForList().leftPushAll(getRedisCacheKey(key).getKey(), values);
    }

    @Override
    public Long rpush(Object key, Object value) {
        return getRedisTemplate().opsForList().rightPush(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Long rpush(Object key, Object... values) {
        return getRedisTemplate().opsForList().rightPushAll(getRedisCacheKey(key).getKey(), values);
    }

    @Override
    public void hset(Object key, Object field, Object value) {
        getRedisTemplate().opsForHash().put(getRedisCacheKey(key).getKey(), field, value);
    }

    @Override
    public Boolean hsetnx(Object key, Object field, Object value) {
        return getRedisTemplate().opsForHash().putIfAbsent(getRedisCacheKey(key).getKey(), field, value);
    }

    @Override
    public Object hget(Object key, Object field) {
        return getRedisTemplate().opsForHash().get(getRedisCacheKey(key).getKey(), field);
    }

    @Override
    public Boolean hexists(Object key, Object field) {
        return getRedisTemplate().opsForHash().hasKey(getRedisCacheKey(key).getKey(), field);
    }

    @Override
    public Long hdel(Object key, Object... fields) {
        return getRedisTemplate().opsForHash().delete(getRedisCacheKey(key).getKey(), fields);
    }

    @Override
    public void hmset(Object key, Map<Object, Object> hash) {
        getRedisTemplate().opsForHash().putAll(getRedisCacheKey(key).getKey(), hash);
    }

    @Override
    public List<Object> hmget(Object key, Collection fields) {
        return getRedisTemplate().opsForHash().multiGet(getRedisCacheKey(key).getKey(), fields);
    }

    @Override
    public Object lpop(Object key) {
        return getRedisTemplate().opsForList().leftPop(getRedisCacheKey(key).getKey());
    }

    @Override
    public Object rpop(Object key) {
        return getRedisTemplate().opsForList().rightPop(getRedisCacheKey(key).getKey());
    }

    @Override
    public Long llen(Object key) {
        return getRedisTemplate().opsForList().size(getRedisCacheKey(key).getKey());
    }

    @Override
    public Object lindex(Object key, Long index) {
        return getRedisTemplate().opsForList().index(getRedisCacheKey(key).getKey(), index);
    }

    @Override
    public void lset(Object key, Long index, Object value) {
        getRedisTemplate().opsForList().set(getRedisCacheKey(key).getKey(), index, value);
    }

    @Override
    public List lrange(Object key, Long start, Long stop) {
        return getRedisTemplate().opsForList().range(getRedisCacheKey(key).getKey(), start, stop);
    }

    @Override
    public void ltrim(Object key, Long start, Long stop) {
        getRedisTemplate().opsForList().trim(getRedisCacheKey(key).getKey(), start, stop);
    }

    @Override
    public Long sadd(Object key, Object... members) {
        return getRedisTemplate().opsForSet().add(getRedisCacheKey(key).getKey(), members);
    }

    @Override
    public Boolean sismember(Object key, Object member) {
        return getRedisTemplate().opsForSet().isMember(getRedisCacheKey(key).getKey(), member);
    }

    @Override
    public Object spop(Object key) {
        return getRedisTemplate().opsForSet().pop(getRedisCacheKey(key).getKey());
    }

    @Override
    public Long srem(Object key, Object... members) {
        return getRedisTemplate().opsForSet().remove(getRedisCacheKey(key).getKey(), members);
    }

    @Override
    public Long scard(Object key) {
        return getRedisTemplate().opsForSet().size(getRedisCacheKey(key).getKey());
    }

    @Override
    public Set<String> smembers(Object key) {
        return getRedisTemplate().opsForSet().members(getRedisCacheKey(key).getKey());
    }

    @Override
    public void set(Object key, Object value) {
         getRedisTemplate().opsForValue().set(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Boolean setnx(Object key, Object value) {
        return getRedisTemplate().opsForValue().setIfAbsent(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Object get(Object key) {
        return getRedisTemplate().opsForValue().get(getRedisCacheKey(key).getKey());
    }

    @Override
    public Integer append(Object key, String value) {
        return getRedisTemplate().opsForValue().append(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Long incrBy(Object key, Long count) {
        return getRedisTemplate().opsForValue().increment(getRedisCacheKey(key).getKey(), count);
    }

    @Override
    public Boolean zadd(Object key, Object value, Double score) {
        return getRedisTemplate().opsForZSet().add(getRedisCacheKey(key).getKey(), value, score);
    }

    @Override
    public Long zadd(Object key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        return getRedisTemplate().opsForZSet().add(getRedisCacheKey(key).getKey(), tuples);
    }

    @Override
    public Double zscore(Object key, String value) {
        return getRedisTemplate().opsForZSet().score(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Double zincrby(Object key, String value, double score) {
        return getRedisTemplate().opsForZSet().incrementScore(getRedisCacheKey(key).getKey(), value, score);
    }

    @Override
    public Long zcard(Object key) {
        return getRedisTemplate().opsForZSet().zCard(getRedisCacheKey(key).getKey());
    }

    @Override
    public Long zcount(Object key, double min, double max) {
        return getRedisTemplate().opsForZSet().count(getRedisCacheKey(key).getKey(), min, max);
    }

    @Override
    public Set<String> zrange(Object key, long start, long end) {
        return getRedisTemplate().opsForZSet().range(getRedisCacheKey(key).getKey(), start, end);
    }

    @Override
    public Set<String> zrevrange(Object key, long start, long end) {
        return getRedisTemplate().opsForZSet().reverseRange(getRedisCacheKey(key).getKey(), start, end);
    }

    @Override
    public Set<String> zrangeByScore(Object key, double min, double max) {
        return getRedisTemplate().opsForZSet().rangeByScore(getRedisCacheKey(key).getKey(), min, max);
    }

    @Override
    public Set<String> zrevrangeByScore(Object key, double min, double max) {
        return getRedisTemplate().opsForZSet().reverseRangeByScore(getRedisCacheKey(key).getKey(), min, max);
    }

    @Override
    public Long zrank(Object key, Object value) {
        return getRedisTemplate().opsForZSet().rank(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Long zrevrank(Object key, Object value) {
        return getRedisTemplate().opsForZSet().reverseRank(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Long zrem(Object key, Object... value) {
        return getRedisTemplate().opsForZSet().remove(getRedisCacheKey(key).getKey(), value);
    }

    @Override
    public Long zremrangeByRank(Object key, long start, long end) {
        return getRedisTemplate().opsForZSet().removeRange(getRedisCacheKey(key).getKey(), start, end);
    }

    @Override
    public Long zremrangeByScore(Object key, double start, double end) {
        return getRedisTemplate().opsForZSet().removeRangeByScore(getRedisCacheKey(key).getKey(), start, end);
    }

    @Override
    public Set<ZSetOperations.TypedTuple> zrangeByScoreWithScores(Object key, double min, double max){
        return getRedisTemplate().opsForZSet().rangeByScoreWithScores(getRedisCacheKey(key).getKey(), min, max);
    }

    @Override
    public Set<ZSetOperations.TypedTuple> zreverseRangeByScoreWithScores(Object key, double min, double max){
        return getRedisTemplate().opsForZSet().reverseRangeByScoreWithScores(getRedisCacheKey(key).getKey(), min, max);
    }

}
