package com.yallanow.analyticsservice.models;

public class Location {
    private String id;
    private String street;
    private String city;
    private String province;
    private String country;

    public Location(String id, String street, String city, String province, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
