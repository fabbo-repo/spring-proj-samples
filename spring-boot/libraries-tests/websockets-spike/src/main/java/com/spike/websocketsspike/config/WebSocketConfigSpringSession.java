package com.spike.websocketsspike.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigSpringSession {
    @Value("${api.chat.relay.host}")
    private String relayHost;

    @Value("${api.chat.relay.port}")
    private Integer relayPort;

    protected void configureStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setHandshakeHandler()
                .withSockJS();
    }

    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/queue/", "/topic/")
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort);

        registry.setApplicationDestinationPrefixes("/chatroom");
    }
}
