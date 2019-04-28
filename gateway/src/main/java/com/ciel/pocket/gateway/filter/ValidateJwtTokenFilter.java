package com.ciel.pocket.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import com.ciel.pocket.gateway.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/3/29 16:13
 */
@Component
public class ValidateJwtTokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();

        if (!requestContext.sendZuulResponse()){
            return false;
        }

        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURI();
        String requestMethod = requestContext.getRequest().getMethod();

        // check if token validation should be enabled
        if (url.startsWith("/auth-service") || url.equals("/user-service/api/account")) {
            return false;
        }
        return true;
    }

    @Autowired
    TokenStore tokenStore;

    @Override
    public Object run() throws ZuulException {
        Authentication a = SecurityContextHolder.getContext()
                .getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) a.getDetails();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();

        Object userIdObj = additionalInformation.get("id");
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.addZuulRequestHeader(Constants.Header_AccountId, userIdObj.toString());
        return null;
    }
}
