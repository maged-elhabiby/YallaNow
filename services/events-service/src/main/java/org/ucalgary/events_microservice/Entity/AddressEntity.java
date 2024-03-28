package org.ucalgary.events_microservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * AddressEntity, Used to Create and Store AddressEntity Objects in MySQL Database
 */
@Entity
@Table(name = "address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressEntity {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "country")
    private String country;

    // Constructors
    public AddressEntity() {
    }

    public AddressEntity(Integer addressId, String street, String city, String province, String postalCode, String country) {
        validateAddress(street, city, province, postalCode, country);
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and setters
    public Integer getAddressId() {return addressId;}
    public String getStreet() {return street;}
    public String getCity() {return city;}
    public String getProvince() {return province;}
    public String getPostalCode() {return postalCode;}
    public String getCountry() {return country;}

    public void setAddressId(Integer addressId) {this.addressId = addressId;}
    public void setStreet(String street) {this.street = street;}
    public void setCity(String city) {this.city = city;}
    public void setProvince(String province) {this.province = province;}
    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}
    public void setCountry(String country) {this.country = country;}

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", street, city, province, postalCode, country);
    }

    /**
     * Validates the address fields.
     * @param street
     * @param city
     * @param province
     * @param postalCode
     * @param country
     */
    private void validateAddress(String street, String city, String province, String postalCode, String country) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be empty");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        if (province == null || province.isEmpty()) {
            throw new IllegalArgumentException("Province cannot be empty");
        }
        if (postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be empty");
        }
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty");
        }
    }
}
