package com.spike.websocketsspike.services;

import com.spike.websocketsspike.models.dtos.ChatGroup;
import com.spike.websocketsspike.models.dtos.ChatMessage;
import com.spike.websocketsspike.models.dtos.ChatUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatService {
    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    private final List<ChatUser> chatUsers = new ArrayList<>();

    private final List<ChatGroup> chatGroups = new ArrayList<>();

    @Override
    public ChatGroup join(
            final ChatUser joiningUser,
            final ChatGroup chatGroup
    ) {
        // Add user as connected user
        chatUsers.add(joiningUser);

        // Save user inside chatGroup
        chatGroups
                .stream()
                .filter(
                        group -> group
                                .getId()
                                .equals(chatGroup.getId())
                )
                .findFirst()
                .orElseThrow(

                );

        sendPublicMessage(SystemMessages.welcome(chatRoom.getId(), joiningUser.getUsername()));
        updateConnectedUsersViaWebSocket(chatRoom);
        return chatRoom;
    }
}
