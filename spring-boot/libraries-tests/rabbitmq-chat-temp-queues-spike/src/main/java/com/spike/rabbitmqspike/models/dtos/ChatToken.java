package com.spike.rabbitmqspike.models.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChatToken {
    private String id;

    private String token;

    private LocalDateTime endValid;

    public ChatToken(String id, String token, LocalDateTime endValid) {
        this.id = id;
        this.token = token;
        this.endValid = endValid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getEndValid() {
        return endValid;
    }

    public void setEndValid(LocalDateTime endValid) {
        this.endValid = endValid;
    }

    @Override
    public String toString() {
        return "ChatToken{" +
               "id='" + id + '\'' +
               ", token='" + token + '\'' +
               ", endValid=" + endValid +
               '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ChatToken chatToken = (ChatToken) object;
        return Objects.equals(id, chatToken.id) && Objects.equals(token, chatToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token);
    }
}
