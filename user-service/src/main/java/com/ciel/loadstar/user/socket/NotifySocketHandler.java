package com.ciel.loadstar.user.socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.SocketHandler;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/27 16:56
 */

public class NotifySocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
    }
}
