package org.ucalgary.events_service.DTO;

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

    public AddressDTO(String street, String city, String province, String postalCode ,String country)throws IllegalArgumentException {
        validateInput(street, city, province, postalCode, country);
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    public AddressDTO(int addressID, String street, String city, String province, 
                    String postalCode, String country) throws IllegalArgumentException {
        validateInput(street, city, province, postalCode, country);
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

    public void setAddressID(final int addressID) {this.addressID = addressID;}
    public void setStreet(final String street) {this.street = street;}
    public void setCity(final String city) {this.city = city;}
    public void setProvince(final String province) {this.province = province;}
    public void setPostalCode(final String postalCode) {this.postalCode = postalCode;}
    public void setCountry(final String country) {this.country = country;}

    // Override Methods
    @Override
    public String toString() {
        return String.format("%s %s, %s, %s", street, city, province, country);
    }

    /**
     * Validates the input for the address.
     * @param street
     * @param city
     * @param province
     * @param postalCode
     * @param country
     */
    private void validateInput(String street, String city, String province, String postalCode, String country) throws IllegalArgumentException{
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (province == null || province.isEmpty()) {
            throw new IllegalArgumentException("Province cannot be null or empty");
        }
        if (postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }
}
