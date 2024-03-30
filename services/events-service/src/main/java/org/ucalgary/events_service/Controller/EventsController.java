package org.ucalgary.events_service.Controller;

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

@RestController
@CrossOrigin
@RequestMapping("/events")
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
    public ResponseEntity<?> addEvent(@RequestBody EventDTO event, @RequestAttribute("Id") String userId) {
        try{
            eventService.checkEvent(event, userId);
            AddressEntity address = addressService.createAddress(event); // Add the Address to the DataBase
            EventsEntity events = eventService.createEvent(event, address, userId); // createEvent(event);
            eventsPubService.publishEvents(events, "ADD");
            return ResponseEntity.ok(events);
        }catch(AccessException e){
            return (ResponseEntity<?>) ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Update an event
     * @param event
     * @return Response Entity with the object of the event updated
     */
    @PostMapping("/UpdateEvent")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO event, @RequestAttribute("Id") String userId) {
        try{
            eventService.checkEvent(event, userId); // Check if the event is valid
            AddressEntity newAddress = addressService.updateAddress(event); // Update the Address in the DataBase
            EventsEntity events = eventService.updateEvent(event, newAddress, userId); // updateEvent(event);
            eventsPubService.publishEvents(events, "UPDATE");
            return ResponseEntity.ok(events);
        }catch(AccessException e){
            return (ResponseEntity<?>) ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    /**
     * Get all available events
     * @return Response Entity with the list of events
     */
    @PostMapping("/AllAvailableEvents")
    public ResponseEntity<?> getAllAvailableEvents() {
        List<EventsEntity> events = eventService.getAllAvailableEvents(); // getAllAvailableEvents(entity);
        return ResponseEntity.ok(events);
    }
    
    /**
     * Get all events
     * @return Response Entity with the list of events
     */
    @GetMapping("/GetEvent/{eventID}")
    public ResponseEntity<?> retrieveEvent(@PathVariable int eventID) {
        try{
            EventsEntity events = eventService.getEvent(eventID); // getEvent(eventID);
            return ResponseEntity.ok(events);
        }catch( EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all events for a group
     * @param groupId
     * @return Response Entity with the list of events for the group
     */
    @GetMapping("/GetGroupEvents/{groupId}")
    public ResponseEntity<?> retrieveEventsByGroup(@PathVariable int groupId) {
        try{
            ArrayList<EventsEntity> events = eventService.getEventsByGroup(groupId); // getEventsByGroup(groupId);
            return ResponseEntity.ok(events);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete an event
     * @param eventId
     * @return Response Entity with the message of the event deleted
     */
    @DeleteMapping("/DeleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable int eventId, @RequestAttribute("Id") String userId ) {
        try {
            EventsEntity events = eventService.getEvent(eventId);
            if (events != null) {
                eventService.deleteEvent(events, userId);
                addressService.deleteAddress(events.getLocationId());
                eventsPubService.publishEvents(events, "DELETE");
                return ResponseEntity.ok("Event with ID " + eventId + " has been deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessException e) {
            return (ResponseEntity<?>) ResponseEntity.status(403);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}