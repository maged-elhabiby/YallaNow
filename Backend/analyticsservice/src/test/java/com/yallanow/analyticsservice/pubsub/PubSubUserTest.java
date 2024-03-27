package com.yallanow.analyticsservice.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import com.yallanow.analyticsservice.integrations.PubSubTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import java.util.Random;

import static com.yallanow.analyticsservice.factories.UserMessageFactory.generateUserMessage;

@SpringBootTest(classes = {AnalyticsServiceApplication.class, PubSubTestConfig.class})
public class PubSubUserTest {

    @Autowired
    private PubSubTestConfig.UserPubsubOutboundGateway messagingGateway;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPubSubMessageProcessing() throws Exception {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int userId = random.nextInt();
            Map<String, Object> message = generateUserMessage("ADD", userId);
            String messageJson = objectMapper.writeValueAsString(message);
            System.out.println(messageJson);
            messagingGateway.sendToPubsub(messageJson);
        }
        Thread.sleep(5000);

        /*
        Map<String, Object> deleteMessage = generateUserMessage("DELETE", userId);
        String deleteJson = objectMapper.writeValueAsString(deleteMessage);
        System.out.println(deleteJson);
        messagingGateway.sendToPubsub(deleteJson);
        */
    }
}
