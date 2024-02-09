package com.spike.websocketsspike.controllers;

import ChatMessage;
import ChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private InstantMessageService instantMessageService;

    @SubscribeMapping("/connected.users")
    public List<ChatUser> listChatRoomConnectedUsersOnSubscribe(final SimpMessageHeaderAccessor headerAccessor) {
        final String chatRoomId = headerAccessor
                .getSessionAttributes()
                .get("chatRoomId")
                .toString();
        return chatService
                .findById(chatRoomId)
                .getConnectedUsers();
    }

    @SubscribeMapping("/old.messages")
    public List<ChatMessage> listOldMessagesFromUserOnSubscribe(
            Principal principal,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
        return instantMessageService.findAllInstantMessagesFor(principal.getName(), chatRoomId);
    }

    @MessageMapping("/send-msg")
    @SendTo("/receive-msg")
    public void sendMessage(
            @Payload ChatMessage chatMessage,
            Principal principal,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        String chatRoomId = headerAccessor
                .getSessionAttributes()
                .get("chatRoomId")
                .toString();
        instantMessage.setFromUser(principal.getName());
        instantMessage.setChatRoomId(chatRoomId);

        if (instantMessage.isPublic()) {
            chatRoomService.sendPublicMessage(instantMessage);
        } else {
            chatRoomService.sendPrivateMessage(instantMessage);
        }
    }
}