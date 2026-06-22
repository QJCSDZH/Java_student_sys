package org.example.studentsystem.common.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.studentsystem.common.context.BaseContext;
import org.example.studentsystem.common.exception.BusinessException;
import org.example.studentsystem.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String header = request.getHeader("Authorization");

        // 1. token不存在
        if (header == null || header.isEmpty()) {
            throw new BusinessException("未登录");
        }

        // 2. 格式校验
        if (!header.startsWith("Bearer ")) {
            throw new BusinessException("token格式错误");
        }

        String token = header.substring("Bearer ".length());

        try {
            // 解析token
            /*
            // userId_request是controller里HttpServletRequest request的用法
            String userId_request = JwtUtil.parseToken(token);
            // 可以放入 request，后面Controller用
            request.setAttribute("userId", userId_request);
            */

            // 3. 解析JWT
            // 常规用法,给BaseContext填数据
            Claims claims = JwtUtil.getClaims(token);

            String userId = claims.getSubject();
            String userName = claims.get("userName", String.class);


            // 4. 🔥 Redis校验（关键新增）
            String redisKey = "login:token:" + token;
            String redisValue = redisService.getToken(token);
            if (redisValue == null) {
                throw new BusinessException("登录已过期或已被踢下线");
            }

            // 5. 写入ThreadLocal
            BaseContext.setUserId(Long.valueOf(userId));
            BaseContext.setUserName(userName);

        } catch (Exception e) {
            throw new BusinessException("token无效或已过期");
        }

        return true;
    }





    @Override
    public void afterCompletion(HttpServletRequest request,

                                HttpServletResponse response,

                                Object handler,

                                Exception ex) {

        try {

            BaseContext.remove();

        } finally {

            System.out.println("ThreadLocal已清理");

        }

    }

}
