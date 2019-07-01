package com.ciel.loadstar.user.socket;

import org.apache.commons.lang.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/28 11:44
 */

public class UserInterceptor extends ChannelInterceptorAdapter {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String username = accessor.getNativeHeader("name").get(0);
            if (StringUtils.isNotEmpty(username)){
                accessor.setUser(new SocketPrincipal(username));
            }
        }
        return message;
    }
}
