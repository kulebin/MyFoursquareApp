package com.github.kulebin.myfoursquareapp;

import java.util.List;

public interface VenueListPresentation {

    void presentVenueToShowData(List<VenueDisplayData> venueToShowData);

    int getCount();

    void onBindView(VenueItemView holder, int position);

    void onViewCreated();

    void onError(Exception e);

    void setProgress(boolean isVisible);

}
