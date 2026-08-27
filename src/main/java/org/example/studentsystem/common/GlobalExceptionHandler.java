package org.example.studentsystem.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public PHResult<Void> handleBusinessException(BusinessException e) {
        log.warn("""
                
                {}
                ⚠ 【业务异常】 traceId={}
                    detail :
                {}
                {}
                """,
                LogHelper.LINE,
                MDC.get("traceId"),
                LogHelper.formatValue(e.getMessage()),
                LogHelper.LINE);
        return PHResult.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public PHResult<Void> handleException(Exception e) {
        log.error("""
                
                {}
                ✖ 【系统异常】 traceId={}
                    detail :
                {}
                {}
                """,
                LogHelper.LINE,
                MDC.get("traceId"),
                LogHelper.formatValue(e.getMessage()),
                LogHelper.LINE,
                e);
        return PHResult.fail("系统繁忙，请稍后重试");
    }
}
