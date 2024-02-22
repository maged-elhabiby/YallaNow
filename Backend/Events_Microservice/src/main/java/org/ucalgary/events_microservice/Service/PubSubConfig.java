package org.ucalgary.events_microservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.PublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.SubscriberFactory;
import org.springframework.cloud.gcp.pubsub.support.converter.PubSubMessageConverter;
import org.springframework.cloud.gcp.pubsub.support.converter.SimplePubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfig {

    @Bean
    public PubSubTemplate pubSubTemplate(PublisherFactory pubSubPublisherTemplate,
                                         SubscriberFactory pubSubSubscriberTemplate) {
        return new PubSubTemplate(pubSubPublisherTemplate, pubSubSubscriberTemplate);
    }
    
    @Bean
    public PubSubMessageConverter pubSubMessageConverter() {
        return new SimplePubSubMessageConverter();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
