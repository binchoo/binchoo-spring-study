package org.binchoo.study.spring.websocket.securitychat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class InboxController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/{chatroom}")
    public void broadcastUserMessage(@Header("simpSessionId") String sessionId, Principal principal,
                @DestinationVariable("chatroom") String chatRoomName, @Payload String message) {
        String userDisplayName = sessionId;
        String chatRoomMessageBrokerDestination = "/chatroom/" + chatRoomName;

        if (principal != null)
            userDisplayName = principal.getName() + "(" + sessionId + ")";

        simpMessagingTemplate.convertAndSend(chatRoomMessageBrokerDestination,
                userDisplayName + ": " + message);
    }
}
