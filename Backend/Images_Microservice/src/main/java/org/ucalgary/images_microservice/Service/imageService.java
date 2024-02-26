package org.ucalgary.images_microservice.Service;

import org.ucalgary.images_microservice.DTO.ImageDTO;
import org.ucalgary.images_microservice.Entity.ImageEntity;
import org.ucalgary.images_microservice.Repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageEntity addImage(ImageDTO imageDTO) {
        ImageEntity image = new ImageEntity(imageDTO.getImageLink());
        return imageRepository.save(image);
    }

    public ImageEntity getImage(int imageId) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId);
        return optionalImage.orElse(null);
    }

    public ImageEntity updateImage(int imageId, String newImageLink) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            ImageEntity image = optionalImage.get();
            image.setImageLink(newImageLink);
            return imageRepository.save(image);
        } else {
            throw new IllegalArgumentException("Image not found");
        }
    }    

    public void deleteImage(int imageId) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            ImageEntity image = optionalImage.get();
            imageRepository.delete(image);
        }
        else {
            throw new IllegalArgumentException("Image not found");
        }
    }
    
}
