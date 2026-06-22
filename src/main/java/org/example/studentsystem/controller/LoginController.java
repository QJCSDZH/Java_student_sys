package org.example.studentsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.studentsystem.DTO.LoginDTO;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.common.annotation.OperationLog;
import org.example.studentsystem.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studentsystem")
public class LoginController {

    private final LoginService loginService;

    /**
     * {
     *     "userName" : "123",
     *     "passWord": 1234567,
     *     "userId":7654321
     * }
    * */

    // http://127.0.0.1:8081/login
    @OperationLog("登陆")
    @PostMapping("/login")
    public PHResult<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = loginService.login(loginDTO);
        return PHResult.success(token);
    }

}
