package com.zzuzl.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void update(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public void pushList(List<String> list, String key) {
        redisTemplate.opsForList().leftPushAll(key, list);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public List<Object> getAll(long size, String key) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, 0, size);
    }
}
