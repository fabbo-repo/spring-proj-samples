package com.spike.websocketsspike.models.dtos;

import java.util.List;
import java.util.UUID;

public class ChatGroup {

    private UUID id;

    private String username;

    private List<ChatUser> chatUsers;

    public ChatGroup() {
    }

    public ChatGroup(UUID id, String username, List<ChatUser> chatUsers) {
        this.id = id;
        this.username = username;
        this.chatUsers = chatUsers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }
}
