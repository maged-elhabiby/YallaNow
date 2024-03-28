package com.yallanow.analyticsservice.models;

/**
 * Represents a location with street, city, province, and country information.
 */
public class Location {

    private String street;
    private String city;
    private String province;
    private String country;

    /**
     * Constructs a new Location object with the specified street, city, province, and country.
     *
     * @param street   the street name
     * @param city     the city name
     * @param province the province name
     * @param country  the country name
     */
    public Location(String street, String city, String province, String country) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    /**
     * Returns the street name of the location.
     *
     * @return the street name
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street name of the location.
     *
     * @param street the street name to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns the city name of the location.
     *
     * @return the city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city name of the location.
     *
     * @param city the city name to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the province name of the location.
     *
     * @return the province name
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the province name of the location.
     *
     * @param province the province name to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Returns the country name of the location.
     *
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country name of the location.
     *
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
