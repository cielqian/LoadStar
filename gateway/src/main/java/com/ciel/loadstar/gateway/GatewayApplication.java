package com.ciel.loadstar.gateway;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import com.github.mthizo247.cloud.netflix.zuul.web.socket.ZuulPropertiesResolver;
import com.github.mthizo247.cloud.netflix.zuul.web.socket.ZuulWebSocketProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
@EnableResourceServer
@EnableZuulWebSocket
@EnableWebSocketMessageBroker
public class GatewayApplication extends ResourceServerConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/**/api-docs",
                        "/oauth/**",
                        "/auth-service/**",
                        "/user-service/api/account").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/*.html", "/**/*.html","/swagger-resources/**").permitAll()
                .anyRequest().authenticated();
    }

    //    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }

//    @Bean
//    public ZuulPropertiesResolver zuulPropertiesResolver(
//            final ZuulProperties zuulProperties) {
//        return new ZuulPropertiesResolver() {
//            @Override
//            public String getRouteHost(ZuulWebSocketProperties.WsBrokerage wsBrokerage) {
//// 默认方法去读配置文件url属性
////return zuulProperties.getRoutes().get(wsBrokerage.getId()).getUrl();
////自己改写，可以通过注册中心读取服务地址、或者数据库等方式
//                zuulProperties.getRoutes();
//                return "http://xxx";
//            }
//        };
//    }
}
