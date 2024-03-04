package com.spike.rabbitmqspike.services;

import com.spike.rabbitmqspike.config.RabbitMQConfig;
import com.spike.rabbitmqspike.models.dtos.ChatGroup;
import com.spike.rabbitmqspike.models.dtos.ChatMessage;
import com.spike.rabbitmqspike.models.dtos.ChatUser;
import com.spike.rabbitmqspike.models.exceptions.ChatGroupNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    private final List<ChatUser> connectedUserList = new ArrayList<>();

    private final List<ChatGroup> chatGroupList = new ArrayList<>();

    @PostConstruct
    void setup() {
        this.chatGroupList.add(
                new ChatGroup(
                        UUID.fromString("3fa85f65-5717-4562-b3fc-2c967f66afa6"),
                        "Grupo 1",
                        new ArrayList<>()
                )
        );
        this.chatGroupList.add(
                new ChatGroup(
                        UUID.fromString("3fa95f65-5717-4563-b3fc-2c967f66afa7"),
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
        rabbitTemplate.convertAndSend(
                "/topic/connected.users",
                connectedUserList.size()
        );
        return chatGroup;
    }

    public void sendPublicMessage(final ChatMessage chatMessage) {
        rabbitTemplate.convertAndSend(
                "topic/" + chatMessage.getToGroup() + "public.messages",
                chatMessage
        );
        chatMessageList.add(chatMessage);
    }

    public void sendPrivateMessage(final ChatMessage chatMessage) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitAdmin.declareQueue(
                new Queue(
                        "q." + chatMessage.getToUser().toString(),
                        false,
                        false,
                        true
                )
        );
        rabbitAdmin.declareBinding(
                new Binding(
                        "q." + chatMessage.getToUser().toString(),
                        Binding.DestinationType.QUEUE,
                        RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                        chatMessage.getToUser().toString(),
                        null
                )
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                chatMessage.getToUser().toString(),
                chatMessage.toString()
        );
        chatMessageList.add(chatMessage);
    }

    public List<ChatGroup> listChatGroups() {
        return chatGroupList;
    }

    public List<ChatMessage> listChatMessages(
            final UUID userId,
            final UUID chatGroupId
    ) {
        return chatMessageList
                .stream()
                .filter(
                        chatMessage ->
                                chatGroupId.equals(chatMessage.getToGroup())
                                && chatGroupList.stream().anyMatch(
                                        chatGroup -> chatGroup.getId().equals(chatGroupId)
                                                     && chatGroup.getChatUsers().stream().anyMatch(
                                                chatUser -> chatUser.getId().equals(userId)
                                        )
                                )
                ).toList();
    }

    public List<ChatUser> listConnectedUsers(final UUID chatGroupId) {
        return connectedUserList
                .stream()
                .filter(
                        chatUser ->
                                chatGroupList
                                        .stream()
                                        .anyMatch(chatGroup ->
                                                          chatGroup
                                                                  .getId()
                                                                  .equals(chatGroupId)
                                                          && chatGroup
                                                                  .getChatUsers()
                                                                  .stream()
                                                                  .anyMatch(
                                                                          groupUser -> groupUser
                                                                                  .getId()
                                                                                  .equals(chatUser.getId())
                                                                  )
                                        )
                )
                .toList();
    }
}
