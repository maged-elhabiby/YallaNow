package org.ucalgary.events_microservice.Controller;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/microservice/events")
public class EventsController {
    @PostMapping("/MakeEvent")
    public void makeEvent(@RequestBody EventDTO event) {
        // TODO implement here
    }
    @PostMapping("/UpdateEvent")
    public void updateEvent(@RequestBody EventDTO event) {
        // TODO implement here
    }
    @PostMapping("/DeleteEvent")
    public void deleteEvent(@RequestBody EventDTO event) {
        // TODO implement here
    }

    @GetMapping("/RetrieveEvent/{eventID}")
    public void retrieveEvent(@PathVariable int eventID) {
        // TODO implement here
    }
    
    
    

}
