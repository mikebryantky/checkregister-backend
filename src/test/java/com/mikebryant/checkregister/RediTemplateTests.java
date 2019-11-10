package com.mikebryant.checkregister;

import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckregisterBackendApplication.class)
@Log
public class RediTemplateTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheRefreshService cacheRefreshService;


    @Before
    public void setup() throws Exception {
        cacheRefreshService.clearAll();
    }

    @Test
    public void verifyRedis() {
        String key = "name";
        String sentValue = "Mike";

        redisTemplate.opsForValue().set(key, sentValue);

        String receivedValue = "" + redisTemplate.opsForValue().get("name");
        Assert.assertEquals(sentValue, receivedValue);

        Long counter = redisTemplate.opsForValue().increment("counter", 1L);
        Assert.assertEquals(new Long(1), counter);
    }

}
