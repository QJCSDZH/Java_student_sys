package org.example.studentsystem.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.studentsystem.common.PHResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     系统异常
     */
    @ExceptionHandler(RuntimeException.class)
    public PHResult<?> handleRuntimeException(RuntimeException e) {
        System.out.println("进入系统异常处理器");
        e.printStackTrace();
        return PHResult.fail("系统异常,稍后再试");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public PHResult<?> handleException(BusinessException e) {
        log.info("进入业务异常处理器");
        e.printStackTrace();
        return PHResult.fail(e.getMessage());
    }


    /**
     * 校验异常
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public PHResult<?> handleValidException(MethodArgumentNotValidException e) {
        if (e.getBindingResult().getFieldErrors().isEmpty()) {
            return PHResult.fail("校验失败");
        }
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return PHResult.fail(message);
    }
}
