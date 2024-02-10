package com.spike.websocketsspike.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${api.chat.broker.host}")
    private String brokerHost;

    @Value("${api.chat.broker.port}")
    private Integer brokerPort;

    @Value("${api.chat.broker.user}")
    private String brokerUser;

    @Value("${api.chat.broker.password}")
    private String brokerPassword;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                //.setHandshakeHandler()
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/queue/", "/topic/")
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setRelayHost(brokerHost)
                .setRelayPort(brokerPort)
                .setSystemLogin(brokerUser)
                .setSystemPasscode(brokerPassword);

        registry.setApplicationDestinationPrefixes("/chatGroup");
    }
}
