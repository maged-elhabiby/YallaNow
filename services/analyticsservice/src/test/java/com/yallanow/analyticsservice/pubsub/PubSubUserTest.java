package com.yallanow.analyticsservice.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import com.yallanow.analyticsservice.integrations.PubSubTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

import static com.yallanow.analyticsservice.factories.UserMessageFactory.generateUserMessage;

@SpringBootTest(classes = {AnalyticsServiceApplication.class, PubSubTestConfig.class})
public class PubSubUserTest {

    @Autowired
    private PubSubTestConfig.TestPubsubOutboundGateway messagingGateway;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPubSubMessageProcessing() throws Exception {
        Map<String, Object> userMessage = generateUserMessage("ADD");
        String userMessageJson = objectMapper.writeValueAsString(userMessage);
        System.out.println(userMessageJson);
        messagingGateway.sendToPubsub(userMessageJson);

        Thread.sleep(5000);
    }
}
