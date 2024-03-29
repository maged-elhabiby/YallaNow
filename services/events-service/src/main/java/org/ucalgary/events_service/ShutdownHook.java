package org.ucalgary.events_service;

import org.springframework.stereotype.Component;
import org.ucalgary.events_service.Service.EventsPubService;

import javax.annotation.PreDestroy;

@Component
public class ShutdownHook {

    private final EventsPubService eventsPubService;

    public ShutdownHook(EventsPubService eventsPubService) {
        this.eventsPubService = eventsPubService;
    }

    /**
     * Shutdown hook to close resources gracefully.
     */
    @PreDestroy
    public void onShutdown() {
        try {
            if (eventsPubService != null) {
                eventsPubService.shutdown(); // Call the shutdown method of your service to close resources gracefully
            }
        } catch (Exception e) {
            System.err.println("Error occurred during shutdown: " + e.getMessage());
        }
    }
}
