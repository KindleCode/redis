package com.cache.ip.fdd.operation;

/** http://redisdoc.com/string/index.html
 * redis操作string接口
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheString extends CacheKey{

    /***
     * 设置键值对
     * 如果key已经持有其他值，SET就覆写旧值，无视类型
     * @param key
     * @param value
     * @return
     */
    void set(Object key, Object value);

    /**
     * 将key的值设为value，当且仅当key不存在。
     * 若给定的key已经存在，则SETNX不做任何动作
     * @param key
     * @param value
     * @return
     */
    Boolean setnx(Object key, Object value);

    /**
     * 返回与键key相关联的字符串值
     * @param key
     * @return
     */
    Object get(Object key);

    /**
     * 如果键 key 已经存在并且它的值是一个字符串， APPEND 命令将把 value 追加到键 key 现有值的末尾
     * 如果 key 不存在， APPEND 就简单地将键 key 的值设为 value ， 就像执行 SET key value 一样
     * @param key
     * @param value
     * @return
     */
    Integer append(Object key, String value);

    /**
     * 为键 key 储存的数字值加上增量 count
     * @param key
     * @param count
     * @return
     */
    Long incrBy(Object key, Long count);


}
