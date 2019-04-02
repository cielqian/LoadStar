package com.ciel.pocket.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/4/1 16:03
 */
@Component
public class CorsPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return false;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();

        if(!response.containsHeader("Access-Control-Allow-Origin")){
            response.setHeader("Access-Control-Allow-Origin","*");
        }

        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Expose-Headers","X-forwared-port, X-forwarded-host");
        response.setHeader("Vary","Origin,Access-Control-Request-Method,Access-Control-Request-Headers");

        return null;
    }
}
