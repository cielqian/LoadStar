package com.ciel.loadstar.user.config;

import com.ciel.loadstar.user.socket.SocketHandshakeHandler;
import com.ciel.loadstar.user.socket.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/27 16:56
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                .setHandshakeHandler(new SocketHandshakeHandler())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/topic");
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new UserInterceptor());
    }

//    @EventListener
//    void handleSessionConnectedEvent(SessionConnectedEvent event) {
//        // Get Accessor
//        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
//    }

    //    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//        webSocketHandlerRegistry.addHandler(notifySocketHandler(), "/notify")
//                .setAllowedOrigins("*").withSockJS();
//    }
//
//    @Bean
//    public NotifySocketHandler notifySocketHandler(){
//        return new NotifySocketHandler();
//    }
}
