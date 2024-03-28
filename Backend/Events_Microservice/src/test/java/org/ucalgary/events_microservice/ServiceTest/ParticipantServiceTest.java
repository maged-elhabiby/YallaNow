package org.ucalgary.events_microservice.ServiceTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.ucalgary.events_microservice.DTO.*;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Entity.ParticipantEntity;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Repository.ParticipantRepository;
import org.ucalgary.events_microservice.Service.*;

import org.junit.jupiter.api.*;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("serviceTest")
public class ParticipantServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventsPubService eventsPubService;

    @Mock
    private MailSenderService mailSenderService;

    @InjectMocks
    private ParticipantService participantService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Tests for AddParticipant method")
    class AddNewParticipants {

        @Test
        @DisplayName("Add new participant to event successfully")
        void testAddParticipantToEvent_Success() {
            // Create a valid participant DTO
            ParticipantDTO participantDTO = createValidParticipantDTO();

            // Create an existing event
            EventsEntity mockEvent = createExistingEvent();
            mockEvent.setEventId(1); // Set the event ID to match the one in the participantDTO

            when(participantRepository.findByUserIdAndEvent_EventId(anyString(), anyInt()))
                    .thenReturn(Optional.empty());
            when(eventRepository.findEventByEventId(eq(1))).thenReturn(Optional.of(mockEvent)); // Match event ID 1

            when(participantRepository.save(any())).thenReturn(new ParticipantEntity());

            // Call the method
            assertThrows(
                    EntityNotFoundException.class,
                    () -> participantService.addParticipantToEvent(participantDTO,"test@example.com", "Test User", mockEvent));
        }        

        @Test
        @DisplayName("Handle event capacity reached")
        void testAddParticipantToEvent_CapacityReached() {
            // Create a valid participant DTO
            ParticipantDTO participantDTO = createValidParticipantDTO();

            // Create an existing event at capacity
            EventsEntity mockEvent = createExistingEvent();
            mockEvent.setCount(mockEvent.getCapacity()); // Set count equal to capacity

            when(participantRepository.findByUserIdAndEvent_EventId(anyString(), anyInt()))
                    .thenReturn(Optional.empty());
            when(eventRepository.findById(anyInt())).thenReturn(Optional.of(mockEvent));

            // Call the method and expect an IllegalStateException
            assertThrows(IllegalStateException.class, () ->
                    participantService.addParticipantToEvent(participantDTO, "test@example.com", "Test User", mockEvent));
        }

        // Test adding a participant to an event
        @Test
        public void testAddParticipantToEvent() {
            // Create a valid ParticipantDTO object using the helper function
            ParticipantDTO participantDTO = createValidParticipantDTO();

            // Mock the participantRepository to return an empty Optional (participant does not exist)
            when(participantRepository.findByUserIdAndEvent_EventId(participantDTO.getUserid(), participantDTO.getEventid()))
                    .thenReturn(Optional.empty());

            // Mock the eventRepository to return an existing EventsEntity object using the createExistingEvent helper function
            EventsEntity existingEvent = createExistingEvent();
            when(eventRepository.findById(participantDTO.getEventid())).thenReturn(Optional.of(existingEvent));

            // Create a ParticipantService object
            ParticipantService participantService = new ParticipantService(eventRepository, participantRepository, eventsPubService, mailSenderService);

            // Call the addParticipantToEvent method and assert the result
            assertDoesNotThrow(() -> {
                ParticipantEntity participant = participantService.addParticipantToEvent(participantDTO, "test@example.com", "Test User", existingEvent);
                Assertions.assertNotNull(participant);
            });
        }
    }

    @Nested
    @DisplayName("Tests for UpdateParticipant method")
    class UpdateParticipants {
        @Test
        @DisplayName("Update existing participant")
        void testUpdateParticipant_Success() {
            // Create a valid participant DTO
            ParticipantDTO participantDTO = createValidParticipantDTO();

            // Create an existing participant and event
            ParticipantEntity existingParticipant = createExistingParticipant(createExistingEvent());
            EventsEntity mockEvent = createExistingEvent();

            // Mock the necessary methods
            when(participantRepository.findByUserIdAndEvent_EventId(anyString(), anyInt())).thenReturn(Optional.of(existingParticipant));
            when(participantRepository.save(any())).thenReturn(existingParticipant);

            // Call the method under test
            ParticipantEntity result = participantService.addParticipantToEvent(participantDTO, "test@example.com", "Test User", mockEvent);

            // Assertions
            assertNotNull(result);
            assertEquals("123", result.getUserId());
            assertEquals(ParticipantStatus.Attending, result.getParticipantStatus());
            verify(eventRepository, never()).findById(anyInt()); // Make sure event retrieval is not called
        }
    }

    // Test retrieving participant status
    @Test
    public void testGetParticipantStatus() {
        // Create a valid ParticipantDTO object using the helper function
        ParticipantDTO participantDTO = createValidParticipantDTO();

        // Mock the participantRepository to return a ParticipantEntity object with the specified status
        when(participantRepository.findByUserIdAndEvent_EventId(participantDTO.getUserid(), participantDTO.getEventid()))
                .thenReturn(Optional.of(new ParticipantEntity(participantDTO.getUserid(), participantDTO.getParticipantStatus(), new EventsEntity())));

        // Create a ParticipantService object
        ParticipantService participantService = new ParticipantService(eventRepository, participantRepository, eventsPubService, mailSenderService);

        // Call the getParticipantStatus method and assert the returned status
        assertDoesNotThrow(() -> {
            ParticipantStatus status = participantService.getParticipantStatus(participantDTO.getUserid(), participantDTO.getEventid());
            Assertions.assertEquals(participantDTO.getParticipantStatus(), status);
        });
    }

    // Test retrieving events for a participant when no events are registered
    @Test
    public void testGetEventsForParticipant_NoEventsRegistered() {
        // Mock the participantRepository to return an empty list of ParticipantEntity objects
        when(participantRepository.findAllByUserId("user123")).thenReturn(Collections.emptyList());

        // Create a ParticipantService object
        ParticipantService participantService = new ParticipantService(eventRepository, participantRepository, eventsPubService, mailSenderService);

        // Call the getEventsForParticipant method and assert that it throws EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> participantService.getEventsForParticipant("user123"));
    }

    // Test retrieving all participants for an event when no participants are found
    @Test
    public void testGetAllEventParticipants_NoParticipantsFound() {
        // Mock the participantRepository to return an empty Optional
        when(participantRepository.findAllByEvent_EventId(1)).thenReturn(Optional.empty());

        // Create a ParticipantService object
        ParticipantService participantService = new ParticipantService(eventRepository, participantRepository, eventsPubService, mailSenderService);

        // Call the getAllEventParticipants method and assert that it returns an empty list
        List<ParticipantEntity> participants = participantService.getAllEventParticipants(1);
        assertTrue(participants.isEmpty());
    }


    // Test deleting a participant when the participant does not exist
    @Test
    public void testDeleteParticipant_ParticipantNotExists() {
        // Mock the participantRepository to return an empty Optional (participant does not exist)
        when(participantRepository.findByUserIdAndEvent_EventId("user123", 1)).thenReturn(Optional.empty());

        // Create a ParticipantService object
        ParticipantService participantService = new ParticipantService(eventRepository, participantRepository, eventsPubService, mailSenderService);

        // Call the deleteParticipant method and assert that it throws EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> participantService.deleteParticipant("user123", 1));
    }

    // ************************************************************************************************

    // Helper method to create a valid ParticipantDTO for testing
    private ParticipantDTO createValidParticipantDTO() {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setUserid("123");
        participantDTO.setEventid(1);
        participantDTO.setParticipantStatus(ParticipantStatus.Attending);
        return participantDTO;
    }

    private ParticipantEntity createExistingParticipant(EventsEntity event) {
        ParticipantEntity existingParticipant = new ParticipantEntity();
        existingParticipant.setParticipantId(1);
        existingParticipant.setUserId("123");
        existingParticipant.setEvent(event);
        existingParticipant.setParticipantStatus(ParticipantStatus.Attending);
        return existingParticipant;
    }

    private EventsEntity createExistingEvent() {
        EventsEntity existingEvent = new EventsEntity();
        AddressEntity address = new AddressEntity(1, "Street", "City",
                "Province", "T2M 4W7", "Country");

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
    
}
