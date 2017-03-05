package com.github.kulebin.myfoursquareapp.model;

public class Venue {

    private final String id;
    private final String name;
    private final String contact;
    private final Location location;
    private final float rating;
    private final String imageUrl;
    private final String description;

    public Venue(final String pId, final String pName, final String pContact, final Location pLocation, final float pRating, final String pImageUrl, final String pDescription) {
        id = pId;
        name = pName;
        contact = pContact;
        location = pLocation;
        rating = pRating;
        imageUrl = pImageUrl;
        description = pDescription;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public Location getLocation() {
        return location;
    }

    public float getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedAddress() {
        if (location.getAddress() != null) {
            String displayAddress = location.getAddress();

            if (location.getCity() != null) {
                displayAddress += ", " + location.getCity();
            }

            return displayAddress;
        } else {
            return null;
        }
    }
}
