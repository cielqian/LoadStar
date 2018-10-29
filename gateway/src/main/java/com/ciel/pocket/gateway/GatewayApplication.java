package com.ciel.pocket.gateway;

import com.ciel.pocket.gateway.filter.DefaultFilterProcessor;
import com.ciel.pocket.gateway.filter.ErrorFilter;
import com.ciel.pocket.gateway.filter.ThrowExceptionFilter;
import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
public class GatewayApplication {
    public static void main(String[] args) {
        FilterProcessor.setProcessor(new DefaultFilterProcessor());
        SpringApplication.run(GatewayApplication.class, args);
    }

//    @Bean
//    public ThrowExceptionFilter throwExceptionFilter(){
//        return new ThrowExceptionFilter();
//    }

}
