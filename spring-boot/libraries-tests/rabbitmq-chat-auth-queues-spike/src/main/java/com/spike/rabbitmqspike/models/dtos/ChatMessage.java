package com.spike.rabbitmqspike.models.dtos;

import java.util.UUID;

public class ChatMessage {
    private UUID fromUser;

    private UUID toUser;

    private String text;

    public ChatMessage() {
    }

    public ChatMessage(
            final UUID fromUser,
            final UUID toUser,
            final String text
    ) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
    }

    public UUID getFromUser() {
        return fromUser;
    }

    public void setFromUser(UUID fromUser) {
        this.fromUser = fromUser;
    }

    public UUID getToUser() {
        return toUser;
    }

    public void setToUser(UUID toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toJson() {
        return "{" +
               "\"fromUser\": \"" + fromUser +
               "\", \"toUser\": \"" + toUser +
               "\", \"text\": \"" + text + "\"" +
               "}";
    }
}
