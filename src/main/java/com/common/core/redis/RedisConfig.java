package com.common.core.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashSet;
import java.util.Set;

/**
 * Redis 缓存配置类，通过 Lettuce Client 实现
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

//    @Bean
//    public CacheManager cacheManager() {
//
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
//                .RedisCacheManagerBuilder
//                .fromConnectionFactory(lettuceConnectionFactory);
////        Set<String> cacheNames = new HashSet<String>() {{
////            add("codeNameCache");
////        }};
////        builder.initialCacheNames(cacheNames);
//        return builder.build();
//    }



    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(lettuceConnectionFactory);

        // 设置 value 序列化使用的类
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 忽略json字符串中不识别的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // key 序列化使用的类
        RedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);//key序列化

//        template.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        template.setValueSerializer(new RedisObjectSerializer());//value序列化
//        template.setHashKeySerializer(stringSerializer);//Hash key序列化
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化

        // 更新一下 value 的序列化实现方式
        template.afterPropertiesSet();
        return template;
    }

//    @Override
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(target.getClass().getName());
//                stringBuilder.append(method.getName());
//                for (Object object : params) {
//                    stringBuilder.append(object.toString());
//                }
//                return stringBuilder.toString();
//            }
//
//        };
//    }

}