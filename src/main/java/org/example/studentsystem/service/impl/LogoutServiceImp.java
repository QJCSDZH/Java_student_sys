package org.example.studentsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.LoginDTO;
import org.example.studentsystem.service.LogoutService;
import org.example.studentsystem.service.RedisService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class LogoutServiceImp implements LogoutService {

    private final RedisService redisService;


    @Override
    public boolean logout(String token) {
        return redisService.deleteToken(token);
    }
}
