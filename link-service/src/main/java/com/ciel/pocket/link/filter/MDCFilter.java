package com.ciel.pocket.link.filter;

import com.ciel.pocket.infrastructure.constants.Constants;
import org.apache.commons.lang.StringUtils;
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
public class MDCFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accountId = request.getHeader(Constants.Header_AccountId);
        if (StringUtils.isNotEmpty(accountId)){
            MDC.put("AccountId", accountId);
        }
        return true;
    }
}
