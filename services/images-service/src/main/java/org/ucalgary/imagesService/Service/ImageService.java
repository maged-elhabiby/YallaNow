package org.ucalgary.imagesService.Service;

import com.cloudinary.*;

import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import org.ucalgary.imagesService.Repository.ImageRepository;
import org.ucalgary.imagesService.Entity.ImageEntity;
import org.ucalgary.imagesService.DTO.ImageBase64DTO;
import org.ucalgary.imagesService.DTO.ImageDTO;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
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
    private final Cloudinary cloudinary;


    public ImageService(ImageRepository imageRepository, Cloudinary cloudinary) {
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;
    }

    /**
     * Adds an image to the database
     * @param imageDTO
     * @return ImageEntity
     * @throws IllegalArgumentException
     */
    public ImageEntity addImage(ImageDTO imageDTO) throws IllegalArgumentException{
        isValidLink(imageDTO.getImageLink()); // Check if the link is valid
        Optional<ImageEntity> optionalImage = imageRepository.findByImageLink(imageDTO.getImageLink());
        if (optionalImage.isPresent()) {return updateImage(imageDTO);} // If the ImageEntity is found, update it

        ImageEntity image = new ImageEntity(imageDTO.getImageLink()); // Create an ImageEntity
        return imageRepository.save(image); // Save the ImageEntity to the database
    }

    /**
     * Uploads an image to the database
     * @param base64Image
     * @return ImageEntity
     * @throws IOException
     */
    public ImageEntity uploadImage(ImageBase64DTO base64Image) throws IOException {
        // Upload image to Cloudinary
        Map<?, ?> uploadResult = cloudinary.uploader().upload("data:image/jpeg;base64," + base64Image.getBase64Image(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        // Create ImageEntity and save to database
        ImageEntity imageEntity = new ImageEntity(imageUrl);
        return imageRepository.save(imageEntity);
    }

    /**
     * Gets an image from the database
     * @param imageId
     * @return ImageEntity
     * @throws EntityNotFoundException
     */
    public ImageEntity getImage(int imageId) throws EntityNotFoundException {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId); // Get the ImageEntity from the database using the imageID
        if (optionalImage.isPresent()) {
            return optionalImage.get(); // If the ImageEntity is found, return it
        }
        else {
            throw new EntityNotFoundException("Image not found"); // If the ImageEntity is not found, throw an exception
        }
    }

    /**
     * Updates an image in the database
     * @param imageDTO
     * @return ImageEntity
     * @throws IllegalArgumentException
     */
    public ImageEntity updateImage(ImageDTO imageDTO)throws IllegalArgumentException {
        isValidLink(imageDTO.getImageLink()); // Check if the link is valid
        Optional<ImageEntity> optionalImage = imageRepository.findByImageLink(imageDTO.getImageLink()); // Get the ImageEntity from the database using the imageID
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
     * @throws EntityNotFoundException
     */
    public void deleteImage(int imageId)throws EntityNotFoundException {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId); // Get the ImageEntity from the database using the imageID
        if (optionalImage.isPresent()) {
            ImageEntity image = optionalImage.get(); 
            imageRepository.delete(image); // If the ImageEntity is found, delete it
        }
        else {
            throw new EntityNotFoundException("Image not found"); // If the ImageEntity is not found, throw an exception
        }
    }

    /**
     * Checks if the link is valid
     * @param link
     * @throws IllegalArgumentException
     */
    public static void isValidLink(String link) throws IllegalArgumentException {
        if(link == null || link.isEmpty()) {throw new IllegalArgumentException("Link is empty");} // Check if the link is empty
        Pattern pattern = Pattern.compile(LINK_REGEX); 
        Matcher matcher = pattern.matcher(link); // Match the link with the regex pattern

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Link is not valid");
        }
    }
    
}
