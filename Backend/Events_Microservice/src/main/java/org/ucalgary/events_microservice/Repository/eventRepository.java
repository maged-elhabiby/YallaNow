package org.ucalgary.events_microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucalgary.events_microservice.DTO.EventDTO;

import java.util.Optional;

@Repository
public interface eventRepository extends JpaRepository<EventDTO, Integer>{


    Optional<EventDTO> findEventById(Integer event_id);
}
