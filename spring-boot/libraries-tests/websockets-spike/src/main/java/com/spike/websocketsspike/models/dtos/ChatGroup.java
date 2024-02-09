package com.spike.websocketsspike.models.dtos;

import java.util.List;
import java.util.UUID;

public class ChatGroup {

    private UUID id;

    private String groupName;

    private List<ChatUser> chatUsers;

    public ChatGroup() {
    }

    public ChatGroup(UUID id, String groupName, List<ChatUser> chatUsers) {
        this.id = id;
        this.groupName = groupName;
        this.chatUsers = chatUsers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }
}
