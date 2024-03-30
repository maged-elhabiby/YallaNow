package org.ucalgary.events_service.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.ucalgary.events_service.Repository.EventRepository;
import org.ucalgary.events_service.Entity.AddressEntity;
import org.ucalgary.events_service.Entity.EventsEntity;
import org.ucalgary.events_service.Entity.GroupUsersEntity;
import org.ucalgary.events_service.DTO.EventStatus;
import org.ucalgary.events_service.DTO.EventDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Service class for managing EventsEntity objects.
 * This class provides methods to create, update, retrieve, and delete events.
 */
@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final GroupUsersService groupUsersService;

    public EventService(EventRepository eventRepository, GroupUsersService groupUsersService) {
        this.eventRepository = eventRepository;
        this.groupUsersService = groupUsersService;
    }

    /**
     * Creates a new event entity based on the provided event DTO.
     * @param event The event DTO containing event information.
     * @return The newly created event entity.
     * @throws AccessException if the event is invalid.
     */
    @Transactional
    public EventsEntity createEvent(EventDTO event, AddressEntity address, String userID) throws AccessException, IllegalArgumentException {
        try {
            EventsEntity newEvent = getEventsEntity(event, address);
            EventsEntity savedEvent = eventRepository.save(newEvent); // Add the Event to the DataBase
            logger.info("Created new event with ID: {}", savedEvent.getEventId());
            return savedEvent;
        } catch (Exception e) {
            logger.error("Error creating event: {}", e.getMessage(), e);
            throw e;
        }
    }

    private static EventsEntity getEventsEntity(EventDTO event, AddressEntity address) {
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
                event.getImageUrl());
        newEvent.setAddress(address);
        return newEvent;
    }

    /**
     * Updates an existing event entity based on the provided event DTO.
     * If the event does not exist, it will be created.
     * @param updatedEvent The updated event DTO.
     * @param newAddress The updated address entity.
     * @return The updated or newly created event entity.
     * @throws AccessException if the event is invalid.
     * @throws EntityNotFoundException if the event does not exist.
     */
    @Transactional
    public EventsEntity updateEvent(EventDTO updatedEvent, AddressEntity newAddress, String userID)
            throws AccessException, IllegalArgumentException {
        logger.info("Updating event with ID: {}", updatedEvent.getEventID());
        EventsEntity oldEvent;
        try {
            oldEvent = getEvent(updatedEvent.getEventID()); // Check if the event exists
        } catch (EntityNotFoundException e) {
            logger.info("Event with ID: {} does not exist, creating new event", updatedEvent.getEventID());
            return createEvent(updatedEvent, newAddress, userID); // If the event does not exist, create it
        }

        oldEvent.setGroupId(updatedEvent.getGroupID());
        oldEvent.setEventTitle(updatedEvent.getEventTitle());
        oldEvent.setEventDescription(updatedEvent.getEventDescription());
        oldEvent.setLocationId(newAddress.getAddressId());
        oldEvent.setEventStartTime(updatedEvent.getEventStartTime());
        oldEvent.setEventEndTime(updatedEvent.getEventEndTime());
        oldEvent.setStatus(updatedEvent.getStatus());
        oldEvent.setCount(updatedEvent.getCount());
        oldEvent.setCapacity(updatedEvent.getCapacity());
        oldEvent.setImageUrl(updatedEvent.getImageUrl());
        EventsEntity updated = eventRepository.save(oldEvent); // Update the Event in the DataBase
        logger.info("Updated event with ID: {}", updated.getEventId());
        return updated;
    }

    /**
     * Retrieves an event entity with the specified event ID.
     * @param eventID The ID of the event entity to retrieve.
     * @return The event entity with the specified ID.
     * @throws EntityNotFoundException if the event with the given ID does not exist.
     */
    public EventsEntity getEvent(int eventID) throws EntityNotFoundException {
        logger.info("Retrieving event with ID: {}", eventID);
        Optional<EventsEntity> optionalEvent = eventRepository.findEventByEventId(eventID); // Check if the event exists
        if (optionalEvent.isPresent()) {
            logger.info("Event with ID: {} found", eventID);
            return optionalEvent.get(); // Return the event
        } else {
            logger.error("Event with ID: {} does not exist", eventID);
            throw new EntityNotFoundException("Event with ID " + eventID + " does not exist");
        }
    }

    /**
     * Retrieves all events from the database that are associated with a specific group.
     * @param groupID The ID of the group to retrieve events for.
     * @return A list of all events associated with the specified group.
     */
    @Transactional
    public ArrayList<EventsEntity> getEventsByGroup(int groupID) throws EntityNotFoundException {
        logger.info("Retrieving events for group with ID: {}", groupID);
        Optional<ArrayList<EventsEntity>> optionalEvents = eventRepository.findEventsByGroupId(groupID); // Check if there are any events in a certain group
        if (optionalEvents.isPresent()) {
            logger.info("Found {} events for group with ID: {}", optionalEvents.get().size(), groupID);
            return optionalEvents.get();
        } else {
            logger.error("No events found for group with ID: {}", groupID);
            throw new EntityNotFoundException("No events found for the group");
        }
    }

    /**
     * Retrieves all events from the database that are associated with a specific user.
     * @return A list of all events associated with the specified user.
     */
    @Transactional
    public List<EventsEntity> getAllAvailableEvents() throws EntityNotFoundException {
        logger.info("Retrieving all available events");
        List<EventsEntity> events = eventRepository.findAll();

        events.removeIf(event -> event.getEventEndTime().isEqual(LocalDateTime.now()) ||
                        event.getCapacity().equals(event.getCount()) ||
                        event.getStatus() != EventStatus.Scheduled);
        logger.info("Found {} available events", events.size());
        return events;
    }

    /**
     * Deletes an event entity with the specified event ID.
     * @param event The event entity to delete.
     * @throws AccessException if the user does not have permission to delete the event.
     */
    @Transactional
    public void deleteEvent(EventsEntity event, String userId) throws AccessException, EntityNotFoundException {
        logger.info("Deleting event with ID: {}", event.getEventId());
        Optional<GroupUsersEntity> member = groupUsersService.getGroupUser(event.getGroupId(), userId);
        if (member.isEmpty()) {
            throw new AccessException("You are not a member of this group");
        } if(!member.get().getRole().equals("ADMIN")) {
            throw new AccessException("You do not have enough permissions");
        }
        eventRepository.delete(event);
        logger.info("Deleted event with ID: {}", event.getEventId());
    }

    /**
     * Checks if the event is valid.
     * @param event The event DTO containing event information.
     * @param userID The ID of the user making the request.
     * @throws AccessException if the event is invalid.
     * @throws IllegalArgumentException if the event is invalid.
     */
    public void checkEvent(EventDTO event, String userID) throws AccessException, IllegalArgumentException {
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
        
        Optional<GroupUsersEntity> member = groupUsersService.getGroupUser(event.getGroupID(), userID);
        if (!member.isPresent()) {
            throw new AccessException("You are not a member of this group");
        } if(!member.get().getRole().equals("ADMIN")) {
            throw new AccessException("You do not have enough permissions");
        }
    }
}