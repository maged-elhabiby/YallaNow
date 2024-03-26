package org.example.groups_microservice.Controller;

import org.example.groups_microservice.DTO.EventDTO;
import org.example.groups_microservice.Exceptions.EventNotFoundException;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Service.EventService;
import org.example.groups_microservice.Entity.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/microservice/groups/{groupID}/events")
public class EventController {

    final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(@PathVariable int groupID) {
        List<EventEntity> events = eventService.getEvents(groupID);
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }


    private EventDTO convertToDto(EventEntity eventEntity) {
        EventDTO dto = new EventDTO();
        dto.setEventID(eventEntity.getEventID());
        dto.setEventName(eventEntity.getEventName());
        dto.setGroupID(eventEntity.getGroup().getGroupID());
        dto.setGroupName(eventEntity.getGroup().getGroupName());
        return dto;
    }
    /**
     * getEvent method is used to retrieve a specific event.
     * @param eventID - the ID of the event
     * @return the event entity
     */
    @GetMapping("/{eventID}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable int eventID) {
        EventEntity event = eventService.getEvent(eventID);
        EventDTO eventDTO = convertToDto(event);
        return ResponseEntity.ok(eventDTO);
    }

    /**
     * deleteEvent method is used to delete a specific event.
     * @param eventID - the ID of the event
     * @return the event entity
     * @throws EventNotFoundException if the event does not exist
     */

    @DeleteMapping("/{eventID}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventID) throws EventNotFoundException {
        eventService.deleteEvent(eventID);
        return ResponseEntity.noContent().build();
    }

    /**
     * updateEvent method is used to update a specific event.
     * @param eventDTO - the event object with updated values
     * @return the updated event entity
     * @throws GroupNotFoundException if the group does not exist
     */

    @PutMapping("/{eventID}")
    public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO eventDTO) throws GroupNotFoundException {
        EventEntity event = eventService.updateEvent( eventDTO);
        EventDTO newEventDTO = convertToDto(event);
        return ResponseEntity.ok(newEventDTO);
    }

    /**
     * createEvent method is used to create a new event.
     * @param eventDTO - the event object to be created
     * @return the created event entity
     * @throws GroupNotFoundException if the group does not exist
     */

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) throws GroupNotFoundException {
        EventEntity event = eventService.createEvent(eventDTO);
        EventDTO newEventDTO = convertToDto(event);
        return ResponseEntity.ok(newEventDTO);
    }


}
