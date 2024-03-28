package org.ucalgary.imagesService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ucalgary.imagesService.Entity.ImageEntity;
import java.util.Optional;

/**
 * Repository for ImageEntity Responsible for handling the database operations of the ImageEntity
 */
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    Optional<ImageEntity> findByImageLink(String imageLink); // Find an ImageEntity by the image_link
}
