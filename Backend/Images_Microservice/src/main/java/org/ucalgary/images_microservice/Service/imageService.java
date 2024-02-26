package main.java.org.ucalgary.images_microservice.Service;

import main.java.org.ucalgary.images_microservice.Entity.Image;
import main.java.org.ucalgary.images_microservice.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image addImage(String imageLink) {
        Image image = new Image(imageLink);
        return imageRepository.save(image);
    }

    public Image getImage(int imageId) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        return optionalImage.orElse(null);
    }

    public Image updateImage(int imageId, String newImageLink) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            image.setImageLink(newImageLink);
            return imageRepository.save(image);
        }
        return null;
    }

    public void deleteImage(int imageId) {
        imageRepository.deleteById(imageId);
    }
    
}
