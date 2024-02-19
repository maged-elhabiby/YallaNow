package org.ucalgary.events_microservice.Service;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.Entity.AddressEntity;
import org.ucalgary.events_microservice.Repository.AddressRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository AddressRepository) {
        this.addressRepository = AddressRepository;
    }

    @Transactional
    public AddressEntity createAddress(EventDTO event){
        AddressEntity newAddress = new AddressEntity(event.getAddressID(),
                                                     event.getLocation().getStreet(),
                                                     event.getLocation().getCity(),
                                                     event.getLocation().getProvince(),
                                                     event.getLocation().getCountry());
        return addressRepository.save(newAddress);
    }

    @Transactional
    public AddressEntity updateAddress(EventDTO event){
        AddressEntity newAddress = new AddressEntity(event.getAddressID(),
                                                     event.getLocation().getStreet(),
                                                     event.getLocation().getCity(),
                                                     event.getLocation().getProvince(),
                                                     event.getLocation().getCountry());
        return addressRepository.save(newAddress);
    }

    @Transactional
    public void deleteAddress(int addressID){
        if (addressRepository.existsById(addressID)) {
            addressRepository.deleteById(addressID);
        } else {
            throw new RuntimeException("Address not found with id: " + addressID);
        }
    }
}
