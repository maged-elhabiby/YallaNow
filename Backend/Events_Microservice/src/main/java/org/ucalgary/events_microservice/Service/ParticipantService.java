package org.ucalgary.events_microservice.Service;

import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;
import org.ucalgary.events_microservice.Repository.ParticipantRepository;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Entity.ParticipantEntity;
import org.ucalgary.events_microservice.DTO.ParticipantStatus;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.DTO.ParticipantDTO;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing ParticipantEntity objects.
 * This class provides methods to add, update, retrieve, and delete participants.
 */
@Service
public class ParticipantService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public ParticipantService(EventRepository eventRepository,ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    /**
     * Adds a participant to an event.
     * @param user The participant DTO containing participant information.
     * @return The newly created participant entity.
     * @throws IllegalStateException if the event has reached its capacity or if the participant already exists.
     */
    @Transactional
    public ParticipantEntity addParticipantToEvent(ParticipantDTO user) {
        Optional<ParticipantEntity> checkDup = participantRepository.findByUserIdAndEvent_EventId(user.getUserid(), user.getEventid());
        if (checkDup.isPresent()) {
            return updateParticipant(user);
        }

        // Retrieve the event
        EventsEntity event = eventRepository.findById(user.getEventid())
                .orElseThrow(() -> new EntityNotFoundException("Event with ID " + user.getEventid() + " not found"));

        // Check if the event has reached its capacity
        if (event.getCapacity() != null && getParticipantCount(user.getEventid()) > event.getCapacity()) {
            throw new IllegalStateException("Event has reached its capacity. Cannot add more participants.");
        }

        // Create a new participant entity and associate it with the event
        ParticipantEntity participant = new ParticipantEntity(user.getUserid(),
                                                            user.getParticipantStatus(), 
                                                            event);

        updateEventCount(event, user.getParticipantStatus(), user.getParticipantStatus());

        return participantRepository.save(participant);
    }

    /**
     * Retrieves the status of a participant for an event.
     * @param userId The ID of the user to retrieve the status for.
     * @param eventId The ID of the event to retrieve the status for.
     * @return The status of the participant for the event.
     * @throws EntityNotFoundException if the participant with the given user ID and event ID does not exist.
     */
    @Transactional
    public ParticipantStatus getParticipantStatus(int userId, int eventId) {
        Optional<ParticipantEntity> participant = participantRepository.findByUserIdAndEvent_EventId(userId, eventId);
        if (participant.isPresent()) {
            return participant.get().getParticipantStatus();
        } else {
            throw new EntityNotFoundException("Participant with ID" + userId + 
                                "did not sign up for Event with ID " + eventId);
        }
    }

    /**
     * Retrieves events associated with a user.
     * @param userId The ID of the user to retrieve events for.
     * @return A list of events associated with the user and their statuses.
     */
    @Transactional
    public List<Map<String, Object>> getEventsForParticipant(int userId) {
        List<ParticipantEntity> participantEntities = participantRepository.findAllByUserId(userId);

        return participantEntities.stream().map(participant -> { // Map the participant entities to a list of event and status data
            EventsEntity event = participant.getEvent();
            ParticipantStatus status = participant.getParticipantStatus();
            return Map.of("event", event, "status", status);
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves all participants for an event.
     * @param eventID The ID of the event to retrieve participants for.
     * @return A list of participant entities associated with the event.
     */
    @Transactional
    public ArrayList<ParticipantEntity> getAllEventParticipants(int eventID) {
        Optional<ArrayList<ParticipantEntity>> optionalParticipants = participantRepository.findAllByEvent_EventId(eventID);
        return optionalParticipants.orElseGet(ArrayList::new);
    }

    /**
     * Retrieves all participants and their statuses for an event.
     * @param event The event DTO containing event information.
     * @return A list of participants and their statuses associated with the event.
     */
    @Transactional
    public List<Map<Integer, String>> getAllParticipantsForEvent(EventDTO event) {
        ArrayList<ParticipantEntity> participants = getAllEventParticipants(event.getEventID());
        return participants.stream().map(participant -> Map.of(participant.getUserId(), participant.getParticipantStatus().toString())).collect(Collectors.toList());
    }

    /**
     * Retrieves the count of participants attending an event.
     * @param eventID The ID of the event to retrieve participant count for.
     * @return The count of participants attending the event.
     */
    @Transactional
    public int getParticipantCount(int eventID) {
        int count = 0;
        ArrayList<ParticipantEntity> participants = getAllEventParticipants(eventID);
        for (ParticipantEntity participant : participants) {
            if (participant.getParticipantStatus() == ParticipantStatus.Attending ||
                participant.getParticipantStatus() == ParticipantStatus.Maybe) {
                count++;
            }
        }
        return count;
    }

    /**
     * Updates the status of a participant for an event.
     * @param oldParticipant The participant DTO containing the old participant information.
     * @return The updated participant entity.
     */
    @Transactional
    public ParticipantEntity updateParticipant(ParticipantDTO oldParticipant) {
        Optional<ParticipantEntity> optionalParticipant = participantRepository.findByUserIdAndEvent_EventId(
                oldParticipant.getUserid(),
                oldParticipant.getEventid()
        );

        return optionalParticipant.map(participant -> {
            // Update event count based on participant status changes
            ParticipantStatus newStatus = oldParticipant.getParticipantStatus();
            updateEventCount(participant.getEvent(), participant.getParticipantStatus(), newStatus);

            // Update participant fields
            participant.setParticipantStatus(newStatus);
            return participantRepository.save(participant);
        }).orElseGet(() -> addParticipantToEvent(oldParticipant));
    }


    /**
     * Deletes a participant from an event.
     * @param userID The ID of the user to delete as a participant.
     * @param eventID The ID of the event from which to delete the participant.
     * @throws EntityNotFoundException if the participant with the given user ID and event ID does not exist.
     */
    @Transactional
    public void deleteParticipant(int userID, int eventID) {
        Optional<ParticipantEntity> participant = participantRepository.findByUserIdAndEvent_EventId(userID, eventID);

        participant.ifPresentOrElse(
                p -> participantRepository.deleteById(p.getParticipantId()),
                () -> {
                    throw new EntityNotFoundException("Participant with user ID " + userID + " and event ID " + eventID + " does not exist");
                }
        );
    }


    /**
     * Updates the count of participants for an event based on status changes.
     * @param event The event for which to update the count.
     * @param oldStatus The old status of the participant.
     * @param newStatus The new status of the participant.
     * @throws IllegalStateException if the event has reached its capacity.
     */
    private void updateEventCount(EventsEntity event, ParticipantStatus oldStatus, ParticipantStatus newStatus) {
        if (newStatus != oldStatus) { // If the status has changed

            // If the new status is not attending and the old status is attending or maybe then decrease count
            if (newStatus == ParticipantStatus.NotAttending &&  
                (oldStatus == ParticipantStatus.Attending || 
                oldStatus == ParticipantStatus.Maybe)) {
                    
                event.setCount(event.getCount() - 1);
            }
            // If the new status is attending or maybe and the old status is not attending then increase count
            else if (oldStatus == ParticipantStatus.NotAttending && 
                (newStatus == ParticipantStatus.Attending || 
                newStatus == ParticipantStatus.Maybe)) {

                if(event.getCapacity() != null && event.getCount() + 1 > event.getCapacity()){ // Check if the event has reached its capacity
                    throw new IllegalStateException("Event has reached its capacity. Cannot add more participants.");
                }
                event.setCount(event.getCount() + 1);
            }
            eventRepository.save(event); // Save the updated event
        }
    }
}

