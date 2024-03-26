package org.ucalgary.events_microservice.ServiceTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.ucalgary.events_microservice.DTO.AddressDTO;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.DTO.EventStatus;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Repository.AddressRepository;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Service.AddressService;
import org.ucalgary.events_microservice.Service.EventService;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private EventService eventService;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository, addressService);
    }

    @Nested
    @DisplayName("Tests for createEvent method")
    class CreateEventTests {

        @Test
        void testCreateEvent() {
            MockitoAnnotations.openMocks(this);

            // Mock the behavior of addressService.createAddress
            EventDTO event = createValidEventDTO();
            AddressDTO address = new AddressDTO("Street", "City", "Province", "PostalCode", "Country");
            event.setLocation(address);

            AddressEntity mockAddress = new AddressEntity(1, "Street", "City", "Province", "PostalCode", "Country");
            EventsEntity mockEvent = new EventsEntity(1, 1, "Event Title", "Event Description", mockAddress.getAddressId(),
                    event.getEventStartTime(), event.getEventEndTime(), EventStatus.Scheduled, 0, 100, 1);

            when(eventRepository.save(any(EventsEntity.class))).thenReturn(mockEvent);

            // Test the createEvent method
            EventsEntity createdEvent = eventService.createEvent(event, mockAddress);

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
            assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(eventDTO, addressEntity));
        }
    }

    // ********************************************************************************************************************

    @Nested
    @DisplayName("Tests for updateEvent method")
    class UpdateEventTests {

        @Test
        @DisplayName("Update existing event")
        void testUpdateExistingEvent() {
            // Arrange
            EventDTO updatedEventDTO = createValidEventDTO();
            EventsEntity existingEvent = createExistingEvent();
            AddressEntity updatedAddressEntity = new AddressEntity(1, "Street", "City", "Province", "T2M 4W7", "Country");

            // Mock the behavior of eventRepository
            when(eventRepository.save(existingEvent)).thenReturn(existingEvent);
            when(eventRepository.findEventByEventId(updatedEventDTO.getEventID())).thenReturn(Optional.of(existingEvent));

            // Act
            EventsEntity updatedEvent = eventService.updateEvent(updatedEventDTO, updatedAddressEntity);

            assertThat(updatedEvent).isNotNull();
            assertThat(updatedEvent).isEqualTo(existingEvent);
            // Add specific assertions for updated event
        }


        @Test
        @DisplayName("Fail to update non-existing event")
        void testFailUpdateNonExistingEvent() {
            // Mock the behavior of eventRepository to return Optional.empty() when searching for any event ID
            when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.empty());

            // Act and Assert: Expect IllegalStateException to be thrown when trying to update a non-existing event
            assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(createInvalidEventDTO(), new AddressEntity()));

        }

    }

    @Test
    @DisplayName("Fail to update event with invalid data")
    void testUpdateInvalidEvent() {
        // Arrange
        EventDTO invalidEventDTO = createInvalidEventDTO();
        EventsEntity existingEvent = createExistingEvent();
        when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.of(existingEvent));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(invalidEventDTO, new AddressEntity()));
    }

    @Test
    @DisplayName("Fail to update event due to AddressService failure")
    void testUpdateEventAddressServiceFailure() {
        // Arrange
        EventDTO validEventDTO = createValidEventDTO();
        EventsEntity existingEvent = createExistingEvent();
        when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.of(existingEvent));
        when(addressService.updateAddress(validEventDTO)).thenThrow(IllegalStateException.class);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(createInvalidEventDTO(), new AddressEntity()));
    }

    @Test
    @DisplayName("Create new event when updating non-existing event with invalid data")
    void testUpdateNonExistingEventInvalidData() {
        // Arrange
        EventDTO invalidEventDTO = createInvalidEventDTO();
        when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(invalidEventDTO, new AddressEntity()));
        verify(eventRepository, never()).save(any());
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
            // Arrange
            when(eventRepository.findEventByEventId(anyInt())).thenReturn(Optional.empty());

            // Act and Assert
            assertThrows(EntityNotFoundException.class, () -> eventService.deleteEvent(1));
        }
    }

    // ********************************************************************************************************************

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
        event.setEventStartTime(LocalDateTime.now());
        event.setEventEndTime(LocalDateTime.now().plusHours(1));
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

