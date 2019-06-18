package com.ciel.loadstar.link.es;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/7 16:57
 */
@Component
public class ESRestClient {
    @Autowired
    private ElkConfig elkConfig;

    private static ESRestClient restClientUtil;

    private static RestHighLevelClient client = null;

    @PostConstruct
    public void init() {
        restClientUtil = this;
        restClientUtil.elkConfig = this.elkConfig;
    }

    public RestHighLevelClient getClient(){
        if(client != null){
            return client;
        }else {
            synchronized(ESRestClient.class) {
                HttpHost[] httpHosts = new HttpHost[restClientUtil.elkConfig.getHosts().size()];
                String username = "", password = "";
                for (int i = 0; i<httpHosts.length;i++){
                    Hosts host = restClientUtil.elkConfig.getHosts().get(i);
                    httpHosts[i] = new HttpHost(host.getIp(),Integer.parseInt(host.getPort()),host.getSchema());
                    if (StringUtils.isNotEmpty(host.getUsername())){
                        username = host.getUsername();
                    }
                    if (StringUtils.isNotEmpty(host.getPassword())){
                        password = host.getPassword();
                    }
                }
                RestClientBuilder clientBuilder = RestClient.builder(httpHosts);

                if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password) ){
                    CredentialsProvider credentialsProvider =
                            new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY,
                            new UsernamePasswordCredentials(username, password));

                    clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder ) {
                            return httpClientBuilder
                                    .setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
                            .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                                @Override
                                public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                                    return builder.setConnectTimeout(5000).setSocketTimeout(60000);
                                }
                            });
                }

                client = new RestHighLevelClient(clientBuilder);
                return client;
            }
        }
    }
}
