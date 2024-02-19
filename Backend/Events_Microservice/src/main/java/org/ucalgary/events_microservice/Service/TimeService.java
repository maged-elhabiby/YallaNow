package org.ucalgary.events_microservice.Service;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Entity.TimeEntity;
import org.ucalgary.events_microservice.Repository.TimeRepository;

import jakarta.transaction.Transactional;
@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
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

    @Transactional
    public TimeEntity updateStartTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getStartTimeID(),
                                           event.getEventStartTime().getHour(),
                                           event.getEventStartTime().getMinute());        
        return timeRepository.save(newTime);
    }

    @Transactional
    public TimeEntity updateEndTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getEndTimeID(),
                                           event.getEventEndTime().getHour(),
                                           event.getEventEndTime().getMinute());        
        return timeRepository.save(newTime);
    }


    @Transactional
    public void deleteTime(int timeID){
        if (timeRepository.existsById(timeID)) {
            timeRepository.deleteById(timeID);
        } else {
            throw new RuntimeException("Time not found with id: " + timeID);
        }
    }
}
