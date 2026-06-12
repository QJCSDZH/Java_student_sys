package org.example.studentsystem.common.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.studentsystem.common.context.BaseContext;
import org.example.studentsystem.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String header = request.getHeader("Authorization");

        if (header == null || header.isEmpty()) {
            throw new BusinessException("未登录");
        }

        if (!header.startsWith("Bearer ")) {
            throw new BusinessException("token格式错误");
        }

        String token = header.substring(7);

        try {
            // 解析token
            /*
            // userId_request是controller里HttpServletRequest request的用法
            String userId_request = JwtUtil.parseToken(token);
            // 可以放入 request，后面Controller用
            request.setAttribute("userId", userId_request);
            */

            // 常规用法,给BaseContext填数据
            Claims claims = JwtUtil.getClaims(token);

            BaseContext.setUserId(Long.valueOf(claims.getSubject()));
            BaseContext.setUserName(claims.get("userName", String.class));

        } catch (Exception e) {
            throw new RuntimeException("token无效或已过期");
        }

        return true;
    }





    @Override
    public void afterCompletion(HttpServletRequest request,

                                HttpServletResponse response,

                                Object handler,

                                Exception ex) {

        BaseContext.remove();

        System.out.println("ThreadLocal已清理");

    }

}
