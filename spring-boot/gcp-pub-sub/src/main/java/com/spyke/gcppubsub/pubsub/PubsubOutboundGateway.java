package com.spyke.gcppubsub.pubsub;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
public interface PubsubOutboundGateway {

    void sendToPubsub(String text);
}