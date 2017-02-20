package com.github.kulebin.myfoursquareapp;

import com.github.kulebin.myfoursquareapp.thread.OnResultCallback;

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

        mEntityGateway.fetchVenueList(new OnResultCallback<List<Venue>, Void>() {

            @Override
            public void onStart() {
                mPresenter.setProgress(true);
            }

            @Override
            public void onSuccess(final List<Venue> venueList) {
                mPresenter.setProgress(false);
                final List<VenueDisplayData> venueToShowList = new ArrayList<>(venueList.size());
                for (final Venue venue : venueList) {
                    venueToShowList.add(new VenueDisplayData(venue));
                }
                mPresenter.presentVenueToShowData(venueToShowList);
            }

            @Override
            public void onError(final Exception e) {
                mPresenter.setProgress(false);
                mPresenter.onError(e);
            }

            @Override
            public void onProgressChanged(final Void pVoid) {
                //ignore
            }
        });
    }
}
