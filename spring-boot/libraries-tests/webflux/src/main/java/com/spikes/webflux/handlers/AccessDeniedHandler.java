package com.spikes.webflux.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(
            final ServerWebExchange exchange,
            final AccessDeniedException denied
    ) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return AuthFailureHandler.formatResponse(
                response,
                denied.getMessage()
        );
    }
}
