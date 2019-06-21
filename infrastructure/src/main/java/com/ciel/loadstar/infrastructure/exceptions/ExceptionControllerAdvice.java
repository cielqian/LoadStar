package com.ciel.loadstar.infrastructure.exceptions;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/18 16:44
 */

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    ReturnModel handleControllerException(HttpServletRequest request, Throwable ex) {
        return ApiReturnUtil.fail(ex.getMessage());
    }
}
