package com.yallanow.analyticsservice.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import  com.google.cloud.spring.pubsub.integration.AckMode;


@Configuration
public class PubSubInboundAdapterConfig {

    private final PubSubTemplate pubSubTemplate;

    public PubSubInboundAdapterConfig(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    @Bean
    public PubSubInboundChannelAdapter userMessageChannelAdapter(
            @Qualifier("userInputChannel") MessageChannel inputChannel) {
        return createAdapter(inputChannel, "user-sub");
    }
    @Bean
    public PubSubInboundChannelAdapter eventMessageChannelAdapter(
            @Qualifier("eventInputChannel") MessageChannel inputChannel) {
        return createAdapter(inputChannel, "event-sub");
    }

    private PubSubInboundChannelAdapter createAdapter(MessageChannel inputChannel, String subscription) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscription);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }
}
