package org.ucalgary.events_service.ServiceTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.AccessException;
import org.springframework.test.context.ActiveProfiles;
import org.ucalgary.events_service.DTO.*;
import org.ucalgary.events_service.Entity.AddressEntity;
import org.ucalgary.events_service.Entity.EventsEntity;
import org.ucalgary.events_service.Entity.GroupUsersEntity;
import org.ucalgary.events_service.Repository.AddressRepository;
import org.ucalgary.events_service.Repository.EventRepository;
import org.ucalgary.events_service.Repository.GroupUserRespository;
import org.ucalgary.events_service.Repository.ParticipantRepository;
import org.ucalgary.events_service.Service.*;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("serviceTest")
public class EventsTest {
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
    @DisplayName("EventDTO Tests")
    class EventDTOTests {

        @Test
        @DisplayName("Test EventDTO Constructor")
        void testEventDTOConstructor() {
            // Create test data
            int eventID = 1;
            int groupID = 1;
            String eventTitle = "Test Event";
            String eventDescription = "Description";
            AddressDTO location = new AddressDTO("Street", "City", "Province", "Postal Code", "Country");
            LocalDateTime eventStartTime = LocalDateTime.now();
            LocalDateTime eventEndTime = LocalDateTime.now().plusHours(1);
            EventStatus status = EventStatus.Scheduled;
            int count = 0;
            int capacity = 100;
            int imageID = 1;

            // Create EventDTO object using constructor
            EventDTO eventDTO = new EventDTO(eventID, groupID, eventTitle, eventDescription,
                    location, eventStartTime, eventEndTime, status, count, capacity, imageID);
            eventDTO.setLocation(location);

            // Assertions
            Assertions.assertNotNull(eventDTO);
            Assertions.assertEquals(eventID, eventDTO.getEventID());
            Assertions.assertEquals(groupID, eventDTO.getGroupID());
            Assertions.assertEquals(eventTitle, eventDTO.getEventTitle());
            Assertions.assertEquals(eventDescription, eventDTO.getEventDescription());
            Assertions.assertEquals(location, eventDTO.getLocation());
            Assertions.assertEquals(eventStartTime, eventDTO.getEventStartTime());
            Assertions.assertEquals(eventEndTime, eventDTO.getEventEndTime());
            Assertions.assertEquals(status, eventDTO.getStatus());
            Assertions.assertEquals(count, eventDTO.getCount());
            Assertions.assertEquals(capacity, eventDTO.getCapacity());
            Assertions.assertEquals(imageID, eventDTO.getImageID());
        }

        @Test
        @DisplayName("Test EventDTO Setters and Getters")
        void testEventDTOSettersAndGetters() {
            // Create EventDTO object
            EventDTO eventDTO = new EventDTO();

            // Set values using setters
            eventDTO.setEventID(1);
            eventDTO.setGroupID(1);
            eventDTO.setEventTitle("Test Event");
            eventDTO.setEventDescription("Description");
            AddressDTO location = new AddressDTO(1, "Street", "City", "Province", "Postal Code", "Country");
            eventDTO.setLocation(location);
            LocalDateTime eventStartTime = LocalDateTime.now();
            LocalDateTime eventEndTime = LocalDateTime.now().plusHours(1);
            eventDTO.setEventStartTime(eventStartTime);
            eventDTO.setEventEndTime(eventEndTime);
            eventDTO.setStatus(EventStatus.Scheduled);
            eventDTO.setCount(0);
            eventDTO.setCapacity(100);
            eventDTO.setImageID(1);

            // Assertions using getters
            Assertions.assertEquals(1, eventDTO.getEventID());
            Assertions.assertEquals(1, eventDTO.getGroupID());
            Assertions.assertEquals("Test Event", eventDTO.getEventTitle());
            Assertions.assertEquals("Description", eventDTO.getEventDescription());
            Assertions.assertEquals(location, eventDTO.getLocation());
            Assertions.assertEquals(eventStartTime, eventDTO.getEventStartTime());
            Assertions.assertEquals(eventEndTime, eventDTO.getEventEndTime());
            Assertions.assertEquals(EventStatus.Scheduled, eventDTO.getStatus());
            Assertions.assertEquals(0, eventDTO.getCount());
            Assertions.assertEquals(100, eventDTO.getCapacity());
            Assertions.assertEquals(1, eventDTO.getImageID());
        }
    }

     // ********************************************************************************************************************

    @Nested
    @DisplayName("EventsEntity Tests")
    class EventsEntityTests {

        @Test
        @DisplayName("Test EventsEntity Constructor")
        void testEventsEntityConstructor() {
            // Create test data
            int eventId = 1;
            int groupId = 1;
            String eventTitle = "Test Event";
            String eventDescription = "Description";
            int locationId = 1;
            LocalDateTime eventStartTime = LocalDateTime.now();
            LocalDateTime eventEndTime = LocalDateTime.now().plusHours(1);
            EventStatus status = EventStatus.Scheduled;
            int count = 0;
            int capacity = 100;
            int imageId = 1;

            // Create EventsEntity object using constructor
            EventsEntity eventsEntity = new EventsEntity(eventId, groupId, eventTitle, eventDescription,
                    locationId, eventStartTime, eventEndTime, status, count, capacity, imageId);

            // Assertions
            Assertions.assertNotNull(eventsEntity);
            Assertions.assertEquals(eventId, eventsEntity.getEventId());
            Assertions.assertEquals(groupId, eventsEntity.getGroupId());
            Assertions.assertEquals(eventTitle, eventsEntity.getEventTitle());
            Assertions.assertEquals(eventDescription, eventsEntity.getEventDescription());
            Assertions.assertEquals(locationId, eventsEntity.getLocationId());
            Assertions.assertEquals(eventStartTime, eventsEntity.getEventStartTime());
            Assertions.assertEquals(eventEndTime, eventsEntity.getEventEndTime());
            Assertions.assertEquals(status, eventsEntity.getStatus());
            Assertions.assertEquals(count, eventsEntity.getCount());
            Assertions.assertEquals(capacity, eventsEntity.getCapacity());
            Assertions.assertEquals(imageId, eventsEntity.getImageId());
        }

        @Test
        @DisplayName("Test EventsEntity Setters and Getters")
        void testEventsEntitySettersAndGetters() {
            // Create EventsEntity object
            EventsEntity eventsEntity = new EventsEntity();

            // Set values using setters
            eventsEntity.setEventId(1);
            eventsEntity.setGroupId(1);
            eventsEntity.setEventTitle("Test Event");
            eventsEntity.setEventDescription("Description");
            eventsEntity.setLocationId(1);
            LocalDateTime eventStartTime = LocalDateTime.now();
            LocalDateTime eventEndTime = LocalDateTime.now().plusHours(1);
            eventsEntity.setEventStartTime(eventStartTime);
            eventsEntity.setEventEndTime(eventEndTime);
            eventsEntity.setStatus(EventStatus.Scheduled);
            eventsEntity.setCount(0);
            eventsEntity.setCapacity(100);
            eventsEntity.setImageId(1);

            // Assertions using getters
            Assertions.assertEquals(1, eventsEntity.getEventId());
            Assertions.assertEquals(1, eventsEntity.getGroupId());
            Assertions.assertEquals("Test Event", eventsEntity.getEventTitle());
            Assertions.assertEquals("Description", eventsEntity.getEventDescription());
            Assertions.assertEquals(1, eventsEntity.getLocationId());
            Assertions.assertEquals(eventStartTime, eventsEntity.getEventStartTime());
            Assertions.assertEquals(eventEndTime, eventsEntity.getEventEndTime());
            Assertions.assertEquals(EventStatus.Scheduled, eventsEntity.getStatus());
            Assertions.assertEquals(0, eventsEntity.getCount());
            Assertions.assertEquals(100, eventsEntity.getCapacity());
            Assertions.assertEquals(1, eventsEntity.getImageId());
        }

        @Test
        @DisplayName("Test EventsEntity Validation")
        void testEventsEntityValidation() {
            // Create EventsEntity object
            EventsEntity eventsEntity = new EventsEntity();

            // Set invalid values
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setEventTitle(""));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setEventDescription(""));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setLocationId(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setEventStartTime(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setEventEndTime(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setStatus(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setCount(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setCapacity(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> eventsEntity.setCount(101)); // count > capacity
        }
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

