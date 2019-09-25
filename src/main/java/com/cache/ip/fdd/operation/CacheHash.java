package com.cache.ip.fdd.operation;

import java.util.List;
import java.util.Map;

/** http://redisdoc.com/hash/index.html
 * redis hash 操作接口
 *
 * @author MrCai
 * @date 2019/05/13
 */
public interface CacheHash extends CacheKey{

    /**
     * 将哈希表 hash 中域 field 的值设置为 value
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hset(String key, String field, String value);

    /**
     * 将哈希表 hash 中域 多个field 的值设置为 value
     * @param key
     * @param hash
     * @return
     */
//    Long hset(String key,Map<String,String> hash);

    /**
     * 当且仅当域 field 尚未存在于哈希表的情况下， 将它的值设置为 value
     * @param key
     * @param field
     * @param value
     * @return HSETNX 命令在设置成功时返回 1 ， 在给定域已经存在而放弃执行设置操作时返回 0
     */
    Long hsetnx(String key, String field, String value);

    /**
     * HGET 命令在默认情况下返回给定域的值
     * @param key
     * @param field
     * @return 如果给定域不存在于哈希表中， 又或者给定的哈希表并不存在， 那么命令返回 null
     */
    String hget(String key, String field);

    /**
     * 检查给定域 field 是否存在于哈希表 hash 当中
     * @param key
     * @return HEXISTS 命令在给定域存在时返回 true ， 在给定域不存在时返回 false
     */
    Boolean hexists(String key, String field);

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     * @param key
     * @param fields
     * @return 被成功移除的域的数量，不包括被忽略的域
     */
    Long hdel(String key, String... fields);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param key
     * @param hash
     * @return 如果命令执行成功，返回 字符串 "OK" ，不是哈希表(hash)类型时 抛出异常
     */
    String hmset(String key, Map<String, String> hash);

    /**
     * 返回哈希表 key 中，一个或多个给定域的值
     * @param key
     * @param fields
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样
     */
    List<String> hmget(String key, String... fields);

    /**
     * 返回哈希表 key 中，所有的域和值
     * @param key
     * @return 以列表形式返回哈希表的域和域的值
     */
    Map<String, String> hgetAll(String key);
}
