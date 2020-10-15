package com.ioglyph.modulo.rest.data;

import com.ioglyph.modulo.core.user.UserLocation;

/**
 * Data class for JSON serialization.
 */
public class LocationData {

    public String country;
    public String region;
    public String city;
    public float latitude;
    public float longitude;

    public LocationData(UserLocation location){
        this.country = location.country();
        this.region = location.region();
        this.city = location.city();
        this.latitude = location.latitude();
        this.longitude = location.longitude();
    }
}
