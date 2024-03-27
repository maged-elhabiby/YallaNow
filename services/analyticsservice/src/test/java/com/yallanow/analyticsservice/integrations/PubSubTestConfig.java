package com.yallanow.analyticsservice.integrations;

import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.boot.test.context.TestConfiguration;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@TestConfiguration
public class PubSubTestConfig {

    @MessagingGateway(defaultRequestChannel = "eventPubsubOutputChannel")
    public interface EventPubsubOutboundGateway {
        void sendToPubsub(String text);
    }

    @MessagingGateway(defaultRequestChannel = "userPubsubOutputChannel")
    public interface UserPubsubOutboundGateway {
        void sendToPubsub(String text);
    }

    @Bean
    @ServiceActivator(inputChannel = "eventPubsubOutputChannel")
    public MessageHandler eventMessageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "event");
    }

    @Bean
    @ServiceActivator(inputChannel = "userPubsubOutputChannel")
    public MessageHandler userMessageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "user");
    }
}
