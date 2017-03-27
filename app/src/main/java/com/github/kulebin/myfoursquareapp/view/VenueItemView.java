package com.github.kulebin.myfoursquareapp.view;

import android.view.View;

public interface VenueItemView {

    void displayName(String name);

    void displayAddress(String address);

    void displayRating(String rating);

    void displayImage(String imageUrl);

    void setOnClickListener(View.OnClickListener listener);

}
