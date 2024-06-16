package com.spike.rabbitmqspike.controllers;

import com.spike.rabbitmqspike.models.dtos.RabbitMQResourceCheck;
import com.spike.rabbitmqspike.models.dtos.RabbitMQTopicCheck;
import com.spike.rabbitmqspike.models.dtos.RabbitMQUser;
import com.spike.rabbitmqspike.models.dtos.RabbitMQVirtualHostCheck;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@RestController
@RequestMapping(path = "/auth")
public class AuthBackendHttpController {
    private static final Logger log = Logger.getLogger(AuthBackendHttpController.class.toString());

    private final Map<String, RabbitMQUser> users = new HashMap<>();

    @PostConstruct
    void setup() {
        users.put(
                "guest",
                new RabbitMQUser(
                        "guest",
                        "guest"
                )
        );
        users.put(
                "admin",
                new RabbitMQUser(
                        "admin",
                        "password",
                        asList("administrator", "management")
                )
        );
        users.put(
                "jwt",
                new RabbitMQUser(
                        "jwt",
                        "token"
                )
        );
    }

    @PostMapping("/user")
    public String user(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.info("Trying to authenticate user " + username);
        RabbitMQUser user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return ("allow " + collectionToDelimitedString(user.getTags(), " ")).trim();
        } else {
            return "deny";
        }
    }

    @PostMapping("/vhost")
    public String vhost(RabbitMQVirtualHostCheck check) {
        log.info("Checking vhost access with " + check);
        return "allow";
    }

    @PostMapping("/resource")
    public String resource(RabbitMQResourceCheck check) {
        log.info("Checking resource access with " + check);
        return "allow";
    }

    @PostMapping("/topic")
    public String topic(RabbitMQTopicCheck check) {
        log.info("Checking topic access with " + check);
        return check.getRouting_key().startsWith("a") ? "allow" : "deny";
    }
}
