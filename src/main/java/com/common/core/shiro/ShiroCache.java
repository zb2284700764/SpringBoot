package com.common.core.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

public class ShiroCache<K, V> implements Cache<K, V> {

    @Value("${redis.shiro.session.prefix}")
    private String redisShiroCache;
    private String cacheKey;
    private RedisTemplate<K, V> redisTemplate;
    @Value("${redis.session.expireTime}")
    private long globExpire = 30;


    @Override
    public V get(K k) throws CacheException {

        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
