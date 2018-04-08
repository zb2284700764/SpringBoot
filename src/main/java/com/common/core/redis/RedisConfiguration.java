package com.common.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis 缓存配置类
 */
@Component
public class RedisConfiguration {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
}
