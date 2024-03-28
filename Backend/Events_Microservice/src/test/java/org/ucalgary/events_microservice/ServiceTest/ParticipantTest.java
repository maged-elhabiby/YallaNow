package org.ucalgary.events_microservice.ServiceTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.AccessException;
import org.springframework.test.context.ActiveProfiles;
import org.ucalgary.events_microservice.DTO.*;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Entity.GroupUsersEntity;
import org.ucalgary.events_microservice.Entity.ParticipantEntity;
import org.ucalgary.events_microservice.Repository.AddressRepository;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Repository.GroupUserRespository;
import org.ucalgary.events_microservice.Repository.ParticipantRepository;
import org.ucalgary.events_microservice.Service.*;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("serviceTest")
public class eventsTest {
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;

    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressService addressService;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantService participantService;
    @Mock
    private GroupUserRespository groupUserRespository;
    @InjectMocks
    private GroupUsersService groupUsersService;


    @Mock
    private EventsPubService eventsPubService;

    @Mock
    private MailSenderService mailSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository, groupUsersService);
    }

    @Nested
    @DisplayName("Tests for createEvent method")
    class CreateEventTests {

        @Test
        void testCreateEvent() throws AccessException {
            MockitoAnnotations.openMocks(this);

            // Mock the behavior of addressService.createAddress
            EventDTO event = createValidEventDTO();
            AddressDTO address = new AddressDTO("Street", "City", "Province", "PostalCode", "Country");
            event.setLocation(address);

            AddressEntity mockAddress = new AddressEntity(1, "Street", "City", "Province", "PostalCode", "Country");
            EventsEntity mockEvent = new EventsEntity(1, 1, "Event Title", "Event Description", mockAddress.getAddressId(),
                    event.getEventStartTime(), event.getEventEndTime(), EventStatus.Scheduled, 0, 100, 1);

            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "ADMIN")));
            when(eventRepository.save(any(EventsEntity.class))).thenReturn(mockEvent);

            // Test the createEvent method
            EventsEntity createdEvent = eventService.createEvent(event, mockAddress, "1");

            // Assert that the createdAddress is not null
            assertNotNull(createdEvent);
        }

        @Test
        @DisplayName("Fail to create event with invalid data")
        void testFailCreateInvalidEvent() {
            // Arrange
            EventDTO eventDTO = createInvalidEventDTO();
            AddressEntity addressEntity = new AddressEntity();
            when(eventRepository.save(any(EventsEntity.class))).thenReturn(new EventsEntity());

            // Act and Assert
            assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(eventDTO, addressEntity, "1"));
        }

        @Test
        @DisplayName("Fail to create event with invalid address")
        void testCreateEventWithPastEventStartTime() {
            MockitoAnnotations.openMocks(this);
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "ADMIN")));
            EventDTO event = createValidEventDTO();
            event.setEventStartTime(LocalDateTime.now().minusHours(1)); // Set past event start time

            AddressEntity address = new AddressEntity(1, "Street", "City", "Province", "PostalCode", "Country");

            // Test that IllegalArgumentException is thrown
            assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(event, address, "1"));
        }

        @Test
        @DisplayName("Fail to create event with invalid permissions")
        void testCreateEventWithInvalidPermissions() {
            MockitoAnnotations.openMocks(this);

            EventDTO event = createValidEventDTO();
            AddressEntity address = new AddressEntity(1, "Street", "City", "Province", "PostalCode", "Country");

            // Mock the behavior of groupUsersService.getGroupUser to return a non-ADMIN role
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "MEMBER")));

            // Test that AccessException is thrown
            assertThrows(AccessException.class, () -> eventService.createEvent(event, address, "1"));
        }

        @Test
        @DisplayName("Fail to create event with non-existing group user")
        void testCreateEventWithNonExistingGroupUser() {
            MockitoAnnotations.openMocks(this);
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "4", "ADMIN")));
            EventDTO event = createValidEventDTO();
            AddressEntity address = new AddressEntity(1, "Street", "City", "Province", "PostalCode", "Country");

            // Mock the behavior of groupUsersService.getGroupUser to return empty Optional
            when(groupUsersService.getGroupUser(anyInt(), anyString())).thenReturn(Optional.empty());

            // Test that EntityNotFoundException is thrown
            assertThrows(AccessException.class, () -> eventService.createEvent(event, address, "1"));
        }
    }

    // ********************************************************************************************************************

    @Nested
    @DisplayName("Tests for updateEvent method")
    class UpdateEventTests {

        @Test
        @DisplayName("Update existing event")
        void testUpdateExistingEvent() throws AccessException {
            // Arrange
            EventDTO updatedEventDTO = createValidEventDTO();
            EventsEntity existingEvent = createExistingEvent();
            AddressEntity updatedAddressEntity = new AddressEntity(1, "Street", "City", "Province", "T2M 4W7", "Country");

            // Mock the behavior of eventRepository
            when(eventRepository.save(existingEvent)).thenReturn(existingEvent);
            when(eventRepository.findEventByEventId(updatedEventDTO.getEventID())).thenReturn(Optional.of(existingEvent));
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "ADMIN")));
            // Act
            EventsEntity updatedEvent = eventService.updateEvent(updatedEventDTO, updatedAddressEntity, "1");

            assertThat(updatedEvent).isNotNull();
            assertThat(updatedEvent).isEqualTo(existingEvent);
            // Add specific assertions for updated event
        }


        @Test
        void testUpdateEventWithInvalidEventID() {
            MockitoAnnotations.openMocks(this);
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "ADMIN")));
            EventDTO updatedEvent = createValidEventDTO();
            AddressEntity newAddress = new AddressEntity(1, "New Street", "New City", "New Province", "New PostalCode", "New Country");

            // Mock the behavior of eventRepository.findById to return empty Optional
            when(eventRepository.findById(1)).thenReturn(Optional.empty());

            // Test that EntityNotFoundException is thrown
            assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(updatedEvent, newAddress, "1"));
        }

        @Test
        void testUpdateEventWithPastEventStartTime() {
            MockitoAnnotations.openMocks(this);
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "ADMIN")));            EventDTO updatedEvent = createValidEventDTO();
            updatedEvent.setEventStartTime(LocalDateTime.now().minusHours(1)); // Set past event start time
            AddressEntity newAddress = new AddressEntity(1, "New Street", "New City", "New Province", "New PostalCode", "New Country");

            // Test that IllegalArgumentException is thrown
            assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(updatedEvent, newAddress, "1"));
        }

        @Test
        void testUpdateEventWithInvalidPermissions() {
            MockitoAnnotations.openMocks(this);

            EventDTO updatedEvent = createValidEventDTO();
            AddressEntity newAddress = new AddressEntity(1, "New Street", "New City", "New Province", "New PostalCode", "New Country");

            // Mock the behavior of groupUsersService.getGroupUser to return a non-ADMIN role
            when(groupUsersService.getGroupUser(anyInt(), anyString()))
                    .thenReturn(Optional.of(new GroupUsersEntity(1, "1", "MEMBER")));

            // Test that AccessException is thrown
            assertThrows(AccessException.class, () -> eventService.updateEvent(updatedEvent, newAddress, "1"));
        }
    }


    // ********************************************************************************************************************

    @Nested
    @DisplayName("Tests for getEvent method")
    class GetEventTests {

        @Test
        @DisplayName("Get existing event")
        void testGetExistingEvent() {
            // Arrange
            EventsEntity existingEvent = createExistingEvent();
            when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.of(existingEvent));

            // Act
            EventsEntity retrievedEvent = eventService.getEvent(1);

            // Assert
            verify(eventRepository).findEventByEventId(anyInt());
            assertThat(retrievedEvent).isNotNull();
            // Add specific assertions for retrieved event
        }

        @Test
        @DisplayName("Fail to get non-existing event")
        void testFailGetNonExistingEvent() {
            // Arrange
            when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.empty());

            // Act and Assert
            assertThrows(EntityNotFoundException.class, () -> eventService.getEvent(1));
        }
    }

    // ********************************************************************************************************************

    @Nested
    @DisplayName("Tests for getEventsByGroup method")
    class GetEventsByGroupTests {

        @Test
        @DisplayName("Get events by group ID")
        void testGetEventsByGroup() {
            // Arrange
            List<EventsEntity> eventsList = createEventsList();
            when(eventRepository.findEventsByGroupId(anyInt())).thenReturn(Optional.of(new ArrayList<>(eventsList)));

            // Act
            ArrayList<EventsEntity> retrievedEvents = eventService.getEventsByGroup(1);

            // Assert
            verify(eventRepository).findEventsByGroupId(anyInt());
            assertThat(retrievedEvents).isNotNull();
            // Add specific assertions for retrieved events
        }
    }

    // ********************************************************************************************************************

    @Nested
    @DisplayName("Tests for getAllAvailableEvents method")
    class GetAllAvailableEventsTests {

        @Test
        @DisplayName("Get all available events")
        void testGetAllAvailableEvents() {
            // Arrange
            List<EventsEntity> allEvents = createEventsList();
            when(eventRepository.findAll()).thenReturn(allEvents);

            // Act
            List<EventsEntity> availableEvents = eventService.getAllAvailableEvents();

            // Assert
            verify(eventRepository).findAll();
            assertThat(availableEvents).isNotNull();
            // Add specific assertions for available events
        }
    }

    // ********************************************************************************************************************

    @Nested
    @DisplayName("Tests for deleteEvent method")
    class DeleteEventTests {

        @Test
        @DisplayName("Fail to delete non-existing event")
        void testFailDeleteNonExistingEvent() {
            EventsEntity fakeEvent = new EventsEntity();
            // Arrange
            when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.empty());

            // Act and Assert
            assertThrows(AccessException.class, () -> eventService.deleteEvent(fakeEvent, "1"));
        }
    }

    // ********************************************************************************************************************
 
    @Nested
    @DisplayName("Tests for AddParticipant method")
    class AddNewParticipants {

        @Test
        void testAddNewParticipantToEvent() throws EntityNotFoundException {

            ParticipantDTO participantDTO = createValidParticipantDTO();
            EventsEntity event = createExistingEvent();

            when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
            when(participantRepository.findByUserIdAndEvent_EventId(participantDTO.getUserid(), participantDTO.getEventid()))
                    .thenReturn(Optional.empty());
            when(participantRepository.save(any(ParticipantEntity.class))).thenReturn(new ParticipantEntity());

            ParticipantEntity newParticipant = participantService.addParticipantToEvent(participantDTO, "test@example.com", "John Doe");

            assertNotNull(newParticipant);
            assertEquals(participantDTO.getUserid(), newParticipant.getUserId());
        }

        @Test
        @DisplayName("Test adding a new participant to a non-existent event")
        void testUpdateExistingParticipant() throws EntityNotFoundException {
            MockitoAnnotations.openMocks(this);

            ParticipantDTO participantDTO = createValidParticipantDTO();
            EventsEntity event = new EventsEntity();
            event.setEventId(1);

            ParticipantEntity existingParticipant = new ParticipantEntity();
            existingParticipant.setUserId(participantDTO.getUserid());
            existingParticipant.setEvent(event);

            when(eventRepository.findById(1)).thenReturn(Optional.of(event));
            when(participantRepository.findByUserIdAndEvent_EventId(participantDTO.getUserid(), participantDTO.getEventid()))
                    .thenReturn(Optional.of(existingParticipant));
            when(participantRepository.save(any(ParticipantEntity.class))).thenReturn(existingParticipant);

            ParticipantEntity updatedParticipant = participantService.addParticipantToEvent(participantDTO, "test@example.com", "John Doe");

            assertNotNull(updatedParticipant);
            assertEquals(participantDTO.getUserid(), updatedParticipant.getUserId());
            // Add more assertions as needed
        }

        @Test
        @DisplayName("Test adding a new participant to a non-existent event")
        void testAddParticipantToNonExistentEvent() {
            MockitoAnnotations.openMocks(this);

            ParticipantDTO participantDTO = createValidParticipantDTO();

            // Mock the behavior of eventRepository.findById to return empty Optional
            when(eventRepository.findById(1)).thenReturn(Optional.empty());

            // Test that EntityNotFoundException is thrown
            assertThrows(EntityNotFoundException.class, () -> participantService.addParticipantToEvent(participantDTO, "test@example.com", "John Doe"));
        }

        @Test
        @DisplayName("Test adding a duplicate participant to an event")
        void testAddDuplicateParticipantToEvent() {
            MockitoAnnotations.openMocks(this);

            ParticipantDTO participantDTO = createValidParticipantDTO();
            EventsEntity event = new EventsEntity();
            event.setEventId(1);

            ParticipantEntity existingParticipant = new ParticipantEntity();
            existingParticipant.setUserId(participantDTO.getUserid());
            existingParticipant.setEvent(event);

            when(eventRepository.findById(1)).thenReturn(Optional.of(event));
            when(participantRepository.findByUserIdAndEvent_EventId(participantDTO.getUserid(), participantDTO.getEventid()))
                    .thenReturn(Optional.of(existingParticipant));

            // Test that IllegalArgumentException is thrown
            assertThrows(IllegalArgumentException.class, () -> participantService.addParticipantToEvent(participantDTO, "test@example.com", "John Doe"));
        }
    }

    // Helper method to create a valid ParticipantDTO for testing
    private ParticipantDTO createValidParticipantDTO() {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setParticipantID(1);
        participantDTO.setUserid("123");
        participantDTO.setEventid(1);
        participantDTO.setParticipantStatus(ParticipantStatus.Attending);
        return participantDTO;
    }

    // Helper methods to create test data
    private EventDTO createValidEventDTO() {
        EventDTO event = new EventDTO();

        AddressDTO address = new AddressDTO();
        address.setStreet("Street");
        address.setCity("City");
        address.setProvince("Province");
        address.setPostalCode("T2M 4W7");
        address.setCountry("Country");

        // Initialize eventDTO with valid data
        event.setEventID(1);
        event.setGroupID(1);
        event.setEventTitle("Event Title");
        event.setEventDescription("Event Description");
        event.setLocation(address);
        event.setEventStartTime(LocalDateTime.now().plusHours(1));
        event.setEventEndTime(LocalDateTime.now().plusHours(2));
        event.setStatus(EventStatus.Scheduled);
        event.setCount(0);
        event.setCapacity(100);
        event.setImageID(1);
        return event;
    }

    private EventDTO createInvalidEventDTO() {
        EventDTO event = new EventDTO();
        // Initialize eventDTO with invalid data
        event.setEventID(1);
        event.setGroupID(1);
        event.setEventTitle(null);
        event.setEventDescription(null);
        event.setLocation(null);
        event.setEventStartTime(LocalDateTime.now());
        event.setEventEndTime(LocalDateTime.now().minusHours(1));
        event.setStatus(EventStatus.Scheduled);
        event.setCount(5);
        event.setCapacity(3);
        return event;
    }

    private EventsEntity createExistingEvent() {
        EventsEntity existingEvent = new EventsEntity();
        AddressEntity address = new AddressEntity();
        address.setAddressId(1);
        address.setStreet("Street");
        address.setCity("City");
        address.setProvince("Province");
        address.setPostalCode("T2M 4W7");
        address.setCountry("Country");

        // Initialize existingEvent with valid data
        existingEvent.setEventId(1);
        existingEvent.setGroupId(1);
        existingEvent.setEventTitle("Event Title");
        existingEvent.setEventDescription("Event Description");
        existingEvent.setLocationId(1);
        existingEvent.setEventStartTime(LocalDateTime.now());
        existingEvent.setEventEndTime(LocalDateTime.now().plusHours(1));
        existingEvent.setStatus(EventStatus.Scheduled);
        existingEvent.setCount(0);
        existingEvent.setCapacity(100);
        existingEvent.setImageId(1);
        return existingEvent;
    }

    private List<EventsEntity> createEventsList() {
        List<EventsEntity> eventsList = new ArrayList<>();
        // Initialize eventsList with multiple events
        for(int i =0; i < 5; i++){
            EventsEntity event = new EventsEntity();
            AddressEntity address = new AddressEntity();
            address.setAddressId(i);
            address.setStreet("Street");
            address.setCity("City");
            address.setProvince("Province");
            address.setPostalCode("T2M 4W7");
            address.setCountry("Country");

            event.setEventId(i);
            event.setGroupId(i);
            event.setEventTitle("Event Title");
            event.setEventDescription("Event Description");
            event.setLocationId(i);
            event.setEventStartTime(LocalDateTime.now());
            event.setEventEndTime(LocalDateTime.now().plusHours(1));
            event.setStatus(EventStatus.Scheduled);
            event.setCount(0);
            event.setCapacity(100);
            event.setImageId(i);
            eventsList.add(event);
        }
        return eventsList;
    }
}

