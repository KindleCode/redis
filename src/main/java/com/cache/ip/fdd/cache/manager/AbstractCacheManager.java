package com.cache.ip.fdd.cache.manager;

import com.cache.ip.fdd.cache.setting.LayeringCacheSetting;
import com.cache.ip.fdd.cache.stats.CacheStatsInfo;
import com.cache.ip.fdd.cache.support.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 公共的抽象 {@link CacheManager} 的实现.
 *
 * @author yuhao.wang3
 */
public abstract class AbstractCacheManager implements CacheManager, InitializingBean, DisposableBean, BeanNameAware {
    private Logger logger = LoggerFactory.getLogger(AbstractCacheManager.class);

    /**
     * 缓存容器
     * 外层key是cache_name
     */
    private final ConcurrentMap<String, Cache> cacheContainer = new ConcurrentHashMap<>(16);

    /**
     * 缓存名称容器
     */
    private volatile Set<String> cacheNames = new LinkedHashSet<>();

    /**
     * CacheManager 容器
     */
    static Set<AbstractCacheManager> cacheManagers = new LinkedHashSet<>();

    /**
     * 是否开启统计
     */
    private boolean stats = true;

    /**
     * 缓存前缀
     */
    protected String cachePrefix;

    /**
     * redis 客户端
     */
    RedisTemplate<String, Object> redisTemplate;

    public static Set<AbstractCacheManager> getCacheManager() {
        return cacheManagers;
    }

    @Override
    public Collection<Cache> getCache(String name) {
       return null;
    }

    @Override
    public Cache getCache(String name, LayeringCacheSetting layeringCacheSetting) {

        Cache cache = cacheContainer.get(name);
        //暂时只有redis缓存
        if (cache != null){
            return cache;
        }
        Cache missingCache = getMissingCache(name, layeringCacheSetting);
        cacheContainer.put(name, missingCache);
        return missingCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

    /**
     * 更新缓存名称容器
     *
     * @param name 需要添加的缓存名称
     */
    private void updateCacheNames(String name) {
        cacheNames.add(name);
    }


    /**
     * 获取Cache对象的装饰示例
     *
     * @param cache 需要添加到CacheManager的Cache实例
     * @return 装饰过后的Cache实例
     */
    protected Cache decorateCache(Cache cache) {
        return cache;
    }

    /**
     * 根据缓存名称在CacheManager中没有找到对应Cache时，通过该方法新建一个对应的Cache实例
     *
     * @param name                 缓存名称
     * @param layeringCacheSetting 缓存配置
     * @return {@link Cache}
     */
    protected abstract Cache getMissingCache(String name, LayeringCacheSetting layeringCacheSetting);

    /**
     * 获取缓存容器
     *
     * @return 返回缓存容器
     */
    public ConcurrentMap<String, Cache> getCacheContainer() {
        return cacheContainer;
    }



    @Override
    public void afterPropertiesSet() throws Exception {

//        BeanFactory.getBean(StatsService.class).setCacheManager(this);
//        if (getStats()) {
//            // 采集缓存命中率数据
//            BeanFactory.getBean(StatsService.class).syncCacheStats();
//        }
    }

    @Override
    public List<CacheStatsInfo> listCacheStats(String cacheName) {
//        return BeanFactory.getBean(StatsService.class).listCacheStats(cacheName);
        return null;
    }

    @Override
    public void resetCacheStat() {
//        BeanFactory.getBean(StatsService.class).resetCacheStat();
    }

    @Override
    public void setBeanName(String name) {
    }

    @Override
    public void destroy() throws Exception {
//        BeanFactory.getBean(StatsService.class).shutdownExecutor();
    }

    public boolean getStats() {
        return stats;
    }

    public void setStats(boolean stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getCachePrefix() {
        return cachePrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        this.cachePrefix = cachePrefix;
    }
}
