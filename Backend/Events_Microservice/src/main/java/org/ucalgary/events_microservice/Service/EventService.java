package org.ucalgary.events_microservice.Service;



import java.util.Optional;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Entity.TimeEntity;
import org.ucalgary.events_microservice.Repository.EventRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TimeService timeService;
    private final AddressService addressService;

    public EventService(EventRepository eventRepository, AddressService addressService, TimeService timeService) {
        this.eventRepository = eventRepository;
        this.timeService = timeService;
        this.addressService = addressService;
    }

    @SuppressWarnings("null")
    @Transactional
    public EventsEntity createEvent(EventDTO event) {
        
        AddressEntity address = addressService.createAddress(event);
        TimeEntity startTime = timeService.createStartTime(event);
        TimeEntity endTime = timeService.createEndTime(event);

        EventsEntity newEvent = new EventsEntity(event.getEventID(),
                                                event.getGroupID(),
                                                event.getEventTitle(), 
                                                event.getEventDescription(), 
                                                address.getAddressId(),
                                                event.getEventDate(),
                                                startTime.getTimeID(),
                                                endTime.getTimeID(),
                                                event.getStatus(), 
                                                event.getPoll(),
                                                event.getCapacity());
        return eventRepository.save(newEvent);
    }

    @Transactional
    public EventsEntity updateEvent(EventDTO updatedEvent) {
        EventsEntity oldEvent = null;
        try {
            oldEvent = getEvent(updatedEvent.getEventID());
            if (oldEvent == null) {
                throw new IllegalStateException("Event" + updatedEvent.getEventID() + "does not exist");
            }
        } catch (IllegalStateException e) {
            return createEvent(updatedEvent);
        }
        
        AddressEntity newAddress = addressService.updateAddress(updatedEvent);
        TimeEntity newStartTime = timeService.updateStartTime(updatedEvent);
        TimeEntity newEndTime = timeService.updateEndTime(updatedEvent);

        oldEvent.setGroupId(updatedEvent.getGroupID());
        oldEvent.setEventTitle(updatedEvent.getEventTitle());
        oldEvent.setEventDescription(updatedEvent.getEventDescription());
        oldEvent.setLocationId(newAddress.getAddressId());
        oldEvent.setEventDate(updatedEvent.getEventDate());
        oldEvent.setEventStartTimeId(newStartTime.getTimeID());
        oldEvent.setEventEndTimeId(newEndTime.getTimeID());
        oldEvent.setStatus(updatedEvent.getStatus());
        oldEvent.setPoll(updatedEvent.getPoll());
        oldEvent.setCapacity(updatedEvent.getCapacity());

        return eventRepository.save(oldEvent);
    }

    public EventsEntity getEvent(int event) {
        Optional<EventsEntity> optionalEvent = eventRepository.findEventByEventId(event);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
    }

    // public static void deleteEvent(int eventID) {
    //     if (eventRepository.existsById(eventID)){
    //         eventRepository.deleteById(eventID);
    //     }else{
    //         throw new IllegalStateException("Event" + eventID +  "does not exist");
    //     }
    // }
}
