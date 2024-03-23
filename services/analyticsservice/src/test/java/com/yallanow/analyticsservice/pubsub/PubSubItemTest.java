package com.yallanow.analyticsservice.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import com.yallanow.analyticsservice.integrations.PubSubTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static com.yallanow.analyticsservice.factories.EventMessageFactory.generateEventMessage;

@SpringBootTest(classes = {AnalyticsServiceApplication.class, PubSubTestConfig.class})
public class PubSubItemTest {

    @Autowired
    private PubSubTestConfig.TestPubsubOutboundGateway messagingGateway;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPubSubMessageProcessing() throws Exception {
        Map<String, Object> eventMessage = generateEventMessage("ADD");
        String eventJson = objectMapper.writeValueAsString(eventMessage);
        System.out.println(eventJson);
        messagingGateway.sendToPubsub(eventJson);

        Thread.sleep(5000);
    }

}
