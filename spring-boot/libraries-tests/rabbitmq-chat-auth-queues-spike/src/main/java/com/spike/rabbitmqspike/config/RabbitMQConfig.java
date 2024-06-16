package com.spike.rabbitmqspike.config;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String PRIVATE_CHAT_EXCHANGE = "x.private-chat";

    @Bean
    public Declarables createRabbitDeclarables(){

        return new Declarables(
                new FanoutExchange(PRIVATE_CHAT_EXCHANGE)
        );
    }
}
