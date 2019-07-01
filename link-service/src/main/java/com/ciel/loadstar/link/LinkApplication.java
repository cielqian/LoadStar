package com.ciel.loadstar.link;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class LinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }

//
//    @Bean
//    public SqlSessionFactory mybatisSqlSessionFactoryBean() throws Exception {
//        org.springframework.core.io.Resource resource = new ClassPathResource("repository/*.xml");
//
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setMapperLocations();
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.ciel.pocket.link.entity,com.ciel.pocket.link.dto.output");
//        return sqlSessionFactoryBean.getObject();
//    }

}
