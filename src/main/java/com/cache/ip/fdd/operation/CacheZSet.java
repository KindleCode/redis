package com.cache.ip.fdd.operation;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/** http://redisdoc.com/sorted_set/index.html
 * redis zset 操作接口
 *
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheZSet extends CacheKey{

    /**
     * 将一个 member 元素及其 score 值加入到有序集 key 当中
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上
     * @param key
     * @param score
     * @param value
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
    Boolean zadd(Object key, Object value, Double score);

    /**
     * 将多个 member 元素及其 score 值加入到有序集 key 当中
     * @param key
     * @param tuples
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
    Long zadd(Object key, Set<ZSetOperations.TypedTuple<Object>> tuples);

    /**
     * 返回有序集 key 中，成员 member 的 score 值
     * @param key
     * @param value
     * @return member 成员的 score 值
     */
    Double zscore(Object key, String value);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment
     * @param key
     * @param score
     * @param value
     * @return member 成员的新 score 值
     */
    Double zincrby(Object key, String value, double score);

    /**
     * 返回有序集中的数量
     * @param key
     * @return
     */
    Long zcard(Object key);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间
     * @param key
     * @param min
     * @param max
     * @return score 值在 min 和 max 之间的成员的数量
     */
    Long zcount(Object key, double min, double max);

    /**
     * 返回有序集 key 中，指定区间内的成员,其中成员的位置按 score 值递增(从小到大)来排序
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    Set<String> zrange(Object key, long start, long end);

    /**
     * 返回有序集 key 中，指定区间内的成员,其中成员的位置按 score 值递减(从大到小)来排列
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    Set<String> zrevrange(Object key, long start, long end);

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<String> zrangeByScore(Object key, double min, double max);

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
     * @param key
     * @param max
     * @param min
     * @return
     */
    Set<String> zrevrangeByScore(Object key, double min, double max);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列
     * 排序从 0 开始
     * @param key
     * @param value
     * @return
     */
    Long zrank(Object key, Object value);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序
     * 排序从 0 开始
     * @param key
     * @param value
     * @return 所在的位置
     */
    Long zrevrank(Object key, Object value);

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * @param key
     * @param value
     * @return
     */
    Long zrem(Object key, Object... value);

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员,区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量
     */
    Long zremrangeByRank(Object key, long start, long end);

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量
     */
    Long zremrangeByScore(Object key, double start, double end);

    /**
     * 分数排序，返回分数
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<ZSetOperations.TypedTuple> zrangeByScoreWithScores(Object key, double min, double max);

    /**
     * 分数排序，返回分数
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<ZSetOperations.TypedTuple> zreverseRangeByScoreWithScores(Object key, double min, double max);

    //其余的命令 http://redisdoc.com/sorted_set/index.html

}
