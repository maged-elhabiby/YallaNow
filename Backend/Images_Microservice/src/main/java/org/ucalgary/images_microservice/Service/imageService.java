package org.ucalgary.images_microservice.Service;

import org.ucalgary.images_microservice.Repository.ImageRepository;
import org.ucalgary.images_microservice.Entity.ImageEntity;
import org.ucalgary.images_microservice.DTO.ImageDTO;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Optional;

/**
 * Service class for ImageEntity Responsible for handling the business logic of the ImageEntity
 */
@Service
public class ImageService {
    private static final String LINK_REGEX = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$";
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Adds an image to the database
     * @param imageDTO
     * @return ImageEntity
     */
    public ImageEntity addImage(ImageDTO imageDTO) {
        isValidLink(imageDTO.getImageLink()); // Check if the link is valid
        Optional<ImageEntity> optionalImage = imageRepository.findByImageLink(imageDTO.getImageLink());
        if (optionalImage.isPresent()) {updateImage(imageDTO);} // If the ImageEntity is found, update it

        ImageEntity image = new ImageEntity(imageDTO.getImageLink()); // Create an ImageEntity
        return imageRepository.save(image); // Save the ImageEntity to the database
    }

    /**
     * Gets an image from the database using ID
     * @param imageId
     * @return ImageEntity
     */
    public ImageEntity getImage(int imageId) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId); // Get the ImageEntity from the database using the imageID
        if (optionalImage.isPresent()) {
            return optionalImage.get(); // If the ImageEntity is found, return it
        }
        else {
            throw new IllegalArgumentException("Image not found"); // If the ImageEntity is not found, throw an exception
        }
    }

    /**
     * Updates an image in the database
     * @param imageDTO
     * @return ImageEntity
     */
    public ImageEntity updateImage(ImageDTO imageDTO) {
        isValidLink(imageDTO.getImageLink()); // Check if the link is valid
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageDTO.getImageId()); // Get the ImageEntity from the database using the imageID
        if (optionalImage.isPresent()) {
            ImageEntity image = optionalImage.get(); // If the ImageEntity is found, update it
            image.setImageLink(imageDTO.getImageLink());
            return imageRepository.save(image);

        } else {
            return addImage(imageDTO); // If the ImageEntity is not found, add it
        }
    }    

    /**
     * Deletes an image from the database
     * @param imageId
     */
    public void deleteImage(int imageId) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId); // Get the ImageEntity from the database using the imageID
        if (optionalImage.isPresent()) {
            ImageEntity image = optionalImage.get(); 
            imageRepository.delete(image); // If the ImageEntity is found, delete it
        }
        else {
            throw new IllegalArgumentException("Image not found"); // If the ImageEntity is not found, throw an exception
        }
    }

    /**
     * Checks if the link is valid
     * @param link
     */
    public static void isValidLink(String link) {
        if(link == null || link.isEmpty()) {throw new IllegalArgumentException("Link is empty");} // Check if the link is empty
        Pattern pattern = Pattern.compile(LINK_REGEX); 
        Matcher matcher = pattern.matcher(link); // Match the link with the regex pattern

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Link is not valid");
        }
    }
    
}
