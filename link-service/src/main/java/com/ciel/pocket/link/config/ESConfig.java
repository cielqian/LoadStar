package com.ciel.pocket.link.config;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-05-07 05:28
 * @Comment
 */
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.ciel.pocket.link.es.repository")
//public class ESConfig  implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
//    }
//
//    @Bean
//    ReactiveElasticsearchClient client() {
//
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200", "localhost:9291")
//                .build();
//
//        return ReactiveRestClients.create(clientConfiguration);
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//    }
//}
