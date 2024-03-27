package com.yallanow.analyticsservice.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import com.yallanow.analyticsservice.factories.EventMessageFactory;
import com.yallanow.analyticsservice.integrations.PubSubTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Map;
import java.util.Random;


@SpringBootTest(classes = {AnalyticsServiceApplication.class, PubSubTestConfig.class})
public class PubSubItemTest {

    @Autowired
    private PubSubTestConfig.EventPubsubOutboundGateway messagingGateway;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPubSubMessageProcessing() throws Exception {
        // Generate and send an ADD event message
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int eventId = random.nextInt();
            // Generate and send ADD event message
            Map<String, Object> addEventMessage = EventMessageFactory.generateEventMessage("ADD", eventId);
            String addEventJson = objectMapper.writeValueAsString(addEventMessage);
            System.out.println(addEventJson);
            messagingGateway.sendToPubsub(addEventJson);
        }
        // Wait for the message to be processed
        Thread.sleep(5000);

        /* Generate and send a DELETE event message for the same event
        Map<String, Object> deleteEventMessage = EventMessageFactory.generateEventMessage("DELETE", eventId);
        String deleteEventJson = objectMapper.writeValueAsString(deleteEventMessage);
        System.out.println(deleteEventJson);
        messagingGateway.sendToPubsub(deleteEventJson);
         */

    }



}
