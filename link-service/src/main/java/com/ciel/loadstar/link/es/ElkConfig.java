package com.ciel.loadstar.link.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/7 16:59
 */

@Data
@ConfigurationProperties(prefix = "loadstar.es")
@Configuration
public class ElkConfig {
    private String clustername;
    private String index;
    @NestedConfigurationProperty
    private List<Hosts> hosts;
}
