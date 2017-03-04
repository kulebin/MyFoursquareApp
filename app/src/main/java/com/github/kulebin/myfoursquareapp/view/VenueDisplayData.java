package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.model.Location;
import com.github.kulebin.myfoursquareapp.model.Venue;

public class VenueDisplayData {

    private final String name;
    private final String address;
    private final float rating;

    public VenueDisplayData(final Venue pVenue) {
        name = pVenue.getName();
        address = getFormattedAddress(pVenue.getLocation());
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

    private String getFormattedAddress(final Location pLocation) {
        if (pLocation.getAddress() != null) {
            String displayAddress = pLocation.getAddress();
            if (pLocation.getCity() != null) {
                displayAddress += ", " + pLocation.getCity();
            }

            return displayAddress;
        } else {
            return null;
        }
    }
}
