package org.ucalgary.imagesService.Controller;

import org.ucalgary.imagesService.DTO.ImageDTO;
import org.ucalgary.imagesService.Entity.ImageEntity;
import org.ucalgary.imagesService.Service.ImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;

/**
 * Controller class for ImageEntity Responsible for handling the API requests of the ImageEntity
 */
@RestController
@RequestMapping("/microservice/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Add an image to the database
     * @param imageDTO
     * @return ResponseEntity<ImageEntity>
     */
    @PostMapping("/AddImage")
    public ResponseEntity<?> addImage(@RequestBody ImageDTO imageDTO) {
        try{
            ImageEntity image = imageService.addImage(imageDTO); // Add image to the database
            return ResponseEntity.ok(image);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get an image from the database
     * @param imageId
     * @return ResponseEntity<ImageEntity>
     */
    @GetMapping("/GetImage/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable int imageId) {
        try{
            ImageEntity image = imageService.getImage(imageId); // Get image from the database
            return ResponseEntity.ok(image);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update an image in the database
     * @param imageDTO
     * @return ResponseEntity<ImageEntity>
     */
    @PostMapping("/UpdateImage")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO) {
        try{
            ImageEntity image = imageService.updateImage(imageDTO); // Update image in the database
            return ResponseEntity.ok(image);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete an image from the database
     * @param imageId
     * @return ResponseEntity<String>
     */
    @GetMapping("/DeleteImage/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable int imageId) {
        try{
            imageService.deleteImage(imageId); // Delete image from the database
            return ResponseEntity.ok("Image deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
