package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.service.RedisService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class LogoutServiceImp {

    private final RedisService redisService;

    public boolean logout(String token) {
        return redisService.deleteToken(token);
    }
}
