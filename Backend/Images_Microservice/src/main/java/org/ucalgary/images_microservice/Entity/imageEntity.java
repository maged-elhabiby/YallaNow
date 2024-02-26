package org.ucalgary.images_microservice.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entity class for ImageEntity Responsible for handling the database operations of the ImageEntity
 */
@Entity
public class ImageEntity {

    // attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int image_id;
    private String image_link;

    // constructors
    protected ImageEntity() {}

    public ImageEntity(String image_link) {
        this.image_link = image_link;
    }

    public ImageEntity(int image_id, String image_link) {
        this.image_id = image_id;
        this.image_link = image_link;
    }

    // getters and setters
    public int getImageId() {return image_id;}
    public String getImageLink() {return image_link;}

    public void setImageId(int image_id) {this.image_id = image_id;}
    public void setImageLink(String image_link) {this.image_link = image_link;}
}
