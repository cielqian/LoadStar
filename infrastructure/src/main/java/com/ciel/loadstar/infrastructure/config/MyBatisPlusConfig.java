package com.ciel.loadstar.infrastructure.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/18 16:22
 */
@Configuration
@MapperScan("com.ciel.loadstar.*.repository")
public class MyBatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    @Profile(value = "dev")
    public PerformanceInterceptor performanceInterceptor() {return new PerformanceInterceptor();}

}
