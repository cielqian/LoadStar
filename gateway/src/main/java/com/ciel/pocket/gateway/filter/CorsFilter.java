package com.ciel.pocket.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

//@Component
public class CorsFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//
//        HttpServletResponse response = ctx.getResponse();
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=UTF-8");
        return null;
    }
}
