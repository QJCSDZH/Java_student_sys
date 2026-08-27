package org.example.studentsystem.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.studentsystem.annotation.OperationLog;
import org.example.studentsystem.common.LogHelper;
import org.example.studentsystem.common.PHResult;
import org.example.studentsystem.service.OperationLogService;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperationLogService operationLogService;

    @Around("@annotation(opLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperationLog opLog) throws Throwable {
        long start = System.currentTimeMillis();
        org.example.studentsystem.entity.OperationLog record = buildBaseRecord(joinPoint, opLog);

        try {
            Object result = joinPoint.proceed();
            record.setStatus(1);
            record.setResponseBody(LogHelper.formatValue(result));
            if (result instanceof PHResult<?> phResult) {
                record.setResponseCode(phResult.getCode());
            } else {
                record.setResponseCode(200);
            }
            return result;
        } catch (Throwable e) {
            record.setStatus(0);
            record.setResponseCode(500);
            record.setErrorMsg(e.getMessage());
            record.setResponseBody(LogHelper.formatValue("失败: " + e.getMessage()));
            throw e;
        } finally {
            record.setCostMs((int) (System.currentTimeMillis() - start));
            record.setCreatedAt(LocalDateTime.now());
            operationLogService.saveAsync(record);
        }
    }

    private org.example.studentsystem.entity.OperationLog buildBaseRecord(
            ProceedingJoinPoint joinPoint, OperationLog opLog) {
        org.example.studentsystem.entity.OperationLog record =
                new org.example.studentsystem.entity.OperationLog();

        record.setTraceId(MDC.get("traceId"));
        record.setModule(opLog.module());
        record.setOperation(opLog.operation());
        record.setDescription(opLog.description());
        record.setOperatorName("anonymous");
        record.setRequestParams(LogHelper.formatValue(joinPoint.getArgs()));

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        record.setMethod(joinPoint.getTarget().getClass().getSimpleName()
                + "." + signature.getName());

        HttpServletRequest request = currentRequest();
        if (request != null) {
            record.setHttpMethod(request.getMethod());
            record.setRequestUrl(request.getRequestURL().toString()
                    + LogHelper.queryString(request));
            record.setClientIp(LogHelper.clientIp(request));
        }

        return record;
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
