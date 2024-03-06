package com.yallanow.analyticsservice.integrations;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AnalyticsServiceApplication.class, PubSubTestConfig.class})
public class PubSubIntegrationTest {

    @Autowired
    private PubSubTestConfig.TestPubsubOutboundGateway messagingGateway;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Test
    public void testPubSubMessageProcessing() throws Exception {
        String testMessage = "Test message";
        messagingGateway.sendToPubsub(testMessage);

        QueueChannel channel = new QueueChannel();
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "test-sub");
        adapter.setOutputChannel(channel);
        adapter.start();

        Message<?> receivedMessage = channel.receive(5000);
        adapter.stop();

        assertEquals(testMessage, new String((byte[]) receivedMessage.getPayload()));
    }
}
