package com.youdai.daichao.common.redis;


import com.youdai.daichao.common.utils.SerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;


    public <T> void putCache(String key, T obj) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serializeObj(obj);
        redisTemplate.opsForValue().set(key, obj);
        ;
    }

    public boolean putStringCachenx(String key, String value) {
        boolean flag = false;
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
        }
        return flag;
    }


    public <T> void putCacheWithExpireTime(String key, T obj, int timeOut) {
        try {
            redisTemplate.opsForValue().set(key, obj, timeOut, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据key取缓存数据
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getCache(String key) {
        T result = null;
        try {
            result = (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据key删除缓存数据
     *
     * @return
     * @throws Exception
     */
    public void delCache(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 查询是否有对应key
     *
     * @return
     * @throws Exception
     */
    public boolean haveCache(String key) {
        return redisTemplate.hasKey(key);
    }
}
