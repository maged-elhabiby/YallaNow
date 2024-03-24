package org.ucalgary.events_microservice.DTO;

public class AddressDTO {

    // Attributes
    private int addressID;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;

    // Constructors
    public AddressDTO() {}

    public AddressDTO(String street, String city, String province, String postalCode ,String country) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    public AddressDTO(int addressID, String street, String city, String province, String postalCode, String country) {
        this.addressID = addressID;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and setters
    public final int getAddressID() {return addressID;}
    public final String getStreet() {return street;}
    public final String getCity() {return city;}
    public final String getProvince() {return province;}
    public final String getPostalCode() {return postalCode;}
    public final String getCountry() {return country;}

    public void setStreet(final String street) {this.street = street;}
    public void setCity(final String city) {this.city = city;}
    public void setProvince(final String province) {this.province = province;}
    public void setPostalCode(final String postalCode) {this.postalCode = postalCode;}
    public void setCountry(final String country) {this.country = country;}

    // Override Methods
    @Override
    public String toString() {
        return String.format("%d %s, %s, %s", street, city, province, country);
    }
}
