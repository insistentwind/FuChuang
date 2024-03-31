package com.ning.runner;

import com.ning.utils.RedisCache;
import io.lettuce.core.protocol.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisData;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: qjn
 * @create: 2024/03/28 19:45
 **/
@Order(0)
@Slf4j
@Component
public class RedisClearRunner implements CommandLineRunner {
    @Autowired
    RedisTemplate redisTemplate;
    //开机清空所有的redis缓存
    @Override
    public void run(String... args) throws Exception {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }
}