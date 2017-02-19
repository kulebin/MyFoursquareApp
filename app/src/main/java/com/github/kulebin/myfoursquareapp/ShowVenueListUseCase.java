package com.github.kulebin.myfoursquareapp;

import java.util.ArrayList;
import java.util.List;

public class ShowVenueListUseCase {

    private final EntityGateway mEntityGateway;
    private final VenueListPresentation mPresenter;

    public ShowVenueListUseCase(final EntityGateway pEntityGateway, final VenueListPresentation pPresenter) {
        mEntityGateway = pEntityGateway;
        mPresenter = pPresenter;
    }

    public void showVenueList() {
        //TODO: should be run in worker thread
        final List<Venue> venueList = mEntityGateway.fetchVenueList();
        final List<VenueDisplayData> venueToShowList = new ArrayList<>(venueList.size());
        for (final Venue venue : venueList) {
            venueToShowList.add(new VenueDisplayData(venue));
        }
        mPresenter.presentVenueToShowData(venueToShowList);
    }
}
