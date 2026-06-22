package org.example.studentsystem.service.impl;

import lombok.AllArgsConstructor;
import org.example.studentsystem.DTO.LoginDTO;
import org.example.studentsystem.common.jwt.JwtUtil;
import org.example.studentsystem.service.LoginService;
import org.example.studentsystem.service.RedisService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RedisService redisService;

    @Override
    public String login(LoginDTO loginDTO) {

        // 1. 生成 token（你可以继续用 JWT 或 UUID）
        String token = JwtUtil.generateToken(loginDTO.getUserId(), loginDTO.getUserName());

        // 2. 存 Redis（核心升级点） 7天过期
        redisService.setToken(token, loginDTO.getUserId(), Duration.ofDays(7));

        // 3. 返回 token
        return token;
    }



}
