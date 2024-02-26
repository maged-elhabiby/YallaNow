package main.java.org.ucalgary.images_microservice.DTO;


public class imageDTO {
    private int imageId;
    private String imageLink;
    
    private imageDTO() {
    }

    public imageDTO(int imageId, String imageLink) {
        this.imageId = imageId;
        this.imageLink = imageLink;
    }

    public imageDTO(String imageLink) {
        this.imageLink = imageLink;
    }

    public final int getImageId() {return imageId;}
    public final String getImageLink() {return imageLink;}

    public void setImageId(final int imageId) {this.imageId = imageId;}
    public void setImageLink(final String imageLink) {this.imageLink = imageLink;}
}
