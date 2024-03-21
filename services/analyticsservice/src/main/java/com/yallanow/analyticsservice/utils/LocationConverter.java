package com.yallanow.analyticsservice.utils;

import com.yallanow.analyticsservice.models.Location;

import java.io.IOException;
import java.util.Map;

public class LocationConverter {

    public static Location fromMap(Map<String, Object> map) {
        String addressId = (String) map.get("addressId");
        String street = (String) map.get("street");
        String city = (String) map.get("city");
        String province = (String) map.get("province");
        String county = (String) map.get("country");

        return new Location(addressId, street, city, province, county);
    }

}
