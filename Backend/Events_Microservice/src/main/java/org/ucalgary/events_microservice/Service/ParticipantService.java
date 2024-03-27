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
    private final EventsPubService eventsPubService;
    private final MailSenderService mailSenderService;

    public ParticipantService(EventRepository eventRepository,ParticipantRepository participantRepository, 
                                EventsPubService eventsPubService, MailSenderService mailSenderService) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.eventsPubService = eventsPubService;
        this.mailSenderService = mailSenderService;
    }

    /**
     * Adds a participant to an event.
     * @param user The participant DTO containing user and event information.
     * @return The participant entity added to the event.
     * @throws IllegalArgumentException if the participant already exists.
     */
    @Transactional
    public ParticipantEntity addParticipantToEvent(ParticipantDTO user)throws IllegalArgumentException, EntityNotFoundException {
        Optional<ParticipantEntity> checkDup = participantRepository.findByUserIdAndEvent_EventId(user.getUserid(), user.getEventid());
        if (checkDup.isPresent()) {
            return updateParticipant(user);
        }

        // Retrieve the event
        EventsEntity event = eventRepository.findById(user.getEventid())
                .orElseThrow(() -> new EntityNotFoundException("Event with ID " + user.getEventid() + " not found"));

        // Create a new participant entity and associate it with the event
        ParticipantEntity participant = new ParticipantEntity(user.getUserid(),
                                                            user.getParticipantStatus(), 
                                                            event);

        updateEventCount(event, user.getParticipantStatus());

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
    public ParticipantStatus getParticipantStatus(String userId, int eventId) throws EntityNotFoundException{
        Optional<ParticipantEntity> participant = participantRepository.findByUserIdAndEvent_EventId(userId, eventId);
        if (participant.isPresent()) {
            return participant.get().getParticipantStatus();
        } else {
            throw new EntityNotFoundException("Participant not found");
        }
    }

    /**
     * Retrieves all events for a participant.
     * @param userId The ID of the user to retrieve events for.
     * @return A list of event and status data for the participant.
     */
    @Transactional
    public List<Map<String, Object>> getEventsForParticipant(String userId)throws EntityNotFoundException {
        List<ParticipantEntity> participantEntities = participantRepository.findAllByUserId(userId);
        if (participantEntities.isEmpty()) {
            throw new EntityNotFoundException("No Events Registered");
        }
        return participantEntities.stream().map(participant -> { // Map the participant entities to a list of event and status data
            EventsEntity event = participant.getEvent();
            ParticipantStatus status = participant.getParticipantStatus();
            return Map.of("event", event, "status", status);
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves all participants for an event.
     * @param eventID The ID of the event to retrieve participants for.
     * @return A list of participants associated with the event.
     */
    @Transactional
    public ArrayList<ParticipantEntity> getAllEventParticipants(int eventID) {
        Optional<ArrayList<ParticipantEntity>> optionalParticipants = participantRepository.findAllByEvent_EventId(eventID);
        return optionalParticipants.orElseGet(ArrayList::new);
    }

    /**
     * Retrieves all participants for an event.
     * @param event The event DTO to retrieve participants for.
     * @return A list of participants associated with the event.
     */
    @Transactional
    public List<Map<String, String>> getAllParticipantsForEvent(EventDTO event) {
        ArrayList<ParticipantEntity> participants = getAllEventParticipants(event.getEventID());
        return participants.stream().map(participant -> Map.of(participant.getUserId(), participant.getParticipantStatus().toString())).collect(Collectors.toList());
    }

    /**
     * Updates the status of a participant for an event.
     * @param oldParticipant The participant DTO containing the updated status.
     * @return The updated participant entity.
     * @throws IllegalArgumentException if the participant does not exist.
     */
    @Transactional
    public ParticipantEntity updateParticipant(ParticipantDTO oldParticipant)throws IllegalArgumentException {
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
     * @param userID The ID of the user to delete the participant for.
     * @param eventID The ID of the event to delete the participant from.
     * @throws EntityNotFoundException if the participant with the given user ID and event ID does not exist.
     */
    @Transactional
    public void deleteParticipant(String userID, int eventID)throws EntityNotFoundException {
        Optional<ParticipantEntity> participant = participantRepository.findByUserIdAndEvent_EventId(userID, eventID);

        participant.ifPresentOrElse(
                p -> participantRepository.deleteById(p.getParticipantId()),
                () -> {throw new EntityNotFoundException("Event Particiapant not found");}
        );
    }

    /**
     * Updates the count of participants for an event based on status changes.
     * @param event The event for which to update the count.
     * @param newStatus The new status of the participant.
     * @throws IllegalStateException if the event has reached its capacity.
     */
    private void updateEventCount(EventsEntity event, ParticipantStatus newStatus)throws IllegalArgumentException{
        if(newStatus != ParticipantStatus.NotAttending){
            event.setCount(event.getCount() + 1);
            // Check if the event has reached its capacity
            if (event.getCapacity() != null && event.getCount() > event.getCapacity()) {
                throw new IllegalStateException("Event has reached its capacity. Cannot add more participants.");
            }
            eventRepository.save(event);
            mailSenderService.sendMessage(event, "symasc","a.h.b.draco1@gmail.com",newStatus);
            if(event.getCapacity().equals(event.getCount())){ // in the case where after adding the user, the event is full make it not available for publishing
                eventsPubService.publishEvents(event, "UPDATE");
            }
        }
    }

    /**
     * Updates the count of participants for an event based on status changes.
     * @param event The event for which to update the count.
     * @param oldStatus The old status of the participant.
     * @param newStatus The new status of the participant.
     * @throws IllegalStateException if the event has reached its capacity.
     */
    private void updateEventCount(EventsEntity event, ParticipantStatus oldStatus, ParticipantStatus newStatus) throws IllegalArgumentException {
        if (newStatus != oldStatus) { // If the status has changed

            // If the new status is not attending and the old status is attending or maybe then decrease count
            if (newStatus == ParticipantStatus.NotAttending &&  
                (oldStatus == ParticipantStatus.Attending || 
                oldStatus == ParticipantStatus.Maybe)) {
                
                if(event.getCapacity().equals(event.getCount())){ 
                    event.setCount(event.getCount() - 1);
                    eventsPubService.publishEvents(event, "UPDATE"); // Make the event available for publishing
                }else{
                    event.setCount(event.getCount() - 1);    
                }   
            }
            // If the new status is attending or maybe and the old status is not attending then increase count
            else if (oldStatus == ParticipantStatus.NotAttending && 
                (newStatus == ParticipantStatus.Attending || 
                newStatus == ParticipantStatus.Maybe)) {

                event.setCount(event.getCount() + 1);

                if(event.getCapacity() != null && event.getCount() > event.getCapacity()){ // Check if the event has reached its capacity
                    throw new IllegalStateException("Event has reached its capacity. Cannot add more participants.");
                }
            }
            eventRepository.save(event); // Save the updated event
            mailSenderService.sendMessage(event, "symasc","a.h.b.draco1@gmail.com",newStatus);

            if(event.getCapacity().equals(event.getCount())){ // in the case where after adding the user, the event is full make it not available for publishing
                eventsPubService.publishEvents(event, "UPDATE");
            }
        }
    }
}

