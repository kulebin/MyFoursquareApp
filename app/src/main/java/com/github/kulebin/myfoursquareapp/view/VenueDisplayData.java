package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.model.Venue;

public class VenueDisplayData {

    private final String name;
    private final String address;
    private final float rating;
    private final String imageUrl;

    public VenueDisplayData(final Venue pVenue) {
        name = pVenue.getName();
        address = pVenue.getLocation().getFormattedAddress();
        rating = pVenue.getRating();
        imageUrl = pVenue.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }
}
