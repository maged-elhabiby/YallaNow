package org.ucalgary.events_microservice.Service;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Repository.AddressRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for managing AddressEntity objects.
 * This class provides methods to create, update, and delete address entities.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Creates a new address entity based on the provided event DTO.
     * @param event The event DTO containing address information.
     * @return The newly created address entity.
     */
    @Transactional
    public AddressEntity createAddress(EventDTO event){
        AddressEntity newAddress = new AddressEntity(event.getAddressID(),
                                                     event.getLocation().getStreet(),
                                                     event.getLocation().getCity(),
                                                     event.getLocation().getProvince(),
                                                     event.getLocation().getCountry());
        return addressRepository.save(newAddress);
    }

    /**
     * Updates an existing address entity based on the provided event DTO.
     * @param event The event DTO containing updated address information.
     * @return The updated address entity.
     */
    @Transactional
    public AddressEntity updateAddress(EventDTO event){
        AddressEntity updatedAddress = new AddressEntity(event.getAddressID(),
                                                          event.getLocation().getStreet(),
                                                          event.getLocation().getCity(),
                                                          event.getLocation().getProvince(),
                                                          event.getLocation().getCountry());
        return addressRepository.save(updatedAddress);
    }

    /**
     * Deletes an address entity with the specified address ID.
     * @param addressID The ID of the address entity to delete.
     * @throws RuntimeException if the address entity with the given ID is not found.
     */
    @Transactional
    public void deleteAddress(int addressID){
        if (addressRepository.existsById(addressID)) {
            addressRepository.deleteById(addressID);
        } else {
            throw new RuntimeException("Address not found with id: " + addressID);
        }
    }
}
