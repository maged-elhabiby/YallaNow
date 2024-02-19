package org.ucalgary.events_microservice.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/microservice/events")
public class EventsController {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/AddEvent")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO event) {
        EventsEntity events = eventService.createEvent(event);
        return ResponseEntity.ok(events);
    }   

    @GetMapping("/GetEvent/{eventID}")
    public ResponseEntity<?> retrieveEvent(@PathVariable int eventID) {
        EventsEntity events = eventService.getEvent(eventID);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/UpdateEvent")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO event) {
        EventsEntity events = eventService.updateEvent(event);
        return ResponseEntity.ok(events);
    }

    // @DeleteMapping("/DeleteEvent")
    // public void deleteEvent(@RequestBody int event) {
    //     EventService.deleteEvent(event);
    // }
}
