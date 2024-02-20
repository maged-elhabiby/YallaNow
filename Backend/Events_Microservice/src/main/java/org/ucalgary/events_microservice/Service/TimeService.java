package org.ucalgary.events_microservice.Service;

import org.ucalgary.events_microservice.Repository.TimeRepository;
import org.ucalgary.events_microservice.Entity.TimeEntity;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

/**
 * Service class for managing TimeEntity objects.
 * This class provides methods to create, update, and delete time entities.
 */
@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }
    
    /**
     * Creates a new time entity based on the provided event DTO.
     * @param event The event DTO containing time information.
     * @return The newly created time entity.
     */
    @Transactional
    public TimeEntity createStartTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getStartTimeID(),
                                           event.getEventStartTime().getHour(),
                                           event.getEventStartTime().getMinute());        
        return timeRepository.save(newTime);
    }

    /**
     * Creates a new time entity based on the provided event DTO.
     * @param event The event DTO containing time information.
     * @return The newly created time entity.
     */
    @Transactional
    public TimeEntity createEndTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getEndTimeID(),
                                           event.getEventEndTime().getHour(),
                                           event.getEventEndTime().getMinute());        
        return timeRepository.save(newTime);
    }

    /**
     * Updates an existing time entity based on the provided event DTO.
     * @param event The event DTO containing updated time information.
     * @return The updated time entity.
     */
    @Transactional
    public TimeEntity updateStartTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getStartTimeID(),
                                           event.getEventStartTime().getHour(),
                                           event.getEventStartTime().getMinute());        
        return timeRepository.save(newTime);
    }

    /**
     * Updates an existing time entity based on the provided event DTO.
     * @param event The event DTO containing updated time information.
     * @return The updated time entity.
     */
    @Transactional
    public TimeEntity updateEndTime(EventDTO event){
        TimeEntity newTime = new TimeEntity(event.getEndTimeID(),
                                           event.getEventEndTime().getHour(),
                                           event.getEventEndTime().getMinute());        
        return timeRepository.save(newTime);
    }

    /**
     * Deletes a time entity with the specified time ID.
     * @param timeID The ID of the time entity to delete.
     * @throws RuntimeException if the time entity with the given ID is not found.
     */
    @Transactional
    public void deleteTime(int timeID){
        if (timeRepository.existsById(timeID)) {
            timeRepository.deleteById(timeID);
        } else {
            throw new RuntimeException("Time not found with id: " + timeID);
        }
    }
}
