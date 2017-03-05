package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.model.Venue;

public class VenueDisplayData {

    private final String name;
    private final String address;
    private final float rating;

    public VenueDisplayData(final Venue pVenue) {
        name = pVenue.getName();
        address = pVenue.getLocation().getFormattedAddress();
        rating = pVenue.getRating();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public float getRating() {
        return rating;
    }
}
