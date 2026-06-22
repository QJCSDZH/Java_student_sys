package org.example.studentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setToken(String token, String userId, Duration duration) {
        stringRedisTemplate.opsForValue().set(
                "login:token:" + token,
                userId,
                duration
        );
    }

    public String getToken(String token) {
        return stringRedisTemplate.opsForValue().get("login:token:" + token);
    }

    public boolean deleteToken(String token) {
        Boolean result = stringRedisTemplate.delete("login:token:" + token);
        return Boolean.TRUE.equals(result);
    }
}
