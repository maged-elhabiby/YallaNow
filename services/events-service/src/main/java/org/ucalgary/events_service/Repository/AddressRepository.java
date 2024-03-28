package org.ucalgary.events_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucalgary.events_service.Entity.AddressEntity;

/**
 * Repository interface for managing AddressEntity objects in the database.
 * This interface provides methods to perform CRUD operations on AddressEntity objects.
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{
}
