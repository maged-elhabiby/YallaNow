package org.ucalgary.images_microservice.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class for ImageEntity Responsible for handling the database operations of the ImageEntity
 */
@Entity
@Table(name = "image_table")
public class ImageEntity {

    // attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;
    @Column(name = "image_link")
    private String imageLink;

    // constructors
    protected ImageEntity() {}

    public ImageEntity(String imageLink)throws IllegalArgumentException {
        if(imageLink == null || imageLink.isEmpty()) {
            throw new IllegalArgumentException("Image link cannot be null or empty");
        }
        this.imageLink = imageLink;
    }

    public ImageEntity(int imageId, String imageLink)throws IllegalArgumentException {
        if (imageLink == null || imageLink.isEmpty()) {
            throw new IllegalArgumentException("Image link cannot be null or empty");
        }
        this.imageId = imageId;
        this.imageLink = imageLink;
    }

    // getters and setters
    public int getImageId() {return imageId;}
    public String getImageLink() {return imageLink;}

    public void setImageId(int imageId) {this.imageId = imageId;}
    public void setImageLink(String imageLink) {this.imageLink = imageLink;}
}
