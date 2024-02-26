package org.ucalgary.images_microservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ucalgary.images_microservice.DTO.ImageDTO;
import org.ucalgary.images_microservice.Entity.ImageEntity;
import org.ucalgary.images_microservice.Service.ImageService;

@RestController
@RequestMapping("/microservice/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/AddImage")
    public ResponseEntity<?> addImage(@RequestBody ImageDTO imageDTO) {
        ImageEntity image = imageService.addImage(imageDTO);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/GetImage/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable int imageId) {
        ImageEntity image = imageService.getImage(imageId);
        return ResponseEntity.ok(image);
    }


    @PostMapping("/UpdateImage")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO) {
        ImageEntity image = imageService.updateImage(imageDTO.getImageId(), imageDTO.getImageLink());
        return ResponseEntity.ok(image);
    }

    @GetMapping("/DeleteImage/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable int imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("Image deleted");
    }
}
