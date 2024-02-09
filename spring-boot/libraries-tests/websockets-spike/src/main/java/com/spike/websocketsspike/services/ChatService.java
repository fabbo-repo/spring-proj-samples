package com.spike.websocketsspike.services;

import com.spike.websocketsspike.models.dtos.ChatGroup;
import com.spike.websocketsspike.models.dtos.ChatMessage;
import com.spike.websocketsspike.models.dtos.ChatUser;
import com.spike.websocketsspike.models.exceptions.ChatGroupNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ChatService {
    @Autowired
    private SimpMessagingTemplate webSocketMessagingTemplate;

    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    private final List<ChatUser> connectedUserList = new ArrayList<>();

    private final List<ChatGroup> chatGroupList = new ArrayList<>();

    @PostConstruct
    void setup() {
        this.chatGroupList.add(
                new ChatGroup(
                        UUID.randomUUID(),
                        "Grupo 1",
                        new ArrayList<>()
                )
        );
        this.chatGroupList.add(
                new ChatGroup(
                        UUID.randomUUID(),
                        "Grupo 2",
                        new ArrayList<>()
                )
        );
    }

    public ChatGroup join(
            final ChatUser joiningUser,
            final UUID chatGroupId
    ) {
        // Add user as connected user
        connectedUserList.add(joiningUser);

        // Save user inside chatGroup
        final ChatGroup chatGroup = chatGroupList
                .stream()
                .filter(
                        group -> group
                                .getId()
                                .equals(chatGroupId)
                )
                .findFirst()
                .orElseThrow(
                        ChatGroupNotFoundException::new
                );
        final ChatMessage chatMessage = new ChatMessage(
                chatMessageList.size() + 1,
                joiningUser.getId(),
                null,
                chatGroup.getId(),
                joiningUser.getUsername() + " has joined :)"
        );
        sendPublicMessage(chatMessage);
        return chatGroup;
    }

    public ChatGroup leave(
            final ChatUser leavingUser,
            final UUID chatGroupId
    ) {

        final ChatGroup chatGroup = chatGroupList
                .stream()
                .filter(
                        group -> group
                                .getId()
                                .equals(chatGroupId)
                )
                .findFirst()
                .orElseThrow(
                        ChatGroupNotFoundException::new
                );
        final ChatMessage chatMessage = new ChatMessage(
                chatMessageList.size() + 1,
                leavingUser.getId(),
                null,
                chatGroupId,
                leavingUser.getUsername() + " has left :("
        );

        sendPublicMessage(chatMessage);

        chatGroup
                .getChatUsers()
                .removeIf(
                        chatUser -> chatUser
                                .getId()
                                .equals(leavingUser.getId())
                );

        connectedUserList.removeIf(
                chatUser -> chatUser
                        .getId()
                        .equals(leavingUser.getId())
        );
        webSocketMessagingTemplate.convertAndSend(
                "/topic/connected.users",
                connectedUserList.size()
        );
        return chatGroup;
    }

    public void sendPublicMessage(final ChatMessage chatMessage) {
        webSocketMessagingTemplate.convertAndSend(
                "topic/" + chatMessage.getToGroup() + "public.messages",
                chatMessage
        );
        chatMessageList.add(chatMessage);
    }

    public void sendPrivateMessage(final ChatMessage chatMessage) {
        webSocketMessagingTemplate.convertAndSendToUser(
                chatMessage.getToUser().toString(),
                "/queue/private.messages",
                chatMessage
        );

        webSocketMessagingTemplate.convertAndSendToUser(
                chatMessage.getFromUser().toString(),
                "/queue/private.messages",
                chatMessage
        );
        chatMessageList.add(chatMessage);
    }

    public List<ChatGroup> listChatGroups() {
        return chatGroupList;
    }
}
