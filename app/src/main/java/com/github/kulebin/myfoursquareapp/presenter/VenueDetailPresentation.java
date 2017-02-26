package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

public interface VenueDetailPresentation extends BasePresentation {

    void presentVenueToShowData(VenueDisplayData venueToShowData);

    String getVenueId();

}
