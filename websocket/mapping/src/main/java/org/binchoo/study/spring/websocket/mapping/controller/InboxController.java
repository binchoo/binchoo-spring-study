package org.binchoo.study.spring.websocket.mapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class InboxController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/{chatroom}")
    public void broadcastUserMessage(@Header("simpSessionId") String sessionId,
                @DestinationVariable("chatroom") String chatRoomName, @Payload String body) {
        String chatRoomMessageBrokerDestination = "/chatroom/" + chatRoomName;
        simpMessagingTemplate.convertAndSend(chatRoomMessageBrokerDestination, sessionId + ": " + body);
    }
}
