package com.spikes.webflux.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthFailureHandler {

    public static Mono<Void> formatResponse(
            final ServerHttpResponse response,
            final String message
    ) {
        response.getHeaders()
                .add(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                );

        final byte[] bytes = getBytes(message);
        final DataBuffer buffer = response
                .bufferFactory()
                .wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    private static byte[] getBytes(final String message) {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, String> apiResponse = new HashMap<>();
        apiResponse.put("message", message);

        StringBuilder json = new StringBuilder();
        try {
            json.append(mapper.writeValueAsString(apiResponse));
        } catch (JsonProcessingException ignored) {
            // Ignored
        }

        final String responseBody = json.toString();
        return responseBody.getBytes(StandardCharsets.UTF_8);
    }
}
