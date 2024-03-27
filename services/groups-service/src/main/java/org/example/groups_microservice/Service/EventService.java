package org.example.groups_microservice.Service;

import jakarta.transaction.Transactional;
import org.example.groups_microservice.Entity.EventEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.DTO.EventDTO;
import org.example.groups_microservice.Exceptions.EventNotFoundException;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Repository.EventRepository;
import org.example.groups_microservice.Repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    final EventRepository eventRepository;
    final GroupRepository groupRepository;

    public EventService(EventRepository eventRepository, GroupRepository groupRepository) {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    public EventEntity createEvent(EventDTO eventsDTO) throws GroupNotFoundException {
        GroupEntity group = groupRepository.findById(eventsDTO.getGroupID())
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + eventsDTO.getGroupID()));
        EventEntity eventEntity = new EventEntity();
        eventEntity.setGlobalEventID(eventsDTO.getGlobalEventID());
        eventEntity.setGroup(group);
        eventEntity.setEventID(eventsDTO.getEventID());
        return eventRepository.save(eventEntity);
    }

    @Transactional
    public void deleteEvent(int eventId) throws EventNotFoundException {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));

        eventRepository.delete(event);
    }

    @Transactional
    public EventEntity getEvent(Integer eventID) {
        return eventRepository.findById(eventID).orElse(null);
    }

    public List<EventEntity> getEvents(Integer groupID) {
        return eventRepository.findByGroupGroupID(groupID);

    }

    public EventEntity getEventById(Integer eventId) throws EventNotFoundException {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }


    @Transactional
    public EventEntity updateEvent(EventDTO eventsDTO) throws GroupNotFoundException {
        GroupEntity group = groupRepository.findById(eventsDTO.getGroupID())
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + eventsDTO.getGroupID()));
        EventEntity eventEntity = eventRepository.findById(eventsDTO.getEventID())
                .orElseThrow(() -> new GroupNotFoundException("Event does not exist with ID: " + eventsDTO.getEventID()));
        eventEntity.setGlobalEventID(eventsDTO.getGlobalEventID());
        eventEntity.setGroup(group);
        eventEntity.setEventID(eventsDTO.getEventID());


        return eventRepository.save(eventEntity);
    }

    @Transactional
    public void updateEvents(GroupEntity groupEntity, List<EventDTO> eventDTOs) throws GroupNotFoundException {
        Map<Integer, EventEntity> existingEventsById = groupEntity.getEvents().stream()
                .filter(event -> event.getEventID() != null)
                .collect(Collectors.toMap(EventEntity::getEventID, event -> event));

        for (EventDTO dto : eventDTOs) {
            if (dto.getEventID() != null) {
                EventEntity existingEvent = existingEventsById.get(dto.getEventID());
                if (existingEvent != null) {
                    mapDtoToEntity(dto, existingEvent);
                } else {
                    // This branch could log an error or throw an exception
                    // if an eventID is provided for a non-existing event.
                    System.err.println("Event with ID " + dto.getEventID() + " not found.");
                }
            } else {
                // For new events (no eventID), create and add to the group
                EventEntity newEvent = new EventEntity();
                mapDtoToEntity(dto, newEvent);
                newEvent.setGroup(groupEntity);
                groupEntity.getEvents().add(newEvent);
            }
        }

        groupRepository.save(groupEntity);
    }
    private void mapDtoToEntity(EventDTO dto, EventEntity eventEntity) {
        eventEntity.setGlobalEventID(dto.getGlobalEventID());
        eventEntity.setEventID(dto.getEventID());

    }
}




