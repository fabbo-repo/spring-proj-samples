package com.spike.rabbitmqspike.models.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChatQueueResponse {
    private String queue;

    public ChatQueueResponse(String queue) {
        this.queue = queue;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

}
