package com.spike.rabbitmqspike.controllers;

import com.spike.rabbitmqspike.models.dtos.ChatMessage;
import com.spike.rabbitmqspike.models.dtos.ChatQueueResponse;
import com.spike.rabbitmqspike.models.dtos.ChatUser;
import com.spike.rabbitmqspike.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/connected-users")
    public List<ChatUser> listConnectedUsers() {
        return chatService.listConnectedUsers();
    }

    @GetMapping("/old-messages")
    public List<ChatMessage> listOldMessagesFromUser(
            @RequestParam final UUID userId
    ) {
        return chatService
                .listChatMessages(
                        userId
                );
    }

    @GetMapping("/chat-queue")
    public ResponseEntity<ChatQueueResponse> getChatQueue(
            @RequestParam final UUID fromUser,
            @RequestParam final UUID toUser
    ) {
        return ResponseEntity.ok(
                chatService.getChatQueue(
                        fromUser,
                        toUser
                )
        );
    }

    @PostMapping("/send-msg")
    public ResponseEntity<Void> sendMessage(
            @RequestBody final ChatMessage chatMessage
    ) {
        chatService.sendMessage(chatMessage);
        return ResponseEntity.noContent().build();
    }
}