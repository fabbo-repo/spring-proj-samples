package com.spike.rabbitmqspike.controllers;

import com.spike.rabbitmqspike.models.dtos.ChatMessage;
import com.spike.rabbitmqspike.models.dtos.ChatUser;
import com.spike.rabbitmqspike.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    private static final String CHAT_GROUP_SESSION_ATTRIBUTE = "chatGroupId";

    @GetMapping("/connected-users/{chatGroupId}")
    public List<ChatUser> listConnectedUsers(
            @PathVariable final UUID chatGroupId
    ) {
        return chatService
                .listConnectedUsers(chatGroupId);
    }

    @GetMapping("/old-messages/{chatGroupId}")
    public List<ChatMessage> listOldMessagesFromUser(
            @PathVariable final UUID chatGroupId,
            @RequestParam final UUID userId
    ) {
        return chatService
                .listChatMessages(
                        userId,
                        chatGroupId
                );
    }

    @PostMapping("/send-msg")
    public ResponseEntity<Void> sendMessage(
            @RequestParam final UUID userId,
            @RequestBody final ChatMessage chatMessage
    ) {
        chatMessage.setFromUser(userId);
        if (chatMessage.getToGroup() != null) {
            chatService.sendPublicMessage(chatMessage);
        } else {
            chatService.sendPrivateMessage(chatMessage);
        }
        return ResponseEntity.noContent().build();
    }
}