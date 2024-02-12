package com.spike.websocketsspike.controllers;

import com.spike.websocketsspike.models.dtos.ChatMessage;
import com.spike.websocketsspike.models.dtos.ChatUser;
import com.spike.websocketsspike.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    private static final String CHAT_GROUP_SESSION_ATTRIBUTE = "chatGroupId";

    @SubscribeMapping("/connected.users")
    public List<ChatUser> listConnectedUsersSubscribe(final SimpMessageHeaderAccessor headerAccessor) {
        if (
                headerAccessor
                        .getSessionAttributes() != null && headerAccessor
                        .getSessionAttributes().containsKey(
                                CHAT_GROUP_SESSION_ATTRIBUTE
                        )
        ) {
            final String chatGroupId = headerAccessor
                    .getSessionAttributes()
                    .get(
                            CHAT_GROUP_SESSION_ATTRIBUTE
                    )
                    .toString();
            return chatService
                    .listConnectedUsers(UUID.fromString(chatGroupId));
        }
        return Collections.emptyList();
    }

    @SubscribeMapping("/old.messages")
    public List<ChatMessage> listOldMessagesFromUserOnSubscribe(
            Principal principal,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        if (
                headerAccessor
                        .getSessionAttributes() != null && headerAccessor
                        .getSessionAttributes().containsKey(
                                CHAT_GROUP_SESSION_ATTRIBUTE
                        )
        ) {
            final String chatGroupId = headerAccessor
                    .getSessionAttributes()
                    .get(CHAT_GROUP_SESSION_ATTRIBUTE)
                    .toString();
            return chatService
                    .listChatMessages(
                            UUID.fromString(principal.getName()),
                            UUID.fromString(chatGroupId)
                    );
        }
        return Collections.emptyList();
    }

    @MessageMapping("/send-msg")
    public void sendMessage(
            @Payload ChatMessage chatMessage,
            Principal principal,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        chatMessage.setFromUser(UUID.fromString(principal.getName()));

        if (chatMessage.getToGroup() != null) {
            chatService.sendPublicMessage(chatMessage);
        } else {
            chatService.sendPrivateMessage(chatMessage);
        }
    }
}