package org.ucalgary.imagesService.DTO;

/**
 * DTO class for ImageEntity Responsible for handling the data transfer of the ImageEntity
 */
public class ImageDTO {
    // attributes
    private int imageId;
    private String imageLink;

    // constructors
    public ImageDTO(int imageId, String imageLink)throws IllegalArgumentException {
        if(imageLink == null || imageLink.isEmpty()) {
            throw new IllegalArgumentException("Image link cannot be null or empty");
        }
        this.imageId = imageId;
        this.imageLink = imageLink;
    }

    public ImageDTO(String imageLink)throws IllegalArgumentException {
        if(imageLink == null || imageLink.isEmpty()) {
            throw new IllegalArgumentException("Image link cannot be null or empty");
        }
        this.imageLink = imageLink;
    }

    // getters and setters
    public final int getImageId() {return imageId;}
    public final String getImageLink() {return imageLink;}

    public void setImageId(final int imageId) {this.imageId = imageId;}
    public void setImageLink(final String imageLink) {this.imageLink = imageLink;}
}
