package com.yallanow.analyticsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PubSubChannelConfig {

    @Bean
    public MessageChannel userInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel eventInputChannel() {
        return new DirectChannel();
    }

}
