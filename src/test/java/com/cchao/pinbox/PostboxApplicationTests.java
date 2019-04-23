package com.cchao.pinbox;

import com.cchao.pinbox.util.Logs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostboxApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
    }


    @Test
    public void testRedis() {
        Logs.println(redisTemplate.opsForValue().get("test.group1.test_name2"));
        redisTemplate.opsForValue().append("test:group1:test_name1", "value_1");
        redisTemplate.opsForValue().set("test.group1.test_name2", "value_2");
        Assert.assertEquals(redisTemplate.opsForValue().get("test.group1.test_name2"), "value_2");
    }
}
