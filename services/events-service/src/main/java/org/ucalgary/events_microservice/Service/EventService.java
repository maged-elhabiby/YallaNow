package org.ucalgary.events_microservice.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Entity.GroupUsersEntity;
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
        checkEvent(event, userID); // Check if the event is valid       

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
                            throws AccessException, IllegalArgumentException{
                                
        EventsEntity oldEvent;
        try {
            oldEvent = getEvent(updatedEvent.getEventID()); // Check if the event exists
        } catch (EntityNotFoundException e) {
            return createEvent(updatedEvent, new AddressEntity(), userID); // If the event does not exist, create it
        }

        checkEvent(updatedEvent, userID); // Check if the event is valid

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
     * @param eventID The ID of the event entity to retrieve.
     * @return The event entity with the specified ID.
     * @throws EntityNotFoundException if the event with the given ID does not exist.
     */
    public EventsEntity getEvent(int eventID) throws EntityNotFoundException {
        Optional<EventsEntity> optionalEvent = eventRepository.findEventByEventId(eventID); // Check if the event exists
        if (optionalEvent.isPresent()) {
            return optionalEvent.get(); // Return the event
        } else {
            throw new EntityNotFoundException("Event with ID " + eventID + " does not exist");
        }
    }

    /**
     * Retrieves all events from the database that are associated with a specific group.
     * @param groupID The ID of the group to retrieve events for.
     * @return A list of all events associated with the specified group.
     */
    @Transactional
    public ArrayList<EventsEntity> getEventsByGroup(int groupID) {
        Optional<ArrayList<EventsEntity>> optionalEvents = eventRepository.findEventsByGroupId(groupID); // Check if there are any events in a certain group
        return optionalEvents.orElse(null);
    }

    /**
     * Retrieves all events from the database that are associated with a specific user.
     * @return A list of all events associated with the specified user.
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
     * @param event The event entity to delete.
     * @throws AccessException if the user does not have permission to delete the event.
     */
    @Transactional
    public void deleteEvent(EventsEntity event, String userId)throws AccessException, EntityNotFoundException {
        Optional<GroupUsersEntity> member = groupUsersService.getGroupUser(event.getGroupId(), userId);
        if (!member.isPresent()) {
            throw new AccessException("You are not a member of this group");
        } if(!member.get().getRole().equals("ADMIN")) {
            throw new AccessException("You do not have enough permissions");
        }
        eventRepository.delete(event);
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