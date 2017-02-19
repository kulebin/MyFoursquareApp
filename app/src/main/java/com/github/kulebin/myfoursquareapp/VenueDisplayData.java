package com.github.kulebin.myfoursquareapp;

public class VenueDisplayData {

    private final String name;
    private final String location;
    private final float rating;

    public VenueDisplayData(final Venue pVenue) {
        name = pVenue.getName();
        location = pVenue.getLocation();
        rating = pVenue.getRating();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public float getRating() {
        return rating;
    }
}
