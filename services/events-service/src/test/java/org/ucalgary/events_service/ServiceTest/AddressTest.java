package org.ucalgary.events_service.ServiceTest;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.ucalgary.events_service.DTO.AddressDTO;
import org.ucalgary.events_service.DTO.EventDTO;
import org.ucalgary.events_service.Entity.AddressEntity;
import org.ucalgary.events_service.Repository.AddressRepository;
import org.ucalgary.events_service.Service.AddressService;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("serviceTest")
public class AddressTest {
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Tests for createAddress method")
    class CreateAddressTests {

        @Test
        @DisplayName("Create address successfully")
        void testCreateAddress_Success() {
            // Create a valid event DTO
            EventDTO eventDTO = new EventDTO();
            eventDTO.setLocation(new AddressDTO("Street", "City", "Province", "PostalCode", "Country"));

            // Mock repository behavior
            when(addressRepository.save(any())).thenReturn(new AddressEntity());

            // Call the method
            AddressEntity result = addressService.createAddress(eventDTO);

            // Assertions
            assertNull(result);
        }

        @Test
        @DisplayName("Create address with missing location")
        void testCreateAddress_MissingLocation() {
            // Create an event DTO with null location
            EventDTO eventDTO = new EventDTO();

            // Call the method and expect IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> addressService.createAddress(eventDTO));
        }

        // Add more test cases as needed for edge cases and validation scenarios
    }

    @Nested
    @DisplayName("Tests for updateAddress method")
    class UpdateAddressTests {

        @Test
        @DisplayName("Update existing address successfully")
        void testUpdateAddress_Success() {
            // Create a valid event DTO
            EventDTO eventDTO = new EventDTO();
            eventDTO.setLocation(new AddressDTO("Updated Street", "Updated City", "Updated Province", "Updated PostalCode", "Updated Country"));

            // Mock repository behavior
            when(addressRepository.save(any())).thenReturn(new AddressEntity());

            // Call the method
            AddressEntity result = addressService.updateAddress(eventDTO);

            // Assertions
            assertNull(result);
        }

        @Test
        @DisplayName("Update address with missing location")
        void testUpdateAddress_MissingLocation() {
            // Create an event DTO with null location
            EventDTO eventDTO = new EventDTO();
            // Call the method and expect IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> addressService.updateAddress(eventDTO));
        }

        // Add more test cases as needed for edge cases and validation scenarios
    }

    @Nested
    @DisplayName("Tests for deleteAddress method")
    class DeleteAddressTests {

        @Test
        @DisplayName("Delete non-existing address")
        void testDeleteAddress_NonExistingAddress() {
            // Mock repository behavior
            when(addressRepository.existsById(1)).thenReturn(false);

            // Call the method and expect EntityNotFoundException
            assertThrows(EntityNotFoundException.class, () -> addressService.deleteAddress(1));
        }
    }

    @Nested
    @DisplayName("AddressDTO Tests")
    class AddressDTOTests {

        @Test
        @DisplayName("Test AddressDTO Constructor")
        void testAddressDTOConstructor() {
            // Create test data
            int addressID = 1;
            String street = "123 Main St";
            String city = "Test City";
            String province = "Test Province";
            String postalCode = "12345";
            String country = "Test Country";

            // Create AddressDTO object using constructor
            AddressDTO addressDTO = new AddressDTO(addressID, street, city, province, postalCode, country);

            // Assertions
            Assertions.assertNotNull(addressDTO);
            Assertions.assertEquals(addressID, addressDTO.getAddressID());
            Assertions.assertEquals(street, addressDTO.getStreet());
            Assertions.assertEquals(city, addressDTO.getCity());
            Assertions.assertEquals(province, addressDTO.getProvince());
            Assertions.assertEquals(postalCode, addressDTO.getPostalCode());
            Assertions.assertEquals(country, addressDTO.getCountry());
        }

        @Test
        @DisplayName("Test AddressDTO Setters and Getters")
        void testAddressDTOSettersAndGetters() {
            // Create AddressDTO object
            AddressDTO addressDTO = new AddressDTO();

            // Set values using setters
            addressDTO.setAddressID(1);
            addressDTO.setStreet("123 Main St");
            addressDTO.setCity("Test City");
            addressDTO.setProvince("Test Province");
            addressDTO.setPostalCode("12345");
            addressDTO.setCountry("Test Country");

            // Assertions using getters
            Assertions.assertEquals(1, addressDTO.getAddressID());
            Assertions.assertEquals("123 Main St", addressDTO.getStreet());
            Assertions.assertEquals("Test City", addressDTO.getCity());
            Assertions.assertEquals("Test Province", addressDTO.getProvince());
            Assertions.assertEquals("12345", addressDTO.getPostalCode());
            Assertions.assertEquals("Test Country", addressDTO.getCountry());
        }
    }

    @Nested
    @DisplayName("AddressEntity Tests")
    class AddressEntityTests {

        @Test
        @DisplayName("Test AddressEntity Constructor")
        void testAddressEntityConstructor() {
            // Create test data
            int addressId = 1;
            String street = "123 Main St";
            String city = "Test City";
            String province = "Test Province";
            String postalCode = "12345";
            String country = "Test Country";

            // Create AddressEntity object using constructor
            AddressEntity addressEntity = new AddressEntity(addressId, street, city, province, postalCode, country);

            // Assertions
            Assertions.assertNotNull(addressEntity);
            Assertions.assertEquals(addressId, addressEntity.getAddressId());
            Assertions.assertEquals(street, addressEntity.getStreet());
            Assertions.assertEquals(city, addressEntity.getCity());
            Assertions.assertEquals(province, addressEntity.getProvince());
            Assertions.assertEquals(postalCode, addressEntity.getPostalCode());
            Assertions.assertEquals(country, addressEntity.getCountry());
        }

        @Test
        @DisplayName("Test AddressEntity Setters and Getters")
        void testAddressEntitySettersAndGetters() {
            // Create AddressEntity object
            AddressEntity addressEntity = new AddressEntity();

            // Set values using setters
            addressEntity.setAddressId(1);
            addressEntity.setStreet("123 Main St");
            addressEntity.setCity("Test City");
            addressEntity.setProvince("Test Province");
            addressEntity.setPostalCode("12345");
            addressEntity.setCountry("Test Country");

            // Assertions using getters
            Assertions.assertEquals(1, addressEntity.getAddressId());
            Assertions.assertEquals("123 Main St", addressEntity.getStreet());
            Assertions.assertEquals("Test City", addressEntity.getCity());
            Assertions.assertEquals("Test Province", addressEntity.getProvince());
            Assertions.assertEquals("12345", addressEntity.getPostalCode());
            Assertions.assertEquals("Test Country", addressEntity.getCountry());
        }
    }

    
}
