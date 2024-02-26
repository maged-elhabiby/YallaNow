package org.ucalgary.events_microservice.DTO;

public class AddressDTO {

    // Attributes
    private int addressID;
    private int street;
    private String city;
    private String province;
    private String country;

    // Constructors
    public AddressDTO() {}

    public AddressDTO(int street, String city, String province, String country) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public AddressDTO(int addressID, int street, String city, String province, String country) {
        this.addressID = addressID;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    // Getters and setters
    public final int getAddressID() {return addressID;}
    public final int getStreet() {return street;}
    public final String getCity() {return city;}
    public final String getProvince() {return province;}
    public final String getCountry() {return country;}

    public void setStreet(final int street) {this.street = street;}
    public void setCity(final String city) {this.city = city;}
    public void setProvince(final String province) {this.province = province;}
    public void setCountry(final String country) {this.country = country;}

    // Override Methods
    @Override
    public String toString() {
        return String.format("%d %s, %s, %s", street, city, province, country);
    }
}
