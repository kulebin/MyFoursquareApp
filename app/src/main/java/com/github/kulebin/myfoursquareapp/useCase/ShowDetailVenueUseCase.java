package com.github.kulebin.myfoursquareapp.useCase;

import android.util.Log;

import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.dataSource.IOnResultCallback;
import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.presenter.VenueDetailPresentation;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

public class ShowDetailVenueUseCase {

    private final IDataSource mDataSource;
    private final VenueDetailPresentation mPresenter;

    public ShowDetailVenueUseCase(final IDataSource pDataSource, final VenueDetailPresentation pPresenter) {
        mDataSource = pDataSource;
        mPresenter = pPresenter;
    }

    public void showDetailVenue() {

        mDataSource.fetchData(Api.getVenuesByIdUrl(mPresenter.getVenueId()), new IOnResultCallback<Venue>() {

            @Override
            public void onStart() {
                mPresenter.setProgress(true);
            }

            @Override
            public void onSuccess(final Venue venue) {
                mPresenter.setProgress(false);
                mPresenter.presentVenueToShowData(new VenueDisplayData(venue));
                //todo should be deleted
                Log.d("ShowDetailVenueUseCase", venue.getName());
            }

            @Override
            public void onError(final Exception e) {
                mPresenter.setProgress(false);
                mPresenter.onError(e);
            }
        });
    }

}
