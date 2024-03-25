package org.ucalgary.images_microservice.DTO;

public class ImageBase64DTO {
    private String base64Image;

    public ImageBase64DTO() {}

    public ImageBase64DTO(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getBase64Image() { return base64Image;}
    public void setBase64Image(String base64Image) {this.base64Image = base64Image;}
}
