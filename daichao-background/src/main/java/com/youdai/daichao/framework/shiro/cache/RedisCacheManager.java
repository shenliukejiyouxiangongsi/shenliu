package com.youdai.daichao.framework.shiro.cache;


import com.youdai.daichao.common.utils.spring.SpringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存管理器 使用redis实现
 * 
 * @author user-xmp
 */
@Service
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class RedisCacheManager implements CacheManager
{
    /**
     * 用于shiro中用到的cache
     */
    private ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    /**
     * redis cache 工具类
     */
    private RedisTemplate redisTemplate = SpringUtils.getBean("redisTemplate");

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException
    {
        Cache<K, V> cache = caches.get(name);
        if (cache == null)
        {
            synchronized (this)
            {
                cache = new RedisCache<>(3600, redisTemplate);
                caches.put(name, cache);
            }
        }
        return cache;
    }

}
