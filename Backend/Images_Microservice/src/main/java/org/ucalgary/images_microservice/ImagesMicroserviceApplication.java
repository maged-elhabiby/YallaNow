package org.ucalgary.images_microservice;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImagesMicroserviceApplication {

    @Bean
    public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.configure().load();
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        return new Cloudinary(cloudinaryUrl);
    }

    public static void main(String[] args) {
        SpringApplication.run(ImagesMicroserviceApplication.class, args);
    }

}
