package org.ucalgary.events_service.Controller;

import org.slf4j.ILoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.ucalgary.events_service.Service.AddressService;
import org.ucalgary.events_service.Service.EventService;
import org.ucalgary.events_service.Entity.AddressEntity;
import org.ucalgary.events_service.Entity.EventsEntity;
import org.ucalgary.events_service.DTO.EventDTO;
import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;
import org.ucalgary.events_service.Service.EventsPubService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventsController {
    private final EventService eventService;
    private final EventsPubService eventsPubService;
    private final AddressService addressService;

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);


    public EventsController(EventService eventService, EventsPubService eventsPubService, AddressService addressService) {
        this.eventService = eventService;
        this.eventsPubService = eventsPubService;
        this.addressService = addressService;
    }

    /**
     * Add an event
     *
     * @param event
     * @return Response Entity with the object of the event added
     */
    @PostMapping("/AddEvent")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO event, @RequestAttribute("Id") String userId) {
        try {
            logger.info("Adding event: {} by user: {}", event, userId);
            eventService.checkEvent(event, userId);
            AddressEntity address = addressService.createAddress(event);
            EventsEntity events = eventService.createEvent(event, address, userId);
            eventsPubService.publishEvents(events, "ADD");
            logger.info("Event added successfully: {}", events);
            return ResponseEntity.ok(events);
        } catch (AccessException e) {
            logger.error("Access error when adding event: {}", e.getMessage());
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument error when adding event: {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found error when adding event: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Update an event
     *
     * @param event
     * @return Response Entity with the object of the event updated
     */
    @PostMapping("/UpdateEvent")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO event, @RequestAttribute("Id") String userId) {
        try {
            logger.info("Updating event: {} by user: {}", event, userId);
            eventService.checkEvent(event, userId); // Check if the event is valid
            AddressEntity newAddress = addressService.updateAddress(event); // Update the Address in the DataBase
            EventsEntity events = eventService.updateEvent(event, newAddress, userId); // updateEvent(event);
            eventsPubService.publishEvents(events, "UPDATE");
            logger.info("Event updated successfully: {}", events);
            return ResponseEntity.ok(events);
        } catch (AccessException e) {
            logger.error("Access error when updating event: {}", e.getMessage());
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument error when updating event: {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found error when updating event: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("General error when updating event: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /**
     * Get all available events
     *
     * @return Response Entity with the list of events
     */
    @PostMapping("/AllAvailableEvents")
    public ResponseEntity<?> getAllAvailableEvents() {
        logger.info("Retrieving all available events");
        List<EventsEntity> events = eventService.getAllAvailableEvents(); // getAllAvailableEvents(entity);
        return ResponseEntity.ok(events);
    }

    /**
     * Get all events
     *
     * @return Response Entity with the list of events
     */
    @GetMapping("/GetEvent/{eventID}")
    public ResponseEntity<?> retrieveEvent(@PathVariable int eventID) {
        try {
            logger.info("Retrieving event with ID: {}", eventID);
            EventsEntity events = eventService.getEvent(eventID); // getEvent(eventID);
            return ResponseEntity.ok(events);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found error when retrieving event with ID: {}", eventID);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("General error when retrieving event with ID: {}", eventID, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all events for a group
     *
     * @param groupId
     * @return Response Entity with the list of events for the group
     */
    @GetMapping("/GetGroupEvents/{groupId}")
    public ResponseEntity<?> retrieveEventsByGroup(@PathVariable int groupId) {
        try {
            logger.info("Retrieving events for group with ID: {}", groupId);
            ArrayList<EventsEntity> events = eventService.getEventsByGroup(groupId); // getEventsByGroup(groupId);
            return ResponseEntity.ok(events);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found error when retrieving events for group with ID: {}", groupId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("General error when retrieving events for group with ID: {}", groupId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete an event
     *
     * @param eventId
     * @return Response Entity with the message of the event deleted
     */
    @DeleteMapping("/DeleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable int eventId, @RequestAttribute("Id") String userId) {
        try {
            logger.info("Deleting event with ID: {} by user: {}", eventId, userId);
            EventsEntity events = eventService.getEvent(eventId);
            if (events != null) {
                eventService.deleteEvent(events, userId);
                addressService.deleteAddress(events.getLocationId());
                eventsPubService.publishEvents(events, "DELETE");
                logger.info("Event with ID: {} deleted successfully.", eventId);
                return ResponseEntity.ok("Event with ID " + eventId + " has been deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found error when deleting event with ID: {}", eventId);
            return ResponseEntity.notFound().build();
        } catch (AccessException e) {
            logger.error("Access error when deleting event with ID: {}", eventId, e);
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            logger.error("General error when deleting event with ID: {}", eventId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}