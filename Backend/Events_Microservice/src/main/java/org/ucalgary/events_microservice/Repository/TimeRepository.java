package org.ucalgary.events_microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucalgary.events_microservice.Entity.TimeEntity;

/**
 * Repository interface for managing TimeEntity objects in the database.
 * This interface provides methods to perform CRUD operations on TimeEntity objects.
 */
@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Integer>{

}
