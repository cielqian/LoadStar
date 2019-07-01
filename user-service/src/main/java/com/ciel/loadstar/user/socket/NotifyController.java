package com.ciel.loadstar.user.socket;

import com.ciel.loadstar.user.socket.dto.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/28 9:32
 */

@RestController
public class NotifyController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    private SimpUserRegistry userRegistry;

    @MessageMapping("/hello")
    public SocketMessage greeting(Principal principal){

        Set users = userRegistry.getUsers();

        SocketMessage message = new SocketMessage();
        message.setData("message content");
//        simpMessageSendingOperations.convertAndSendToUser(principal.getName(),"/queue/greetings", message);
//        simpMessageSendingOperations.convertAndSend("/topic/greetings", message);
        return message;
    }
}
