package com.ciel.pocket.user.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 20:12
 */

@Configuration
public class FastJsonConfig {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        /**
         * 创建FastJson信息转换对象
         */
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        /**
         * 创建Fastjosn对象并设定序列化规则
         */
        com.alibaba.fastjson.support.config.FastJsonConfig fastJsonConfig = new com.alibaba.fastjson.support.config.FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty
        );
        /**
         * 中文乱码解决方案
         */
        List<MediaType> mediaTypes = new ArrayList<>();
        /**
         * 设定json格式且编码为UTF-8
         */
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        /**
         * 规则赋予转换对象
         */
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        return new HttpMessageConverters(fastJsonHttpMessageConverter);

    }
}
