package ucalgary.images_microservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.ucalgary.imagesService.DTO.ImageBase64DTO;
import org.ucalgary.imagesService.DTO.ImageDTO;
import org.ucalgary.imagesService.Entity.ImageEntity;
import org.ucalgary.imagesService.Repository.ImageRepository;
import org.ucalgary.imagesService.Service.ImageService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Optional;

public class ImageTests {

    @Nested
    @DisplayName("ImageService Tests")
    class ImageServiceTests {

        @Test
        @DisplayName("Test addImage Method")
        void testAddImage() {
            // Create mock ImageRepository
            ImageRepository mockImageRepository = Mockito.mock(ImageRepository.class);

            // Create test data
            ImageDTO imageDTO = new ImageDTO("https://example.com/image.jpg");
            ImageEntity existingImageEntity = new ImageEntity("https://example.com/image.jpg");

            // Mock repository behavior
            Mockito.when(mockImageRepository.findByImageLink("https://example.com/image.jpg"))
                    .thenReturn(Optional.of(existingImageEntity));

            // Create ImageService object using constructor injection
            ImageService imageService = new ImageService(mockImageRepository, null);

            // Call the method
            ImageEntity result = imageService.addImage(imageDTO);

            // Assertions
            Assertions.assertNull(result); // Since the image already exists, addImage should return null
        }

        @Test
        @DisplayName("Test uploadImage Method")
        void testUploadImage() throws IOException {
            // Create mock ImageRepository
            ImageRepository mockImageRepository = Mockito.mock(ImageRepository.class);
            // create fake cloudinary object
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dumy",
                    "api_key", "dumy",
                    "api_secret", "dumy"));
                    

            // Create test data
            ImageBase64DTO base64Image = new ImageBase64DTO("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");
            ImageEntity imageEntity = new ImageEntity("https://example.com/image.jpg");

            // Mock repository behavior
            Mockito.when(mockImageRepository.save(Mockito.any(ImageEntity.class)))
                    .thenReturn(imageEntity);

            // Create ImageService object using constructor injection
            ImageService imageService = new ImageService(mockImageRepository, cloudinary);

            // Call the method
            assertThrows(RuntimeException.class, () -> imageService.uploadImage(base64Image)); // Should throw IllegalArgumentException
        }

        @Test
        @DisplayName("Test getImage Method")
        void testGetImage() {
            // Create mock ImageRepository
            ImageRepository mockImageRepository = Mockito.mock(ImageRepository.class);

            // Create test data
            ImageEntity imageEntity = new ImageEntity("https://example.com/image.jpg");

            // Mock repository behavior
            Mockito.when(mockImageRepository.findById(1))
                    .thenReturn(Optional.of(imageEntity));

            // Create ImageService object using constructor injection
            ImageService imageService = new ImageService(mockImageRepository, null);

            // Call the method
            ImageEntity result = imageService.getImage(1);

            // Assertions
            Assertions.assertNotNull(result); // The retrieved image entity should not be null
            assertEquals("https://example.com/image.jpg", result.getImageLink()); // The image link should match
        }

        @Test
        @DisplayName("Test deleteImage Method")
        void testDeleteImage() {
            // Create mock ImageRepository
            ImageRepository mockImageRepository = Mockito.mock(ImageRepository.class);

            // Create test data
            ImageEntity imageEntity = new ImageEntity("https://example.com/image.jpg");

            // Mock repository behavior
            Mockito.when(mockImageRepository.findById(1))
                    .thenReturn(Optional.of(imageEntity));

            // Create ImageService object using constructor injection
            ImageService imageService = new ImageService(mockImageRepository, null);

            // Call the method
            Assertions.assertDoesNotThrow(() -> imageService.deleteImage(1)); // Should not throw EntityNotFoundException
        }
    }

    @Nested
    public class ImageBase64DTOTest {

        @Test
        public void testImageBase64DTOConstructor() {
            String base64Image = "SGVsbG8gV29ybGQh"; // Sample base64 image data
            ImageBase64DTO imageBase64DTO = new ImageBase64DTO(base64Image);
            assertEquals(base64Image, imageBase64DTO.getBase64Image());
        }

        @Test
        public void testImageBase64DTOConstructorWithNullArgument() {
            assertThrows(IllegalArgumentException.class, () -> new ImageBase64DTO(null));
        }

        @Test
        public void testImageBase64DTOConstructorWithEmptyArgument() {
            assertThrows(IllegalArgumentException.class, () -> new ImageBase64DTO(""));
        }

    }

    @Nested
    public class ImageDTOTest {

        @Test
        public void testImageDTOConstructorWithIdAndLink() {
            int imageId = 1;
            String imageLink = "https://example.com/image.jpg";
            ImageDTO imageDTO = new ImageDTO(imageId, imageLink);
            assertEquals(imageId, imageDTO.getImageId());
            assertEquals(imageLink, imageDTO.getImageLink());
        }

        @Test
        public void testImageDTOConstructorWithLinkOnly() {
            String imageLink = "https://example.com/image.jpg";
            ImageDTO imageDTO = new ImageDTO(imageLink);
            assertEquals(imageLink, imageDTO.getImageLink());
        }

        @Test
        public void testImageDTOConstructorWithNullLink() {
            assertThrows(IllegalArgumentException.class, () -> new ImageDTO(null));
        }

        @Test
        public void testImageDTOConstructorWithEmptyLink() {
            assertThrows(IllegalArgumentException.class, () -> new ImageDTO(""));
        }

    }

    @Nested
    public class ImageEntityConstructorTests {

        @Test
        public void testImageEntityConstructorWithIdAndLink() {
            int imageId = 1;
            String imageLink = "https://example.com/image.jpg";
            ImageEntity imageEntity = new ImageEntity(imageId, imageLink);
            assertEquals(imageId, imageEntity.getImageId());
            assertEquals(imageLink, imageEntity.getImageLink());
        }

        @Test
        public void testImageEntityConstructorWithLinkOnly() {
            String imageLink = "https://example.com/image.jpg";
            ImageEntity imageEntity = new ImageEntity(imageLink);
            assertEquals(imageLink, imageEntity.getImageLink());
        }

        @Test
        public void testImageEntityConstructorWithNullLink() {
            assertThrows(IllegalArgumentException.class, () -> new ImageEntity(null));
        }

        @Test
        public void testImageEntityConstructorWithEmptyLink() {
            assertThrows(IllegalArgumentException.class, () -> new ImageEntity(""));
        }

    }

    @Nested
    public class ImageEntitySetterTests {

        @Test
        public void testImageEntitySetImageId() {
            ImageEntity imageEntity = new ImageEntity();
            int imageId = 1;
            imageEntity.setImageId(imageId);
            assertEquals(imageId, imageEntity.getImageId());
        }

        @Test
        public void testImageEntitySetImageLink() {
            ImageEntity imageEntity = new ImageEntity();
            String imageLink = "https://example.com/image.jpg";
            imageEntity.setImageLink(imageLink);
            assertEquals(imageLink, imageEntity.getImageLink());
        }

    }
}
