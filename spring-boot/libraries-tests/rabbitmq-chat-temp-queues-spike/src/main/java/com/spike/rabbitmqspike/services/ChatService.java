package com.spike.rabbitmqspike.services;

import com.spike.rabbitmqspike.config.RabbitMQConfig;
import com.spike.rabbitmqspike.models.dtos.ChatMessage;
import com.spike.rabbitmqspike.models.dtos.ChatToken;
import com.spike.rabbitmqspike.models.dtos.ChatUser;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ChatService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = Logger.getLogger(ChatService.class.toString());

    private static final String CHAT_QUEUE_PREFIX = "q.chat.";

    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    private final List<ChatToken> chatTokenList = new ArrayList<>();

    private final List<ChatUser> connectedUserList = new ArrayList<>();

    private ChatToken getOrRegenerateChatToken(
            final UUID fromUser,
            final UUID toUser
    ) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        final String chatId = fromUser + "-" + toUser;
        return chatTokenList
                .stream()
                .filter(
                        storedChatToken ->
                                storedChatToken.getId()
                                               .equals(chatId)
                )
                .findAny()
                .map(
                        storedChatToken -> {
                            if (storedChatToken.getEndValid().isBefore(
                                    LocalDateTime.now()
                            )) {
                                log.info(
                                        "Queue " + CHAT_QUEUE_PREFIX + storedChatToken.getToken()
                                        + " is no longer valid"
                                );
                                rabbitAdmin.deleteQueue(CHAT_QUEUE_PREFIX + storedChatToken.getToken());
                                chatTokenList.removeIf(
                                        chatListToken ->
                                                chatListToken.getId()
                                                             .equals(
                                                                     storedChatToken.getId()
                                                             )
                                );
                                final ChatToken newChatToken = new ChatToken(
                                        chatId,
                                        generateToken(),
                                        LocalDateTime.now().plusMinutes(5)
                                );
                                chatTokenList.add(newChatToken);
                                return newChatToken;
                            }
                            return storedChatToken;
                        }
                )
                .orElseGet(
                        () -> {
                            final ChatToken newChatToken = new ChatToken(
                                    chatId,
                                    generateToken(),
                                    LocalDateTime.now().plusMinutes(5)
                            );
                            chatTokenList.add(newChatToken);
                            return newChatToken;
                        }
                );
    }

    public ChatToken getChatToken(
            final UUID fromUser,
            final UUID toUser
    ) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        final ChatToken chatToken = getOrRegenerateChatToken(
                fromUser,
                toUser
        );

        rabbitAdmin.declareQueue(
                new Queue(
                        CHAT_QUEUE_PREFIX + chatToken.getToken(),
                        false,
                        false,
                        true
                )
        );
        rabbitAdmin.declareBinding(
                new Binding(
                        CHAT_QUEUE_PREFIX + chatToken.getToken(),
                        Binding.DestinationType.QUEUE,
                        RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                        toUser.toString(),
                        null
                )
        );
        return chatToken;
    }

    public void sendMessage(final ChatMessage chatMessage) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        connectedUserList.add(
                new ChatUser(
                        chatMessage.getFromUser(),
                        "test"
                )
        );

        final ChatToken chatToken = getOrRegenerateChatToken(
                chatMessage.getFromUser(),
                chatMessage.getToUser()
        );

        rabbitAdmin.declareQueue(
                new Queue(
                        CHAT_QUEUE_PREFIX + chatToken.getToken(),
                        false,
                        false,
                        true
                )
        );
        rabbitAdmin.declareBinding(
                new Binding(
                        CHAT_QUEUE_PREFIX + chatToken.getToken(),
                        Binding.DestinationType.QUEUE,
                        RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                        chatMessage.getToUser().toString(),
                        null
                )
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRIVATE_CHAT_EXCHANGE,
                chatMessage.getToUser().toString(),
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

    private String generateToken() {
        final SecureRandom secureRandom = new SecureRandom();
        final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

        final byte[] randomBytes = new byte[44];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
