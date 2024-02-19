package org.ucalgary.events_microservice.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_microservice.Entity.ParticipantEntity;
import org.ucalgary.events_microservice.Service.ParticipantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/microservice/events")
public class ParticipantController {

   @PostMapping("/AddParticipant")
   public void addParticipant(@RequestBody ParticipantEntity participant) {
       ParticipantService.addParticipant(participant);
   }

   @GetMapping("/GetParticipant/{userID}/{eventID}")
   public void GetParticipant(@PathVariable int participantID, @PathVariable int eventID) {
       ParticipantService.getParticipant(participantID, eventID);
   }

//    @PostMapping("/UpdateParticipant")
//    public void updateParticipant(@RequestBody int participantID, int eventID) {
//        ParticipantService.updateParticipant(participantID, eventID);
//    }

   @DeleteMapping("/DeleteParticipant/{userID}/{eventID}")
   public void deleteParticipant(@RequestBody int participantID, @RequestBody int eventID) {
       ParticipantService.deleteParticipant(participantID, eventID);
   }
}
