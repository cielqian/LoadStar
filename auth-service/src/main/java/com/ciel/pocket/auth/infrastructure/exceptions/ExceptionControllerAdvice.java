package com.ciel.pocket.auth.infrastructure.exceptions;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    ReturnModel handleControllerException(HttpServletRequest request, Throwable ex) {
        return ReturnUtils.fail(ex.getMessage());
    }
}
