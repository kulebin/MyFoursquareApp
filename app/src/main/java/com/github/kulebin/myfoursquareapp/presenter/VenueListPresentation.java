package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;
import com.github.kulebin.myfoursquareapp.view.VenueItemView;

import java.util.List;

public interface VenueListPresentation {

    void presentVenueToShowData(List<VenueDisplayData> venueToShowData);

    int getCount();

    void onBindView(VenueItemView holder, int position);

    void onViewCreated();

    void onError(Exception e);

    void setProgress(boolean isVisible);

}
