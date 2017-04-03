package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.model.Venue;

public class VenueDisplayData {

    private final String id;
    private final String name;
    private final String address;
    private final float rating;
    private final String imageUrl;

    public VenueDisplayData(final Venue pVenue) {
        id = pVenue.getId();
        name = pVenue.getName();
        address = pVenue.getLocation().getFormattedAddress();
        rating = pVenue.getRating();
        imageUrl = pVenue.getImageUrl();
    }

    public String getId() {
        return id;
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
