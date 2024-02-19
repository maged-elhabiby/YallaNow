package org.ucalgary.events_microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucalgary.events_microservice.Entity.EventsEntity;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventsEntity, Integer>{
    Optional<EventsEntity> findEventByEventId(Integer eventID);
}
