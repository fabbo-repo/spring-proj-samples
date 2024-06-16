package com.spike.rabbitmqspike.models.dtos;

public class RabbitMQResourceCheck extends RabbitMQBaseCheck {

    private String resource;

    private String name;

    private String permission;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "ResourceCheck{" +
               "resource='" + resource + '\'' +
               ", name='" + name + '\'' +
               ", permission='" + permission + '\'' +
               "} " + super.toString();
    }
}
