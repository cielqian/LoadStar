package com.ciel.pocket.user;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@Import({com.ciel.pocket.infrastructure.config.CorsFilter.class,
com.ciel.pocket.infrastructure.config.FastJsonConfig.class,})
@EnableKafka
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

//    @Bean(name = "hystrixRegistrationBean")
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(
//                new HystrixMetricsStreamServlet(), "/hystrix.stream");
//        registration.setName("hystrixServlet");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
}
