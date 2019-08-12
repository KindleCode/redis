package com.cache.ip.fdd.config;

import com.cache.ip.fdd.cache.serializer.FastJsonRedisSerializer;
import com.cache.ip.fdd.cache.serializer.KryoRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource({"classpath:application.properties"})
public class RedisConfig {

    @Value("${spring.redis.database:0}")
    private int database;

    @Value("${spring.redis.host:192.168.83.128}")
    private String host;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.port:6378}")
    private int port;

    @Value("${spring.redis.pool.max-idle:200}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle:10}")
    private int minIdle;

    @Value("${spring.redis.pool.max-active:80}")
    private int maxActive;

    @Value("${spring.redis.pool.max-wait:-1}")
    private int maxWait;


//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMaxWaitMillis(maxWait);
//
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
//        jedisConnectionFactory.setDatabase(database);
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setUsePool(true);
//        return jedisConnectionFactory;
//    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        ClientResources clientResources = DefaultClientResources.create();
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        builder.clientResources(clientResources);
        LettuceClientConfiguration configuration = builder.build();

        JedisPoolConfig config = new JedisPoolConfig();

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(config)
                .build();

        return new JedisConnectionFactory(new RedisStandaloneConfiguration(host, port), jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);


        KryoRedisSerializer<Object> kryoRedisSerializer = new KryoRedisSerializer<>(Object.class);

        //这个转成的最小
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class, "com.cache.ip.fdd.");

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
