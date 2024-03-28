package com.yallanow.analyticsservice.setup;

import com.yallanow.analyticsservice.AnalyticsServiceApplication;
import com.yallanow.analyticsservice.client.RecombeeClientImpl;
import com.yallanow.analyticsservice.messagehandlers.ItemMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class RecombeeUserSchemaSetupRunner {

    private static final Logger logger = LoggerFactory.getLogger(ItemMessageHandler.class);

    /**
     * The entry point of the application.
     * Initializes the Spring Boot application context, sets up user properties in Recombee,
     * and closes the application context.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Start the Spring Boot application context
        ConfigurableApplicationContext context = SpringApplication.run(AnalyticsServiceApplication.class, args);

        // Get the RecombeeClientImpl bean from the context
        RecombeeClientImpl recombeeClient = context.getBean(RecombeeClientImpl.class);

        try {
            // Set up item properties in Recombee
            recombeeClient.setUserProperties();
            System.out.println("User properties setup successfully in Recombee.");
        } catch (Exception e) {
            logger.error("Failed to set up user properties in Recombee: " + e.getMessage());
            System.err.println("Failed to set up user properties in Recombee: " + e.getMessage());
        }

        // Close the application context
        context.close();
    }
}
