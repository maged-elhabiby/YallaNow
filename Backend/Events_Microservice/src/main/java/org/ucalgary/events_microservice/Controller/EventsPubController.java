package org.ucalgary.events_microservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_microservice.Service.EventService;
import org.ucalgary.events_microservice.Service.EventsPubService;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/microservice/events")
public class EventsPubController {
    private final EventService eventsService;
    private final EventsPubService eventsPubService;

    public EventsPubController(EventsPubService eventsPubService, EventService eventsService) {
        this.eventsService = eventsService;
        this.eventsPubService = eventsPubService;
    }

    /**
     * Publishes all available events to the Google Cloud Pub/Sub.
     * @return Response Entity with the list of published events.
     * @throws InterruptedException if the thread is interrupted while waiting for the publish operation to complete.
     */
    @PostMapping("/publishEvents")
    public ResponseEntity<?> publishEvents() throws InterruptedException, IOException {
        List<EventsEntity> eventsList = eventsService.getAllAvailableEvents();

        return ResponseEntity.ok(eventsPubService.publishEvents(eventsList)); // Publish the list of available events using the EventsPubService
    }

}