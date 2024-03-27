package org.example.groups_microservice;

import org.example.groups_microservice.Controller.EventController;
import org.example.groups_microservice.DTO.EventDTO;
import org.example.groups_microservice.Exceptions.EventNotFoundException;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Service.EventService;
import org.example.groups_microservice.Entity.EventEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Controller.GroupsController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEvents() throws GroupNotFoundException {
        // Arrange
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        List<EventEntity> events = new ArrayList<>();
        events.add(new EventEntity(1,1,groupEntity));

        when(eventService.getEvents(groupID)).thenReturn(events);

        // Act
        ResponseEntity<List<EventDTO>> response = eventController.getEvents(groupID);

        // Assert
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetEvent() throws EventNotFoundException {
        // Arrange
        int eventID = 1;
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        EventEntity eventEntity = new EventEntity(1,1,groupEntity);

        when(eventService.getEvent(eventID)).thenReturn(eventEntity);

        // Act
        ResponseEntity<EventDTO> response = eventController.getEvent(eventID);

        // Assert
        assertEquals(eventEntity.getEventID(), response.getBody().getEventID());
    }

    @Test
    public void testDeleteEvent() throws EventNotFoundException {
        // Arrange
        int eventID = 1;

        // Act
        ResponseEntity<Void> response = eventController.deleteEvent(eventID);

        // Assert
        verify(eventService, times(1)).deleteEvent(eventID);
    }

    @Test
    public void testUpdateEvent() throws GroupNotFoundException {
        // Arrange
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventID(1);
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        EventEntity eventEntity = new EventEntity(1,1,groupEntity);
        when(eventService.updateEvent(eventDTO)).thenReturn(eventEntity);

        // Act
        ResponseEntity<EventDTO> response = eventController.updateEvent(eventDTO);

        // Assert
        assertEquals(eventDTO.getEventID(), response.getBody().getEventID());
    }

    @Test
    public void testCreateEvent() throws GroupNotFoundException {
        // Arrange
        EventDTO eventDTO = new EventDTO();
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        EventEntity eventEntity = new EventEntity(1,1,groupEntity);
        when(eventService.createEvent(eventDTO)).thenReturn(eventEntity);

        // Act
        ResponseEntity<EventDTO> response = eventController.createEvent(eventDTO);

        // Assert
        assertEquals(eventEntity.getEventID(), response.getBody().getEventID());
    }
}