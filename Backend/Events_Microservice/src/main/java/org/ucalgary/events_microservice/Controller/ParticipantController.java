package org.ucalgary.events_microservice.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.DTO.ParticipantDTO;
import org.ucalgary.events_microservice.Entity.ParticipantEntity;
import org.ucalgary.events_microservice.Service.ParticipantService;
import org.springframework.web.bind.annotation.PostMapping;
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

    public ParticipantController(ParticipantService ParticipantService) {
        this.participantService = ParticipantService;
    }


    /**
     * Add a participant to an event
     * @param participant
     * @return Response Entity with the object of the participant added
     */
    @PostMapping("/AddParticipant")
    public ResponseEntity<?>  addParticipant(@RequestBody ParticipantDTO participant) {
        ParticipantEntity participants = participantService.addParticipantToEvent(participant);
        return ResponseEntity.ok(participants);
    }

    /**
     * Get all events for a participant
     * @param userId
     * @return Response Entity with the list of events for the participant
     */
    @GetMapping("/GetAllUserEvents/{userId}")
    public ResponseEntity<?> GetEventsForParticipant(@PathVariable int userId) {
        List<Map<String, Object>> participants = participantService.getEventsForParticipant(userId);
        return ResponseEntity.ok(participants);
    }

    /**
     * Get all participants for an event
     * @param event
     * @return Response Entity with the list of participants for the event
     */
    @PostMapping("/GetAllEventParticipants")
    public ResponseEntity<?> getAllParticipantsForEvent(@RequestBody EventDTO event) {
        List<Map<Integer, String>> participants = participantService.getAllParticipantsForEvent(event);
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/GetParticipantStatus/{userId}/{eventId}")
    public ResponseEntity<?> getParticipantStatus(@PathVariable int userId, @PathVariable int eventId) {
        String status = participantService.getParticipantStatus(userId, eventId).toString();
        return ResponseEntity.ok(status);
    }

    /**
     * Update a participant
     * @param participant
     * @return Response Entity with the object of the participant updated
     */
    @PostMapping("/UpdateParticipant")
    public ResponseEntity<?> updateParticipant(@RequestBody ParticipantDTO participant) {
        ParticipantEntity participants = participantService.updateParticipant(participant);
        return ResponseEntity.ok(participants);
    }

    /**
     * Delete a participant from an event
     * @param userId
     * @param eventId
     * @return Response Entity with a message that the participant has been removed from the event
     */
    @DeleteMapping("/DeleteParticipant/{userId}/{eventId}")
    public ResponseEntity<?> deleteParticipant(@PathVariable int userId, @PathVariable int eventId) {
        participantService.deleteParticipant(userId, eventId);
        return ResponseEntity.ok("User with ID " + userId + " has been removed from Event with ID " + eventId + " successfully.");
    }
}
