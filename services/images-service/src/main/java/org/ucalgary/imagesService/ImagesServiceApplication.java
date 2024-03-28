package org.ucalgary.imagesService;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImagesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImagesServiceApplication.class, args);
    }

    /**
     * Bean for Cloudinary
     * @return
     */
    @Bean
    public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.configure().load();
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        return new Cloudinary(cloudinaryUrl);
    }
}
