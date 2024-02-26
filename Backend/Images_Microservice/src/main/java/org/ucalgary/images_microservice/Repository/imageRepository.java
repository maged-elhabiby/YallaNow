package org.ucalgary.images_microservice.Repository;

import org.ucalgary.images_microservice.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
