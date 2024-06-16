package com.spike.rabbitmqspike.models.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RabbitMQUser {

    private final String username;

    private final String password;

    private final Collection<String> tags;

    public RabbitMQUser(String username, String password) {
        this(
                username,
                password,
                Collections.emptyList()
        );
    }

    public RabbitMQUser(String username, String password, Collection<String> tags) {
        this.username = username;
        this.password = password;
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<String> getTags() {
        return new ArrayList<>(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RabbitMQUser user = (RabbitMQUser) o;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
