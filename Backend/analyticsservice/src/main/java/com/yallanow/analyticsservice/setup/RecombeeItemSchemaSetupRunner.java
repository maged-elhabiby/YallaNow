package com.yallanow.analyticsservice.setup;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import com.yallanow.analyticsservice.client.RecombeeClientImpl;
import com.yallanow.analyticsservice.messagehandlers.ItemMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class RecombeeItemSchemaSetupRunner {

    private static final Logger logger = LoggerFactory.getLogger(ItemMessageHandler.class);

    public static void main(String[] args) {
        // Start the Spring Boot application context
        ConfigurableApplicationContext context = SpringApplication.run(AnalyticsServiceApplication.class, args);

        // Get the RecombeeClientImpl bean from the context
        RecombeeClientImpl recombeeClient = context.getBean(RecombeeClientImpl.class);

        try {
            // Set up item properties in Recombee
            recombeeClient.setItemProperties();
            System.out.println("Item properties setup successfully in Recombee.");
        } catch (Exception e) {
            logger.error("Failed to set up item properties in Recombee: " + e.getMessage());
            System.err.println("Failed to set up item properties in Recombee: " + e.getMessage());
        }

        // Close the application context
        context.close();
    }
}
