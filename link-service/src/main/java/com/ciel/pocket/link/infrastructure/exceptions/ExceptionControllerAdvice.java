package com.ciel.pocket.link.infrastructure.exceptions;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Log
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    ReturnModel handleControllerException(HttpServletRequest request, Throwable ex) {
        logger.error(ex);
        return ReturnUtils.fail(ex.getMessage());
    }
}
