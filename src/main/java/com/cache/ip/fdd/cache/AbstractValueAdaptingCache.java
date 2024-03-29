/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cache.ip.fdd.cache;

import com.alibaba.fastjson.JSON;
import com.cache.ip.fdd.cache.manager.CacheManager;
import com.cache.ip.fdd.cache.stats.CacheMonitor;
import com.cache.ip.fdd.cache.stats.DefaultCacheMonitor;
import com.cache.ip.fdd.cache.support.Cache;
import com.cache.ip.fdd.cache.support.NullValue;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;


/**
 * Cache 接口的抽象实现类，对公共的方法做了一写实现，如是否允许存NULL值
 * <p>如果允许为NULL值，则需要在内部将NULL替换成{@link NullValue#INSTANCE} 对象
 *  *
 * @author yuhao.wang3
 */
public abstract class AbstractValueAdaptingCache implements Cache {

    /**
     * 缓存名称
     */
    private final String name;

    /**
     * 是否开启统计功能
     */
    private boolean stats;

    /**
     * 缓存统计器
     */
    private CacheMonitor cacheMonitor;

    /**
     * 通过构造方法设置缓存配置
     *
     * @param stats           是否开启监控统计
     * @param name            缓存名称
     */
    protected AbstractValueAdaptingCache(boolean stats, String name) {
        Assert.notNull(name, "缓存名称不能为NULL");
        this.stats = stats;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        return (T) fromStoreValue(get(key));
    }

    /**
     * Convert the given value from the internal store to a user value
     * returned from the get method (adapting {@code null}).
     *
     * @param storeValue the store value
     * @return the value to return to the user
     */
    protected Object fromStoreValue(Object storeValue) {
        if (storeValue instanceof NullValue) {
            return null;
        }
        return storeValue;
    }

    /**
     * Convert the given user value, as passed into the put method,
     * to a value in the internal store (adapting {@code null}).
     *
     * @param userValue the given user value
     * @return the value to store
     */
    protected Object toStoreValue(Object userValue) {
        if (userValue == null) {
            return NullValue.INSTANCE;
        }
        return userValue;
    }


    /**
     * {@link #get(Object, Callable)} 方法加载缓存值的包装异常
     */
    public class LoaderCacheValueException extends RuntimeException {

        private final Object key;

        public LoaderCacheValueException(Object key, Throwable ex) {
            super(String.format("加载key为 %s 的缓存数据,执行被缓存方法异常", JSON.toJSONString(key)), ex);
            this.key = key;
        }

        public Object getKey() {
            return this.key;
        }
    }

    /**
     * 获取是否开启统计
     *
     * @return true：开启统计，false：关闭统计
     */
    public boolean isStats() {
        return stats;
    }

    public CacheMonitor getCacheMonitor(){
        if (cacheMonitor != null){
            return cacheMonitor;
        }
        cacheMonitor = new DefaultCacheMonitor();
        return cacheMonitor;
    }

}
