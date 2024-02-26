package org.ucalgary.images_microservice.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int image_id;
    private String image_link;

    protected ImageEntity() {}

    public ImageEntity(String image_link) {
        this.image_link = image_link;
    }

    public ImageEntity(int image_id, String image_link) {
        this.image_id = image_id;
        this.image_link = image_link;
    }

    public int getImageId() {return image_id;}
    public String getImageLink() {return image_link;}

    public void setImageId(int image_id) {this.image_id = image_id;}
    public void setImageLink(String image_link) {this.image_link = image_link;}
}
