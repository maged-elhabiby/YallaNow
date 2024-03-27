package org.ucalgary.events_microservice.Controller;

import org.springframework.web.bind.annotation.*;
import org.ucalgary.events_microservice.Service.AddressService;
import org.ucalgary.events_microservice.Service.EventService;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.DTO.EventDTO;

import org.springframework.http.ResponseEntity;

import jakarta.persistence.EntityNotFoundException;
import org.ucalgary.events_microservice.Service.EventsPubService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/microservice/events")
public class EventsController {
    private final EventService eventService;
    private final EventsPubService eventsPubService;
    private final AddressService addressService;

    public EventsController(EventService eventService, EventsPubService eventsPubService, AddressService addressService) {
        this.eventService = eventService;
        this.eventsPubService = eventsPubService;
        this.addressService = addressService;
    }

    /**
     * Add an event
     * @param event
     * @return Response Entity with the object of the event added
     */
    @PostMapping("/AddEvent")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO event) {
        AddressEntity address = addressService.createAddress(event); // Add the Address to the DataBase
        EventsEntity events = eventService.createEvent(event, address, "1"); // createEvent(event);
        eventsPubService.publishEvents(events, "ADD");
        return ResponseEntity.ok(events);
    }

    /**
     * Update an event
     * @param event
     * @return Response Entity with the object of the event updated
     */
    @PostMapping("/UpdateEvent")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO event, @RequestAttribute("Id") String userId) {
        AddressEntity newAddress = addressService.updateAddress(event); // Update the Address in the DataBase
        EventsEntity events = eventService.updateEvent(event, newAddress, userId); // updateEvent(event);
        eventsPubService.publishEvents(events, "UPDATE");
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
            EventsEntity events = eventService.getEvent(eventId);
            if (events != null) {
                eventService.deleteEvent(eventId);
                addressService.deleteAddress(events.getLocationId());
                eventsPubService.publishEvents(events, "DELETE");
                return ResponseEntity.ok("Event with ID " + eventId + " has been deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}