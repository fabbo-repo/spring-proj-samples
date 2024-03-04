package com.spike.rabbitmqspike.models.dtos;

import java.util.UUID;

public class ChatMessage {
    private int id;

    private UUID fromUser;

    private UUID toUser;

    private UUID toGroup;

    private String text;

    public ChatMessage() {
    }

    public ChatMessage(
            final int id,
            final UUID fromUser,
            final UUID toUser,
            final UUID toGroup,
            final String text
    ) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.toGroup = toGroup;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UUID getToGroup() {
        return toGroup;
    }

    public void setToGroup(UUID toGroup) {
        this.toGroup = toGroup;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
               "id=" + id +
               ", fromUser=" + fromUser +
               ", toUser=" + toUser +
               ", toGroup=" + toGroup +
               ", text='" + text + '\'' +
               '}';
    }
}
