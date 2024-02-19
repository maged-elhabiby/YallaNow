package org.ucalgary.events_microservice.DTO;

public class AddressDTO {
    private int addressID;
    private int street;
    private String city;
    private String province;
    private String country;

    public AddressDTO() {}

    public AddressDTO(int street, String city, String province, String country) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }
    public int getAddressID() {return addressID;}
    public int getStreet() {return street;}
    public String getCity() {return city;}
    public String getProvince() {return province;}
    public String getCountry() {return country;}

    public void setStreet(int street) {this.street = street;}
    public void setCity(String city) {this.city = city;}
    public void setProvince(String province) {this.province = province;}
    public void setCountry(String country) {this.country = country;}


    @Override
    public String toString() {
        return String.format("%d %s, %s, %s", street, city, province, country);
    }
}
