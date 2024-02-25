package com.spikes.webflux.models;

public class ApiExampleEntity {
    private String message;

    public ApiExampleEntity() {
    }

    public ApiExampleEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
