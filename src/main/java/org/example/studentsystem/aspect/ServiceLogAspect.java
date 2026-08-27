package org.example.studentsystem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.studentsystem.common.LogHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLogAspect {

    @Pointcut("execution(* org.example.studentsystem.service..*.*(..))")
    public void servicePointcut() {
    }

    @Around("servicePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("""
                    ▶ 【Service 进入】 {}.{}
                        args :
                    {}
                """,
                className, methodName,
                LogHelper.formatValue(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            long cost = System.currentTimeMillis() - start;

            log.info("""
                        ◀ 【Service 完成】 {}.{} | {}ms
                            result :
                        {}
                    """,
                    className, methodName, cost,
                    LogHelper.formatValue(result));

            return result;
        } catch (Throwable e) {
            long cost = System.currentTimeMillis() - start;
            log.warn("""
                        ✖ 【Service 异常】 {}.{} | {}ms
                            error :
                        {}
                    """,
                    className, methodName, cost,
                    LogHelper.formatValue(e.getMessage()),
                    e);
            throw e;
        }
    }
}
