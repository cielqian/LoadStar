package com.ciel.pocket.user.infrastructure.exceptions;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    ReturnModel handleControllerException(HttpServletRequest request, Throwable ex) {
        return ReturnUtils.fail(ex.getMessage());
    }
}
