package com.ioglyph.modulo.core.user;

public class UserLocation {
    private final String country;
    private final String region;
    private final String city;
    private final float latitude;
    private final float longitude;

    public UserLocation(String country, String region, String city, float latitude, float longitude){
        this.country = country;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String country(){
        return this.country;
    }

    public String region(){
        return this.region;
    }

    public String city(){
        return this.city;
    }

    public float latitude(){
        return latitude;
    }

    public float longitude(){
        return this.longitude;
    }
}
