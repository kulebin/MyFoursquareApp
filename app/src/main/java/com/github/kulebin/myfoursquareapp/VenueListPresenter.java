package com.github.kulebin.myfoursquareapp;

import java.util.List;

public class VenueListPresenter implements VenueListPresentation {

    private List<VenueDisplayData> mVenueDisplayList;

    @Override
    public void presentVenueToShowData(final List<VenueDisplayData> venueToShowData) {
        this.mVenueDisplayList = venueToShowData;
    }

    @Override
    public int getCount() {
        return mVenueDisplayList == null ? 0 : mVenueDisplayList.size();
    }

    @Override
    public void onBindView(final VenueItemView view, final int position) {
        final VenueDisplayData venueDisplayData = mVenueDisplayList.get(position);
        view.displayName(venueDisplayData.getName());
        view.displayLocation(venueDisplayData.getLocation());
        view.displayRating(String.valueOf(venueDisplayData.getRating()));
    }

    @Override
    public void onViewCreated() {
        final EntityGateway entityGateway = EntityGateway.Impl.newInstance();
        new ShowVenueListUseCase(entityGateway, this).showVenueList();
    }
}
