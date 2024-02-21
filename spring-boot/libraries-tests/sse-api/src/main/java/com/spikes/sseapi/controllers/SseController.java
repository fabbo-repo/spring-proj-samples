package com.spikes.sseapi.controllers;

import com.spikes.sseapi.services.SseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {
    private final SseService sseService;

    public SseController(final SseService sseService) {
        this.sseService = sseService;
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);
        // Add the emitter to a list of subscribers or handle it in another way
        return emitter;
    }

    @PostMapping(path = "/post")
    public void postMessage() {
        sseService.sendEvents();
    }
}
