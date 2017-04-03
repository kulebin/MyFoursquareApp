package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.model.Venue;

public class CompleteVenueDisplayData extends VenueDisplayData {

    private final String contact;
    private final String description;

    public CompleteVenueDisplayData(final Venue pVenue) {
        super(pVenue);

        this.contact = pVenue.getContact();
        this.description = pVenue.getDescription();
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }
}
