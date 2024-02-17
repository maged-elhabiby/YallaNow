package org.ucalgary.events_microservice.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/microservice/events")
public class EventsController {

    @PostMapping("/AddEvent")
    public void addEvent(@RequestBody EventDTO event) {
        EventService.createEvent(event);
    }   

    @GetMapping("/GetEvent/{eventID}")
    public void retrieveEvent(@PathVariable int eventID) {
        EventService.getEvent(eventID);
    }

    @PostMapping("/UpdateEvent")
    public void updateEvent(@RequestBody EventDTO eventID) {
        EventService.createEvent(eventID);
    }

    @DeleteMapping("/DeleteEvent")
    public void deleteEvent(@RequestBody int event) {
        EventService.deleteEvent(event);
    }
}
