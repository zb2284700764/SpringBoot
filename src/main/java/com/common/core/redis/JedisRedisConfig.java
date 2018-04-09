package com.common.core.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis 缓存配置类，通过 Jedis Client 实现
 */
//@Component
//@Configuration
//@EnableCaching
public class JedisRedisConfig {


    @Value("${spring.redis.database}")
    private  int database;
    @Value("${spring.redis.host}")
    private  String host;
    @Value("${spring.redis.password}")
    private  String password;
    @Value("${spring.redis.port}")
    private  int port;
    @Value("${spring.redis.timeout}")
    private  int timeout;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        // factory 中用到的 JedisPoolConfig 信息，SpringBoot 会自动加载
        JedisConnectionFactory factory = new JedisConnectionFactory();
        return factory;
    }

    @Bean
    public CacheManager cacheManager() {
        return RedisCacheManager.create(jedisConnectionFactory());
    }

    @Bean
    public RedisTemplate redisTemplate() {
        JedisConnectionFactory factory = jedisConnectionFactory();
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringSerializer);//key序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
//        template.setHashKeySerializer(stringSerializer);//Hash key序列化
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化

        // 更新一下 value 的序列化实现方式
        template.afterPropertiesSet();
        return template;
    }

}
