package com.github.kulebin.myfoursquareapp;

public class Venue {

    private String id;
    private String name;
    private String contact;
    private String location;
    private float rating;
    private String imageUrl;

    public Venue(final String pId, final String pName, final String pContact, final String pLocation, final float pRating, final String pImageUrl) {
        id = pId;
        name = pName;
        contact = pContact;
        location = pLocation;
        rating = pRating;
        imageUrl = pImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(final String pId) {
        id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String pName) {
        name = pName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String pContact) {
        contact = pContact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String pLocation) {
        location = pLocation;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(final float pRating) {
        rating = pRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String pImageUrl) {
        imageUrl = pImageUrl;
    }
}
