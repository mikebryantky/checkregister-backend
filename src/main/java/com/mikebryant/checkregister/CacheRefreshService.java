package com.mikebryant.checkregister;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Log
public class CacheRefreshService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void clearAll() {
        log.info("Clearing cache.");
        redisTemplate.delete(redisTemplate.keys("*"));
    }

}
