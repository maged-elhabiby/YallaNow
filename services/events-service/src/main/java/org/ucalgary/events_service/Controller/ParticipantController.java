package org.ucalgary.events_service.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_service.DTO.EventDTO;
import org.ucalgary.events_service.DTO.ParticipantDTO;
import org.ucalgary.events_service.Entity.EventsEntity;
import org.ucalgary.events_service.Entity.ParticipantEntity;
import org.ucalgary.events_service.Service.EventService;
import org.ucalgary.events_service.Service.ParticipantService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/microservice/events")
public class ParticipantController {

    private final ParticipantService participantService;
    private final EventService eventService;

    public ParticipantController(ParticipantService ParticipantService, EventService eventService) {
        this.participantService = ParticipantService;
        this.eventService = eventService;
    }


    /**
     * Add a participant to an event
     * @param participant
     * @return Response Entity with the object of the participant added
     */
    @PostMapping("/AddParticipant")
    public ResponseEntity<?> addParticipant(@RequestBody ParticipantDTO participant, @RequestAttribute("Id") String userId, 
                                            @RequestAttribute("Email") String email, @RequestAttribute("Name") String name){
        try{
            participant.setUserid(userId);
            EventsEntity event = eventService.getEvent(participant.getEventid());
            ParticipantEntity participants = participantService.addParticipantToEvent(participant, email, name, event);
            return ResponseEntity.ok(participants); // make return 200
        }catch(IllegalArgumentException e){
            return (ResponseEntity<?>) ResponseEntity.status(422);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all events for a participant
     * @param userId
     * @return Response Entity with the list of events for the participant
     */
    @GetMapping("/GetAllUserEvents")
    public ResponseEntity<?> GetEventsForParticipant(@RequestAttribute("Id") String userId) {
        try{
            List<Map<String, Object>> participants = participantService.getEventsForParticipant(userId);
            return ResponseEntity.ok(participants);
        }catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all participants for an event
     * @param event
     * @return Response Entity with the list of participants for the event
     */
    @PostMapping("/GetAllEventParticipants")
    public ResponseEntity<?> getAllParticipantsForEvent(@RequestBody EventDTO event) {
        List<Map<String, String>> participants = participantService.getAllParticipantsForEvent(event);
        return ResponseEntity.ok(participants);
    }

    /**
     * Get the status of a participant for an event
     * @param eventId
     * @param userId
     * @return Response Entity with the status of the participant for the event
     */
    @GetMapping("/GetParticipantStatus/{eventId}")
    public ResponseEntity<?> getParticipantStatus(@PathVariable int eventId, @RequestAttribute("Id") String userId) {
        try{
            String status = participantService.getParticipantStatus(userId, eventId).toString();
            return ResponseEntity.ok(status);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update a participant for an event
     * @param participant
     * @return Response Entity with the object of the participant updated
     */
    @PostMapping("/UpdateParticipant")
    public ResponseEntity<?> updateParticipant(@RequestBody ParticipantDTO participant, @RequestAttribute("Id") String userId,
        @RequestAttribute("Email") String email, @RequestAttribute("Name") String name) {
        try{
            participant.setUserid(userId);
            EventsEntity event = eventService.getEvent(participant.getEventid());
            ParticipantEntity participants = participantService.updateParticipant(participant, email, name, event);
            return ResponseEntity.ok(participants);
        }catch(IllegalArgumentException e){
            return (ResponseEntity<?>) ResponseEntity.status(422);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a participant from an event
     * @param eventId
     * @param userId
     * @return Response Entity with the object of the participant deleted
     */
    @DeleteMapping("/DeleteParticipant/{eventId}")
    public ResponseEntity<?> deleteParticipant(@PathVariable int eventId, @RequestAttribute("Id") String userId) {
        try{
            participantService.deleteParticipant(userId, eventId);
            return (ResponseEntity<?>) ResponseEntity.ok();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
       
    }
}
