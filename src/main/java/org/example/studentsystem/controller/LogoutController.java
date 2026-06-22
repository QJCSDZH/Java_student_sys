package org.example.studentsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.common.annotation.OperationLog;
import org.example.studentsystem.service.LoginService;
import org.example.studentsystem.service.impl.LogoutServiceImp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studentsystem")

public class LogoutController {
    private final LogoutServiceImp logoutServiceImp;

    // http://127.0.0.1:8081/studentsystem/logout
    @OperationLog("退出登陆")
    @GetMapping("/logout")
    public PHResult<?> logout( @RequestHeader("Authorization") String header) {

        String token =  header.substring("Bearer ".length());

        boolean success = logoutServiceImp.logout(token);

        if (success) {
            return PHResult.success("退出成功");
        }

        return PHResult.fail("Token不存在或已失效");

    }

}
