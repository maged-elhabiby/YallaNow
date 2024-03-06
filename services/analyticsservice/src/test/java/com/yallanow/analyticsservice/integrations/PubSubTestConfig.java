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

    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    public interface TestPubsubOutboundGateway {
        void sendToPubsub(String text);
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "test");
    }
}
