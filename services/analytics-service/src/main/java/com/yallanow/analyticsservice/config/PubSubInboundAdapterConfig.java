package com.yallanow.analyticsservice.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;


@Configuration
public class PubSubInboundAdapterConfig {

    private final PubSubTemplate pubSubTemplate;

    /**
     * Constructs a new PubSubInboundAdapterConfig with the specified PubSubTemplate.
     *
     * @param pubSubTemplate the PubSubTemplate to be used by the adapter
     */
    public PubSubInboundAdapterConfig(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    /**
     * A Spring Integration channel adapter that receives messages from a Pub/Sub subscription and sends them to the specified input channel.
     */
    @Bean
    public PubSubInboundChannelAdapter userMessageChannelAdapter(
            @Qualifier("userInputChannel") MessageChannel inputChannel) {
        return createAdapter(inputChannel, "user-sub");
    }

    /**
     * A channel adapter that receives messages from a Pub/Sub topic and sends them to the specified input channel.
     */
    @Bean
    public PubSubInboundChannelAdapter eventMessageChannelAdapter(
            @Qualifier("eventInputChannel") MessageChannel inputChannel) {
        return createAdapter(inputChannel, "event-sub");
    }

    /**
     * A channel adapter that receives messages from a Google Cloud Pub/Sub subscription
     * and sends them to the specified input channel.
     */
    private PubSubInboundChannelAdapter createAdapter(MessageChannel inputChannel, String subscription) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscription);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }
}
