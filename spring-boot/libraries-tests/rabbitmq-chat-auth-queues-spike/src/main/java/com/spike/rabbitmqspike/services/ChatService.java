package com.spike.rabbitmqspike.services;

import com.spike.rabbitmqspike.config.RabbitMQConfig;
import com.spike.rabbitmqspike.models.dtos.ChatMessage;
import com.spike.rabbitmqspike.models.dtos.ChatQueue;
import com.spike.rabbitmqspike.models.dtos.ChatQueueResponse;
import com.spike.rabbitmqspike.models.dtos.ChatUser;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ChatService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String CHAT_QUEUE_PREFIX = "q.chat.";

    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    private final List<ChatQueue> chatQueueList = new ArrayList<>();

    private final List<ChatUser> connectedUserList = new ArrayList<>();

    public ChatQueueResponse getChatQueue(
            final UUID fromUser,
            final UUID toUser
    ) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        final ChatQueue chatQueue = chatQueueList
                .stream()
                .filter(
                        storedChatQueue -> storedChatQueue
                                                   .getUsers()
                                                   .stream().anyMatch(
                                        uuid -> uuid.equals(fromUser)
                                ) && storedChatQueue
                                                   .getUsers()
                                                   .stream().anyMatch(
                                        uuid -> uuid.equals(toUser)
                                )
                )
                .findFirst()
                .orElseGet(
                        () -> {
                            final ChatQueue newChatQueue = new ChatQueue(
                                    UUID.randomUUID(),
                                    List.of(fromUser, toUser)
                            );
                            chatQueueList.add(newChatQueue);
                            return newChatQueue;
                        }
                );

        rabbitAdmin.declareQueue(
                new Queue(
                        CHAT_QUEUE_PREFIX + chatQueue.getQueueId(),
                        false,
                        false,
                        true
                )
        );
        rabbitAdmin.declareBinding(
                new Binding(
                        CHAT_QUEUE_PREFIX + chatQueue.getQueueId(),
                        Binding.DestinationType.QUEUE,
                        RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                        chatQueue.getQueueId().toString(),
                        null
                )
        );
        return new ChatQueueResponse(
                CHAT_QUEUE_PREFIX + chatQueue.getQueueId()
        );
    }

    public void sendMessage(final ChatMessage chatMessage) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        connectedUserList.add(
                new ChatUser(
                        chatMessage.getFromUser(),
                        "test"
                )
        );

        final ChatQueue chatQueue = chatQueueList
                .stream()
                .filter(
                        storedChatQueue -> storedChatQueue
                                                   .getUsers()
                                                   .stream().anyMatch(
                                        uuid -> uuid.equals(chatMessage.getFromUser())
                                ) && storedChatQueue
                                                   .getUsers()
                                                   .stream().anyMatch(
                                        uuid -> uuid.equals(chatMessage.getToUser())
                                )
                )
                .findFirst()
                .orElseThrow();

        rabbitAdmin.declareQueue(
                new Queue(
                        CHAT_QUEUE_PREFIX + chatQueue.getQueueId(),
                        false,
                        false,
                        true
                )
        );
        rabbitAdmin.declareBinding(
                new Binding(
                        CHAT_QUEUE_PREFIX + chatQueue.getQueueId(),
                        Binding.DestinationType.QUEUE,
                        RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                        chatQueue.getQueueId().toString(),
                        null
                )
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                chatQueue.getQueueId().toString(),
                chatMessage.toJson()
        );
        chatMessageList.add(chatMessage);
    }

    public List<ChatMessage> listChatMessages(
            final UUID userId
    ) {
        return chatMessageList
                .stream()
                .filter(
                        chatMessage ->
                                chatMessage.getToUser()
                                           .equals(userId)
                ).toList();
    }

    public List<ChatUser> listConnectedUsers() {
        return connectedUserList;
    }
}
