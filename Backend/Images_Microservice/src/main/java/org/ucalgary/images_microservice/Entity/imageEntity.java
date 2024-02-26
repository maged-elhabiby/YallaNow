package main.java.org.ucalgary.images_microservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;
    private String imageLink;

    protected Image() {}

    public Image(String imageLink) {
        this.imageLink = imageLink;
    }

    public Image(int imageId, String imageLink) {
        this.imageId = imageId;
        this.imageLink = imageLink;
    }

    public int getImageId() {return imageId;}
    public String getImageLink() {return imageLink;}

    public void setImageId(int imageId) {this.imageId = imageId;}
    public void setImageLink(String imageLink) {this.imageLink = imageLink;}
}
