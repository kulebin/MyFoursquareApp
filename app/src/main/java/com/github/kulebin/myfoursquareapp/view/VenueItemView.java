package com.github.kulebin.myfoursquareapp.view;

public interface VenueItemView {

    void displayName(String name);

    void displayAddress(String address);

    void displayRating(String rating);

    void displayImage(String imageUrl);

    void setVenueId(String venueId);

    void setOnItemListener(VenueListContract.Presentation.OnItemListener listener);

}
