package com.cache.ip.fdd.operation;

import java.util.List;

/**http://redisdoc.com/list/index.html
 * redis list 操作接口
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheList extends CacheKey{

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     * @param key
     * @param values
     * @return 执行 LPUSH 命令后，列表的长度
     */
    Long lpush(Object key, Object... values);


    Long lpush(Object key, Object value);

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * @param key
     * @param values
     * @return 执行 RPUSH 操作后，表的长度
     */
    Long rpush(Object key, Object... values);

    Long rpush(Object key, Object value);

    /**
     * 移除并返回列表 key 的头元素
     * @param key
     * @return 列表的头元素。 当 key 不存在时，返回 null
     */
    Object lpop(Object key);

    /**
     * 移除并返回列表 key 的尾元素
     * @param key
     * @return 列表的尾元素。 当 key 不存在时，返回 null
     */
    Object rpop(Object key);

    /**
     * 返回列表 key 的长度
     * @param key
     * @return 列表 key 的长度
     */
    Long llen(Object key);

    /**
     * 返回列表 key 中，下标为 index 的元素
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推.
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推.
     * @param key
     * @param index
     * @return 列表中下标为 index 的元素。 如果 index 参数的值不在列表的区间范围内(out of range)，返回 null
     */
    Object lindex(Object key, Long index);

    /**
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后
     * 当 pivot 不存在于列表 key 时，不执行任何操作
     * 当 key 不存在时， key 被视为空列表，不执行任何操作
     * @param key
     * @param where
     * @param pivot
     * @param value
     * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到 pivot ，返回 -1 。 如果 key 不存在或为空列表，返回 0
     */
//    Long linsert(String key, ListPosition where, String pivot, String value);

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value
     * @param key
     * @param index
     * @param value
     * @return 操作成功返回 ok ，否则返回错误信息
     */
    void lset(Object key, Long index, Object value);

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     * @param key
     * @param start
     * @param stop
     * @return 一个列表，包含指定区间内的元素
     */
    List<String> lrange(Object key, Long start, Long stop);

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * @param key
     * @param start
     * @param stop
     * @return 命令执行成功时，返回 ok
     */
    void ltrim(Object key, Long start, Long stop);

    //TODO 阻塞队列 blpop

}
