package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

public interface VenueDetailPresentation extends BasePresentation {

    void presentVenueToShowData(VenueDisplayData venueToShowData);

    // TODO: 3/1/2017 thing about logic without passing id to use case
    String getVenueId();

}
