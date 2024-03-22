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
    private Integer street;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "country")
    private String country;

    // Constructors
    public AddressEntity() {
    }

    public AddressEntity(Integer addressId, Integer street, String city, String province, String country) {
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    // Getters and setters
    public Integer getAddressId() {return addressId;}
    public Integer getStreet() {return street;}
    public String getCity() {return city;}
    public String getProvince() {return province;}
    public String getCountry() {return country;}

    public void setAddressId(Integer addressId) {this.addressId = addressId;}
    public void setStreet(Integer street) {this.street = street;}
    public void setCity(String city) {this.city = city;}
    public void setProvince(String province) {this.province = province;}
    public void setCountry(String country) {this.country = country;}
}
