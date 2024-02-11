package com.spike.websocketsspike.handlers;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class WebSocketHandshakeHandler implements HandshakeHandler {
    @Override
    public boolean doHandshake(
            final ServerHttpRequest request,
            final ServerHttpResponse response,
            final WebSocketHandler wsHandler,
            final Map<String, Object> attributes
    ) throws HandshakeFailureException {
        System.out.println("HOlaaaa");
        return true;
    }
}
