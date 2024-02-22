package org.ucalgary.events_microservice.Controller;

import org.ucalgary.events_microservice.Service.EventService;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.DTO.EventDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/microservice/events")
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Add an event
     * @param event
     * @return Response Entity with the object of the event added
     */
    @PostMapping("/AddEvent")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO event) {
        EventsEntity events = eventService.createEvent(event); // createEvent(event);
        return ResponseEntity.ok(events);
    }   

    /**
     * Update an event
     * @param event
     * @return Response Entity with the object of the event updated
     */
    @PostMapping("/UpdateEvent")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO event) {
        EventsEntity events = eventService.updateEvent(event); // updateEvent(event);
        return ResponseEntity.ok(events);
    }

    /**
     * Get all Available events i.e. any event that is scheduled and in the future
     * @return Response Entity with the list of events
     */
    @PostMapping("/AllAvailableEvents")
    public ResponseEntity<?> getAllAvailableEvents() {
        List<EventsEntity> events = eventService.getAllAvailableEvents(); // getAllAvailableEvents(entity);
        return ResponseEntity.ok(events);
    }
    
    /**
     * Get an event with a certain ID
     * @param eventID
     * @return Response Entity with the object of the event
     */
    @GetMapping("/GetEvent/{eventID}")
    public ResponseEntity<?> retrieveEvent(@PathVariable int eventID) {
        EventsEntity events = eventService.getEvent(eventID); // getEvent(eventID);
        return ResponseEntity.ok(events);
    }

    /**
     * Get all events for a certain group
     * @param groupId
     * @return Response Entity with the list of events
     */
    @GetMapping("/GetGroupEvents/{groupId}")
    public ResponseEntity<?> retrieveEventsByGroup(@PathVariable int groupId) {
        ArrayList<EventsEntity> events = eventService.getEventsByGroup(groupId); // getEventsByGroup(groupId);
        return ResponseEntity.ok(events);
    }

    /**
     * Delete an event with a certain ID
     * @param eventId
     * @return Response Entity with the message of the event deleted
     */
    @DeleteMapping("/DeleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable int eventId) {
        try {
            eventService.deleteEvent(eventId); // deleteEvent(eventId);
            return ResponseEntity.ok("Event with ID " + eventId + " has been deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    

}
