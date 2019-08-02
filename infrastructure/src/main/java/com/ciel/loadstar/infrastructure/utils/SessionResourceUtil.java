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
    public static Long currentAccountId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String id = request.getHeader(Constants.Header_AccountId);
        if (StringUtils.isNotEmpty(id)){
            return Long.parseLong(id);
        }
        return 0L;
    }
}
