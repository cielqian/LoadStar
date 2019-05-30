package com.ciel.pocket.link.config;

import com.ciel.pocket.link.filter.MDCFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-05-31 05:04
 * @Comment
 */

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MDCFilter());
        super.addInterceptors(registry);
    }
}
