package com.ciel.pocket.link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import({com.ciel.pocket.infrastructure.config.FastJsonExtConfig.class,})
@ComponentScan(basePackages = {"com.ciel.pocket.link"})
@EnableCaching
public class LinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }

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
