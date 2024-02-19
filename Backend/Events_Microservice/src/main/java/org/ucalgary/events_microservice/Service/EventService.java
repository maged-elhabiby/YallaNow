package org.ucalgary.events_microservice.Service;



import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.ucalgary.events_microservice.Entity.TimeEntity;
import org.ucalgary.events_microservice.Repository.AddressRepository;
import org.ucalgary.events_microservice.Repository.EventRepository;
import org.ucalgary.events_microservice.Repository.TimeRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final TimeRepository timeRepository;

    public EventService(EventRepository eventRepository, AddressRepository AddressRepository, TimeRepository TimeRepository) {
        this.eventRepository = eventRepository;
        this.addressRepository = AddressRepository;
        this.timeRepository = TimeRepository;
    }

    @SuppressWarnings("null")
    @Transactional
    public EventsEntity createEvent(EventDTO event) {
        
        AddressEntity address = createAddress(event);
        TimeEntity startTime = createStartTime(event);
        TimeEntity endTime = createEndTime(event);

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
    public AddressEntity createAddress(EventDTO event){
        AddressEntity newAddress = new AddressEntity(event.getAddressID(),
                                                     event.getLocation().getStreet(),
                                                     event.getLocation().getCity(),
                                                     event.getLocation().getProvince(),
                                                     event.getLocation().getCountry());
        return addressRepository.save(newAddress);
    }

    @Transactional
    public TimeEntity createStartTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getStartTimeID(),
                                           event.getEventStartTime().getHour(),
                                           event.getEventStartTime().getMinute());        
        return timeRepository.save(newTime);
    }

    @Transactional
    public TimeEntity createEndTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getEndTimeID(),
                                event.getEventEndTime().getHour(),
                                event.getEventEndTime().getMinute());
        return timeRepository.save(newTime);
    }

    // @Transactional
    // public void updateEvent(EventDTO updatedEvent) {
    //     int eventID = updatedEvent.getEventID();
    //     if (eventRepository.existsById(eventID)) {
    //         eventRepository.save(updatedEvent);
    //     } else {
    //         throw new IllegalStateException("Event" + eventID +  "does not exist");
    //     }
    // }

    // public static EventDTO getEvent(int event) {
    //     Optional<EventDTO> optionalEvent = eventRepository.findColumnByEventID(event);
    //     if (optionalEvent.isPresent()) {
    //         return optionalEvent.get();
    //     } else {
    //         return null;
    //     }
    // }

    // public static void deleteEvent(int eventID) {
    //     if (eventRepository.existsById(eventID)){
    //         eventRepository.deleteById(eventID);
    //     }else{
    //         throw new IllegalStateException("Event" + eventID +  "does not exist");
    //     }
    // }
}
