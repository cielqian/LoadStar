package com.ciel.loadstar.user.socket;

import com.ciel.loadstar.user.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
//            List headers = accessor.getNativeHeader("name");
            String username = accessor.getNativeHeader("name").get(0);
            if (StringUtils.isNotEmpty(username)){
                accessor.setUser(new SocketPrincipal(username));
//                accessor.setSessionId(username);
            }
//            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
//            if (raw instanceof Map) {
//                Object name = ((Map) raw).get("name");
//                accessor.setUser(new SocketUser(name.toString()));
//            }
        }
        return message;
    }
}
