package com.ciel.pocket.link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
@EnableFeignClients
@Import({com.ciel.pocket.infrastructure.config.CorsFilter.class,
        com.ciel.pocket.infrastructure.config.FastJsonConfig.class,})
@ComponentScan(basePackages = {"com.ciel.pocket.link"})
public class LinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }


    @Resource
    DataSource dataSource;
//
//    @Bean
//    public SqlSessionFactory mybatisSqlSessionFactoryBean() throws Exception {
//        org.springframework.core.io.Resource resource = new ClassPathResource("mapper/*.xml");
//
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setMapperLocations();
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.ciel.pocket.link.model,com.ciel.pocket.link.dto.output");
//        return sqlSessionFactoryBean.getObject();
//    }
}
