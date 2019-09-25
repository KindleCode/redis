package com.cache.ip.fdd.operation;

import java.util.Map;
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
     * @param member
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
    Long zadd(String key, double score, String member);

    /**
     * 将一个 member 元素及其 score 值加入到有序集 key 当中
     * @param key
     * @param score
     * @param member
     * @param params nx表示如果key1不存在则插入 / xx表示如果key存在才作插入(更新)，否则不做插入 / ch表示返回被修改的元素个数
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
//    Long zadd(String key, Double score, String member, ZAddParams params);

    /**
     * 将多个 member 元素及其 score 值加入到有序集 key 当中
     * @param key
     * @param scoreMembers
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
    Long zadd(String key, Map<String, Double> scoreMembers);

    /**
     * 将多个 member 元素及其 score 值加入到有序集 key 当中
     * @param key
     * @param scoreMembers
     * @param params nx表示如果key1不存在则插入 / xx表示如果key存在才作插入(更新)，否则不做插入 / ch表示返回被修改的元素个数
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
//    Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params);

    /**
     * 返回有序集 key 中，成员 member 的 score 值
     * @param key
     * @param member
     * @return member 成员的 score 值
     */
    Double zscore(String key, String member);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment
     * @param key
     * @param score
     * @param member
     * @return member 成员的新 score 值
     */
    Double zincrby(String key, double score, String member);

    /**
     * 返回有序集中的数量
     * @param key
     * @return
     */
    Long zcard(String key);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间
     * @param key
     * @param min
     * @param max
     * @return score 值在 min 和 max 之间的成员的数量
     */
    Long zcount(String key, double min, double max);

    /**
     * 返回有序集 key 中，指定区间内的成员,其中成员的位置按 score 值递增(从小到大)来排序
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    Set<String> zrange(String key, long start, long end);

    /**
     * 返回有序集 key 中，指定区间内的成员,其中成员的位置按 score 值递减(从大到小)来排列
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    Set<String> zrevrange(String key, long start, long end);

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<String> zrangeByScore(String key, double min, double max);

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
     * @param key
     * @param max
     * @param min
     * @return
     */
    Set<String> zrevrangeByScore(String key, double max, double min);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列
     * @param key
     * @param member
     * @return
     */
    Long zrank(String key, String member);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序
     * @param key
     * @param member
     * @return
     */
    Long zrevrank(String key, String member);

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * @param key
     * @param member
     * @return
     */
    Long zrem(String key, String... member);

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员,区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量
     */
    Long zremrangeByRank(String key, long start, long end);

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量
     */
    Long zremrangeByScore(String key, double start, double end);

    //其余的命令 http://redisdoc.com/sorted_set/index.html

}
