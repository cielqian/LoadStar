package com.ciel.loadstar.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019/4/3 06:04
 * @Comment
 */
@Component
public class OAuthErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getResponse().getStatus() == 401;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
//
//        ctx.setSendZuulResponse(false);
//        ctx.setResponseStatusCode(401);

        return null;
    }
}
