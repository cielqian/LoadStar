package com.ciel.loadstar.infrastructure.utils;

import com.ciel.loadstar.infrastructure.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/8/2 17:52
 */

public class SessionResourceUtil {
    public static Long getCurrentAccountId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String id = request.getHeader(Constants.Header_AccountId);
        if (StringUtils.isEmpty(id)){
            Object idAttribute = request.getAttribute(Constants.Header_AccountId);
            if (idAttribute != null){
                id = idAttribute.toString();
            }else{
                id = "0";
            }
        }

        return Long.parseLong(id);
    }

    public static void setCurrentAccountId(Long accountId){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        request.setAttribute(Constants.Header_AccountId, accountId);
    }
}
