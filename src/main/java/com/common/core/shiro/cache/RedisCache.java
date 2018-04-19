package com.common.core.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 自定义缓存类
 *
 * @param <K>
 * @param <V>
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate<K, V> redisTemplate;
    private String cacheKey;


    public RedisCache(String cacheKey, RedisTemplate redisTemplate) {
        this.cacheKey = "shiro-cache:" + cacheKey + ":";
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K k) throws CacheException {
        // 对应的 cache key 设置失效时间
        redisTemplate.boundValueOps(getCacheKey(k)).expire(1800, TimeUnit.SECONDS);
        // 获取对应 key 的值，以绑定指定key的方式，操作具有简单值的条目
        return redisTemplate.boundValueOps(getCacheKey(k)).get();
    }

    @Override
    public V put(K k, V v) throws CacheException {
        V old = get(k);
        redisTemplate.boundValueOps(getCacheKey(k)).set(v);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete(getCacheKey(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private K getCacheKey(Object k) {
        return (K) (cacheKey + k);
    }
}