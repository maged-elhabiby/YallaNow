package org.ucalgary.events_microservice.Service;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.DTO.ParticipantDTO;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Entity.ParticipantEntity;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Repository.ParticipantRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ParticipantService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public ParticipantService(EventRepository eventRepository,ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

   @Transactional
    public ParticipantEntity addParticipantToEvent(ParticipantDTO user) {

        // Retrieve the event
        EventsEntity event = eventRepository.findById(user.getEventid())
                .orElseThrow(() -> new EntityNotFoundException("Event with ID " + user.getEventid() + " not found"));

        // Create a new participant entity and associate it with the event
        ParticipantEntity participant = new ParticipantEntity(user.getUserid(),
                                                              user.getParticipantStatus(), 
                                                              event);

        return participantRepository.save(participant);
    }

//    public static void updateParticipant(int participantID, int eventID){
//         ParticipantEntity participant = participantRepository.findById(participantID)
//            .orElseThrow(() -> new RuntimeException("Participant not found with id: " + participantID));
//        participant.setEventID(eventID);
//        participantRepository.save(participant);
//    }

//    public static void getParticipant(int participantID, int eventID){
//        participantRepository.findById(participantID)
//            .orElseThrow(() -> new RuntimeException("Participant not found with id: " + participantID));
//    }

//    public static void deleteParticipant(int participantID, int eventID){
//        if (participantRepository.existsById(participantID)) {
//            participantRepository.deleteById(participantID);
//        } else {
//            throw new RuntimeException("Participant not found with id: " + participantID);
//        }
//    }
}
