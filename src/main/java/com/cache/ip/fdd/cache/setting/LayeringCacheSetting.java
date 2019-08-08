package com.cache.ip.fdd.cache.setting;

import java.util.concurrent.TimeUnit;

/**
 * @author MrCai
 * @date 2019/07/24
 */
public class LayeringCacheSetting {

    /**
     * 描述，数据监控页面使用
     */
    private String depict;

    /**
     * 缓存有效时间
     */
    private int expireTime;

    /**
     * 缓存时间单位
     */
    private TimeUnit timeUnit;

    public LayeringCacheSetting(Integer expireTime, TimeUnit timeUnit, String depict){
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
        this.depict = depict;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
