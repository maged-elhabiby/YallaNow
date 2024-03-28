package org.ucalgary.imagesService.DTO;

public class ImageBase64DTO {
    private String base64Image;

    public ImageBase64DTO() {}

    public ImageBase64DTO(String base64Image)throws IllegalArgumentException {
        if(base64Image == null || base64Image.isEmpty()) {
            throw new IllegalArgumentException("Base64 image cannot be null or empty");
        }
        this.base64Image = base64Image;
    }

    public String getBase64Image() { return base64Image;}
    public void setBase64Image(String base64Image) {this.base64Image = base64Image;}
}
