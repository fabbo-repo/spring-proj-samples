package com.spyke.gcppubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
public class GcpPubSubApplication {
    private static final Logger logger = LoggerFactory.getLogger(GcpPubSubApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GcpPubSubApplication.class, args);
    }

    /**
     * Inbound channel adapter listens to messages from a Google Cloud Pub/Sub subscription
     * and sends them to a Spring channel in an application.
     */
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") final MessageChannel inputChannel,
            final PubSubTemplate pubSubTemplate
    ) {
        final var adapter = new PubSubInboundChannelAdapter(
                pubSubTemplate,
                "testSubscription"
        );
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    /**
     * output channel where the adapter sends the received messages to must be configured
     */
    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    /**
     * Attached to an inbound channel is a service activator which is used to process incoming messages
     */
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            logger.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
            final var originalMessage =
                    message.getHeaders()
                           .get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
        };
    }

    /**
     * An outbound channel adapter listens to new messages from a Spring channel
     * and publishes them to a Google Cloud Pub/Sub topic.
     */
    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "testTopic");
    }
}
