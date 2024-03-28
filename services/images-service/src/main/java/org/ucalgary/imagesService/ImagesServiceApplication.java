package org.ucalgary.imagesService;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImagesServiceApplication {

    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    public static void main(String[] args) {
        SpringApplication.run(ImagesServiceApplication.class, args);
    }

    /**
     * Bean for Cloudinary
     * @return Cloudinary instance
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(cloudinaryUrl);
    }
}
