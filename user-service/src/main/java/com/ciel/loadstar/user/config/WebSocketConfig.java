package com.ciel.loadstar.user.config;

import com.ciel.loadstar.user.socket.NotifySocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/27 16:56
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(notifySocketHandler(), "/notify")
                .setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public NotifySocketHandler notifySocketHandler(){
        return new NotifySocketHandler();
    }
}
