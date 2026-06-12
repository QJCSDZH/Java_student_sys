package org.example.studentsystem.service.impl;

import lombok.AllArgsConstructor;
import org.example.studentsystem.DTO.LoginDTO;
import org.example.studentsystem.common.jwt.JwtUtil;
import org.example.studentsystem.service.LoginService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Override
    public String login(LoginDTO loginDTO) {
        return JwtUtil.generateToken(loginDTO.getUserId(), loginDTO.getUserName());
    }
}
