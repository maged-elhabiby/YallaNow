package org.ucalgary.images_microservice.DTO;

/**
 * DTO class for ImageEntity Responsible for handling the data transfer of the ImageEntity
 */
public class ImageDTO {
    // attributes
    private int imageId;
    private String imageLink;

    // constructors
    public ImageDTO(int imageId, String imageLink) {
        this.imageId = imageId;
        this.imageLink = imageLink;
    }

    public ImageDTO(String imageLink) {
        this.imageLink = imageLink;
    }

    // getters and setters
    public final int getImageId() {return imageId;}
    public final String getImageLink() {return imageLink;}

    public void setImageId(final int imageId) {this.imageId = imageId;}
    public void setImageLink(final String imageLink) {this.imageLink = imageLink;}
}
