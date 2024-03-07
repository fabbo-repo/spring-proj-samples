package com.spike.rabbitmqspike.models.dtos;

import java.util.List;
import java.util.UUID;

public class ChatQueue {

    private UUID queueId;

    private List<UUID> users;

    public ChatQueue() {
    }

    public ChatQueue(UUID queueId, List<UUID> users) {
        this.queueId = queueId;
        this.users = users;
    }

    public UUID getQueueId() {
        return queueId;
    }

    public void setQueueId(UUID queueId) {
        this.queueId = queueId;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }
}
