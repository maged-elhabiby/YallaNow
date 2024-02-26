package main.java.org.ucalgary.images_microservice.Repository;

import main.java.org.ucalgary.images_microservice.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
