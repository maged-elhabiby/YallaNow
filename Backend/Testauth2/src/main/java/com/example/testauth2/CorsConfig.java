package com.example.testauth2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // allow all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // allowed HTTP methods
                .allowedHeaders("*"); // allowed headers

    }
}
// Compare this snippet from src/main/java/com/example/testauth2/MyInterceptor.java: