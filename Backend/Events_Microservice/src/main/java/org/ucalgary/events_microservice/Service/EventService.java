package org.ucalgary.events_microservice.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.DTO.EventStatus;
import org.ucalgary.events_microservice.DTO.EventDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for managing EventsEntity objects.
 * This class provides methods to create, update, retrieve, and delete events.
 */
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AddressService addressService;


    public EventService(EventRepository eventRepository, AddressService addressService) {
        this.eventRepository = eventRepository;
        this.addressService = addressService;
    }

    /**
     * Creates a new event based on the provided event DTO.
     * @param event The event DTO containing event information.
     * @return The newly created event entity.
     * @throws IllegalStateException if the event is invalid.
     */
    @Transactional
    public EventsEntity createEvent(EventDTO event, AddressEntity address) {
        checkEvent(event); // Check if the event is valid

        EventsEntity newEvent = new EventsEntity(event.getEventID(),
                                                event.getGroupID(),
                                                event.getEventTitle(), 
                                                event.getEventDescription(), 
                                                address.getAddressId(),
                                                event.getEventStartTime(),
                                                event.getEventEndTime(),
                                                event.getStatus(), 
                                                event.getCount(),
                                                event.getCapacity(),
                                                event.getImageID());
        newEvent.setAddress(address);
        return eventRepository.save(newEvent); // Add the Event to the DataBase
    }

    /**
     * Updates an existing event based on the provided event DTO.
     * If the event does not exist, it will be created.
     * @param updatedEvent The updated event DTO.
     * @return The updated or newly created event entity.
     * @throws IllegalStateException if the event is invalid.
     */
    @Transactional
    public EventsEntity updateEvent(EventDTO updatedEvent, AddressEntity newAddress) {
        EventsEntity oldEvent;
        try {
            oldEvent = getEvent(updatedEvent.getEventID()); // Check if the event exists
        } catch (EntityNotFoundException e) {
            return createEvent(updatedEvent, new AddressEntity()); // If the event does not exist, create it
        }

        checkEvent(updatedEvent); // Check if the event is valid

        oldEvent.setGroupId(updatedEvent.getGroupID());
        oldEvent.setEventTitle(updatedEvent.getEventTitle());
        oldEvent.setEventDescription(updatedEvent.getEventDescription());
        oldEvent.setLocationId(newAddress.getAddressId());
        oldEvent.setEventStartTime(updatedEvent.getEventStartTime());
        oldEvent.setEventEndTime(updatedEvent.getEventEndTime());
        oldEvent.setStatus(updatedEvent.getStatus());
        oldEvent.setCount(updatedEvent.getCount());
        oldEvent.setCapacity(updatedEvent.getCapacity());
        oldEvent.setImageId(updatedEvent.getImageID());
        return eventRepository.save(oldEvent); // Update the Event in the DataBase
    }

    /**
     * Retrieves an event entity with the specified event ID.
     * @param eventID The ID of the event to retrieve.
     * @return The event entity.
     * @throws EntityNotFoundException if the event with the given ID does not exist.
     */
    public EventsEntity getEvent(int eventID) {
        Optional<EventsEntity> optionalEvent = eventRepository.findEventByEventId(eventID); // Check if the event exists
        if (optionalEvent.isPresent()) {
            return optionalEvent.get(); // Return the event
        } else {
            throw new EntityNotFoundException("Event with ID " + eventID + " does not exist");
        }
    }

    /**
     * Retrieves all events associated with the specified group ID.
     * @param groupID The ID of the group to retrieve events for.
     * @return A list of events associated with the group.
     */
    @Transactional
    public ArrayList<EventsEntity> getEventsByGroup(int groupID) {
        Optional<ArrayList<EventsEntity>> optionalEvents = eventRepository.findEventsByGroupId(groupID); // Check if there are any events in a certain group
        return optionalEvents.orElse(null);
    }

    /**
     * Retrieves all events from the database.
     * @return A list of all events.
     */
    @Transactional 
    public List<EventsEntity> getAllAvailableEvents() {
        List<EventsEntity> events = eventRepository.findAll();

        events.removeIf(event -> event.getEventEndTime().isEqual(LocalDateTime.now()) ||
                        event.getCapacity().equals(event.getCount()) ||
                        event.getStatus() != EventStatus.Scheduled);
        return events; // Return all Events that are Scheduled and that are not in the past.
    }

    /**
     * Deletes an event entity with the specified event ID.
     * @param eventID The ID of the event entity to delete.
     * @throws EntityNotFoundException if the event with the given ID does not exist.
     */
    @Transactional
    public void deleteEvent(int eventID) {
        EventsEntity event = getEvent(eventID);
        if (event != null) {
            eventRepository.delete(event);
        } else {
            throw new EntityNotFoundException("Event with ID " + eventID + " does not exist");
        }
    }

    /**
     * Checks if the event represented by the provided event DTO is valid.
     * @param event The event DTO to validate.
     */
    public void checkEvent(EventDTO event) {
        if (event.getEventEndTime().isBefore(LocalDateTime.now())) { // Check if the event is in the past
            throw new IllegalArgumentException("You can't make an event in the past");
        }
        if (event.getEventStartTime().isAfter(event.getEventEndTime())) { // Check if the event start time is after the end time
            throw new IllegalArgumentException("Start time can't be after end time");
        } 
        if(event.getCapacity() < 0) { // Check if the event capacity is negative
            throw new IllegalArgumentException("Capacity can't be negative");
        }
        if(event.getLocation() == null){
            throw new IllegalArgumentException("Missing Address");
        }
    }
}
