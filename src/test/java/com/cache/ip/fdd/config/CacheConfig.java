package com.cache.ip.fdd.config;

import com.cache.ip.fdd.aspect.CacheAspect;
import com.cache.ip.fdd.cache.manager.CacheManager;
import com.cache.ip.fdd.cache.manager.RedisCacheManager;
import com.cache.ip.fdd.service.CacheService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Import({RedisConfig.class})
@EnableAspectJAutoProxy
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        // 开启统计功能
        redisCacheManager.setStats(true);
        redisCacheManager.setCachePrefix("cache.ip.fdd");
        return redisCacheManager;
    }

    @Bean
    public CacheAspect layeringAspect() {
        return new CacheAspect();
    }

    @Bean
    public CacheService testService() {
        return new CacheService();
    }
}
