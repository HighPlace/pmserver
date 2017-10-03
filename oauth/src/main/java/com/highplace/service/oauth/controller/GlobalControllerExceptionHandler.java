package com.highplace.service.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    // 异常处理方法：
    // 根据特定的异常返回指定的 HTTP 状态码
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public RestServiceError handleValidationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : errors) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return RestServiceError.build(RestServiceError.Type.VALIDATION_ERROR, strBuilder.toString());
    }

    // 通用异常的处理，返回500
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestServiceError handleException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
