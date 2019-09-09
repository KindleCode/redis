package com.cache.ip.fdd.cache.stats;

import com.cache.ip.fdd.cache.support.RedisHitEnum;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Message;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * redis
 * @author MrCai
 * @date 2019/08/13
 */
public class DefaultCacheMonitor implements CacheMonitor{
    @Override
    public void monitor(String key, Object value, RedisHitEnum redisHitEnum) {

        StringBuilder hitting = new StringBuilder();
        hitting.append("[" + key +"] - [");
        hitting.append(redisHitEnum.toString() + "]");

        if (hit(value)){
            hitting.append(" - [HIT]");
        }else {
            hitting.append(" - [NOT_HIT]");
        }

        Event event = Cat.newEvent("RedisKeyHit", hitting.toString());
        event.setStatus(Message.SUCCESS);
        event.complete();
    }

    /**
     *
     * @param value
     * @return
     */
    private Boolean hit(Object value){

        if (value == null){
            return Boolean.FALSE;
        }else {
            //Collection
            if (value instanceof Collection){
                if (CollectionUtils.isEmpty((Collection<?>) value)){
                    return ((Collection<?>) value).size() > 0;
                }
            }
            //map
            if (value instanceof Map){
                return ((Map) value).size() > 0;
            }
        }
        return Boolean.TRUE;
    }

}
