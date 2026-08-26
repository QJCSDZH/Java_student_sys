package org.example.studentsystem.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public PHResult<Void> handleBusinessException(BusinessException e) {
        return PHResult.fail(e.getMessage());
    }
}
