package com.github.kulebin.myfoursquareapp.model;

public class Location {

    private final double latitude;
    private final double longitude;
    private final String address;
    private final int distance;
    private final String city;
    private final String country;

    public Location(final double pLatitude, final double pLongitude, final String pAddress, final int pDistance, final String pCity, final String pCountry) {
        latitude = pLatitude;
        longitude = pLongitude;
        address = pAddress;
        distance = pDistance;
        city = pCity;
        country = pCountry;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public int getDistance() {
        return distance;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getFormattedAddress() {
        if (address != null) {
            String displayAddress = address;

            if (city != null) {
                displayAddress += ", " + city;
            }

            return displayAddress;
        } else {
            return null;
        }
    }
}
