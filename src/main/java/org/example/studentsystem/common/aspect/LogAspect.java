package org.example.studentsystem.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.studentsystem.DTO.LoginDTO;
import org.example.studentsystem.common.context.BaseContext;
import org.example.studentsystem.entity.OperationLogEntity;
import org.example.studentsystem.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.ObjectMapper;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.example.studentsystem.common.annotation.OperationLog;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OperationLogService operationLogService;



    @Around(
            //"execution(* org.example.studentsystem.controller.*.*(..))"
            "@annotation(operationLog)"
    )
    public Object around(ProceedingJoinPoint joinPoint, OperationLog operationLog) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        long start = System.currentTimeMillis();


        Object result = null;

        Exception exception = null;

        // =========================

        // 1. 解析请求信息（提前准备）

        // =========================

        String fullUrl = request.getRequestURL().toString();

        String queryString = request.getQueryString();

        if (queryString != null) {

            fullUrl += "?" + queryString;

        }

        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String name = headerNames.nextElement();

            headerMap.put(name, request.getHeader(name));

        }

        String token = request.getHeader("Authorization");

        try {

            // =========================

            // 2. 执行业务方法

            // =========================

            result = joinPoint.proceed();

            return result;

        } catch (Exception e) {

            exception = e;

            throw e;

        } finally {

            try {

                // =========================

                // 3. 控制台日志（保留学习用）

                // =========================

                log.info("==================================================");

                log.info("操作名称: {}", operationLog.value());

                log.info("请求URL: {}", fullUrl);

                log.info("请求方式: {}", request.getMethod());

                log.info("IP地址: {}", request.getRemoteAddr());

                log.info("调用方法: {}", joinPoint.getSignature().toShortString());

                log.info("Token: {}", token);

                log.info("请求Headers: {}", objectMapper.writeValueAsString(headerMap));

                log.info("请求参数: {}", objectMapper.writeValueAsString(getLoggableArgs(joinPoint.getArgs())));

                if (exception == null) {

                    log.info("返回结果: {}", objectMapper.writeValueAsString(result));

                } else {

                    log.info("异常信息: {}", exception.getMessage());

                }

                log.info("执行耗时: {} ms", System.currentTimeMillis() - start);

                log.info("==================================================");

                // =========================

                // 4. 组装数据库日志

                // =========================

                OperationLogEntity logEntity = new OperationLogEntity();

                logEntity.setOperation(operationLog.value());

                logEntity.setRequestUrl(fullUrl);

                logEntity.setHttpMethod(request.getMethod());

                logEntity.setClassMethod(joinPoint.getSignature().toShortString());

                logEntity.setIp(request.getRemoteAddr());

                logEntity.setToken(token);


                fillUserInfo(logEntity, joinPoint.getArgs());

                logEntity.setParams(objectMapper.writeValueAsString(getLoggableArgs(joinPoint.getArgs())));

                if (exception == null) {

                    logEntity.setResult(objectMapper.writeValueAsString(result));

                } else {

                    logEntity.setResult("ERROR: " + exception.getMessage());

                }

                logEntity.setCostTime(System.currentTimeMillis() - start);

                logEntity.setCreateTime(LocalDateTime.now());

                // =========================

                // 5. 入库

                // =========================

                operationLogService.save(logEntity);

            } catch (Exception e) {

                log.error("操作日志处理失败", e);

            }

        }

    }

    private void fillUserInfo(OperationLogEntity logEntity, Object[] args) {
        String userId = null;
        String userName = BaseContext.getUserName();

        Long contextUserId = BaseContext.getUserId();
        if (contextUserId != null) {
            userId = String.valueOf(contextUserId);
        }

        if (userId == null || userName == null) {
            for (Object arg : args) {
                if (arg instanceof LoginDTO loginDTO) {
                    if (userId == null) {
                        userId = loginDTO.getUserId();
                    }
                    if (userName == null) {
                        userName = loginDTO.getUserName();
                    }
                    break;
                }
            }
        }

        logEntity.setUserId(userId);
        logEntity.setUserName(userName);
    }

    private Object[] getLoggableArgs(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg != null)
                .filter(arg -> !(arg instanceof HttpServletRequest))
                .filter(arg -> !(arg instanceof HttpServletResponse))
                .filter(arg -> !(arg instanceof BindingResult))
                .filter(arg -> !(arg instanceof MultipartFile))
                .toArray();
    }

}
