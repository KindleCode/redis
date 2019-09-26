package com.cache.ip.fdd.operation;

import java.util.Set;

/** http://redisdoc.com/set/index.html
 * redis结合操作接口
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheSet extends CacheKey{

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     * @param key
     * @param members
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素
     */
    Long sadd(Object key, Object... members);

    /**
     * 判断 member 元素是否集合 key 的成员
     * @param key
     * @param member
     * @return 如果 member 元素是集合的成员，返回 1 。 如果 member 元素不是集合的成员，或 key 不存在，返回 0
     */
    Boolean sismember(Object key, Object member);

    /**
     * 移除并返回集合中的一个随机元素
     * @param key
     * @return 被移除的随机元素。 当 key 不存在或 key 是空集时，返回 null
     */
    Object spop(Object key);

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * @param key
     * @param members
     * @return 被成功移除的元素的数量，不包括被忽略的元素
     */
    Long srem(Object key, Object... members);

    /**
     * 返回集合中元素的数量
     * @param key
     * @return 集合的基数。 当 key 不存在时，返回 0
     */
    Long scard(Object key);

    /** TODO 管道获取，防止量大
     * 返回集合 key 中的所有成员
     * @param key
     * @return 集合中的所有成员
     */
    Set<String> smembers(Object key);
    
}
