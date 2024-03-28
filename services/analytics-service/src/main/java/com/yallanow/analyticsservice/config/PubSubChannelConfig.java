package com.yallanow.analyticsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PubSubChannelConfig {

    /**
     * Creates and configures the user input channel.
     * 
     * @return the user input channel
     */
    @Bean
    public MessageChannel userInputChannel() {
        return new DirectChannel();
    }

    /**
     * Creates and configures the event input channel.
     * 
     * @return the event input channel
     */
    @Bean
    public MessageChannel eventInputChannel() {
        return new DirectChannel();
    }

}
