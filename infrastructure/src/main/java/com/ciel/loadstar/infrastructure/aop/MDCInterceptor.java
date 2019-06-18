package com.ciel.loadstar.infrastructure.aop;

import com.ciel.loadstar.infrastructure.constants.Constants;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-05-30 21:30
 * @Comment
 */
@Component
public class MDCInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accountId = request.getHeader(Constants.Header_AccountId);
        if (!"".equals(accountId)){
            MDC.put("AccountId", accountId);
        }
        return true;
    }
}
