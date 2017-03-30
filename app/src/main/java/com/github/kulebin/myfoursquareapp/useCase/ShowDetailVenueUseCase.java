package com.github.kulebin.myfoursquareapp.useCase;

import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.dataSource.IOnResultCallback;
import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.view.CompleteVenueDisplayData;
import com.github.kulebin.myfoursquareapp.view.VenueDetailContract;

public class ShowDetailVenueUseCase {

    private final VenueDetailContract.Presentation mPresenter;

    public ShowDetailVenueUseCase(final VenueDetailContract.Presentation pPresenter) {
        mPresenter = pPresenter;
    }

    public void showDetailVenue(final String pVenueId) {

        IDataSource.Impl.get().fetchVenueById(pVenueId, new IOnResultCallback<Venue>() {

            @Override
            public void onStart() {
                mPresenter.setProgress(true);
            }

            @Override
            public void onSuccess(final Venue venue) {
                mPresenter.setProgress(false);
                if (venue != null) {
                    mPresenter.presentVenueToShowData(new CompleteVenueDisplayData(venue));
                }
            }

            @Override
            public void onError(final Exception e) {
                mPresenter.setProgress(false);
                mPresenter.onError(e);
            }
        });
    }
}
