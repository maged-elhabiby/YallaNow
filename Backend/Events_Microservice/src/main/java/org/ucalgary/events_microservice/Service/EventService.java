package org.ucalgary.events_microservice.Service;

import java.util.Optional;

import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Repository.eventRepository;

import jakarta.transaction.Transactional;


public class EventService {
    private static eventRepository eventRepository;

    public EventService(eventRepository eventRepository) {
        EventService.eventRepository = eventRepository;
    }

    @SuppressWarnings("null")
    @Transactional
    public static void createEvent(EventDTO event) {
        eventRepository.save(event);
    }

    @Transactional
    public void updateEvent(EventDTO updatedEvent) {
        int eventId = updatedEvent.getEventID();
        if (eventRepository.existsById(eventId)) {
            eventRepository.save(updatedEvent);
        } else {
            throw new IllegalStateException("Event" + eventId +  "does not exist");
        }
    }

    public static EventDTO getEvent(int event) {
        Optional<EventDTO> optionalEvent = eventRepository.findEventById(event);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
    }

    public static void deleteEvent(int eventID) {
        if (eventRepository.existsById(eventID)){
            eventRepository.deleteById(eventID);
        }else{
            throw new IllegalStateException("Event" + eventID +  "does not exist");
        }
    }

    // @Value("${spring.datasource.url}")
    // private static String url;

    // @Value("${spring.datasource.username}")
    // private static String username;

    // @Value("${spring.datasource.password}")
    // private static String password; 

}
