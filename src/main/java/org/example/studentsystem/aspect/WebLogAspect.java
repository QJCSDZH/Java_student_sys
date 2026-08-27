package org.example.studentsystem.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.studentsystem.common.BusinessException;
import org.example.studentsystem.common.LogHelper;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

    @Pointcut("execution(* org.example.studentsystem.controller..*(..))")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        HttpServletRequest request = currentRequest();
        String traceId = MDC.get("traceId");

        log.info("""
                
                {}
                >>> 【API 请求】 {} {}{}
                    traceId : {}
                    client  : {}
                    handler : {}.{}
                    headers : {}
                    args    : {}
                {}
                """,
                LogHelper.LINE,
                request != null ? request.getMethod() : "UNKNOWN",
                request != null ? request.getRequestURI() : "unknown",
                request != null ? LogHelper.queryString(request) : "",
                traceId,
                request != null ? LogHelper.clientIp(request) : "unknown",
                className, methodName,
                request != null ? LogHelper.formatValue(LogHelper.requestHeaders(request)) : "{}",
                LogHelper.formatValue(joinPoint.getArgs()),
                LogHelper.LINE);

        try {
            Object result = joinPoint.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("""
                    
                    {}
                    <<< 【API 响应】 {}.{} | {}ms
                        result :
                    {}
                    {}
                    """,
                    LogHelper.LINE,
                    className, methodName, cost,
                    LogHelper.formatValue(result),
                    LogHelper.LINE);
            return result;
        } catch (Throwable e) {
            long cost = System.currentTimeMillis() - start;
            if (e instanceof BusinessException businessException) {
                log.warn("""
                        
                        {}
                        ⚠ 【API 业务失败】 {}.{} | {}ms
                            error :
                        {}
                        {}
                        """,
                        LogHelper.LINE,
                        className, methodName, cost,
                        LogHelper.formatValue(businessException.getMessage()),
                        LogHelper.LINE);
            } else {
                log.error("""
                        
                        {}
                        ✖ 【API 系统异常】 {}.{} | {}ms
                            error :
                        {}
                        {}
                        """,
                        LogHelper.LINE,
                        className, methodName, cost,
                        LogHelper.formatValue(e.getMessage()),
                        LogHelper.LINE,
                        e);
            }
            throw e;
        }
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
