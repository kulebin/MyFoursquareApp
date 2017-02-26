package com.github.kulebin.myfoursquareapp.useCase;

import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.dataSource.IOnResultCallback;
import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.presenter.VenueListPresentation;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

import java.util.ArrayList;
import java.util.List;

public class ShowVenueListUseCase {

    private final IDataSource mDataSource;
    private final VenueListPresentation mPresenter;

    public ShowVenueListUseCase(final IDataSource pDataSource, final VenueListPresentation pPresenter) {
        mDataSource = pDataSource;
        mPresenter = pPresenter;
    }

    public void showVenueList() {

        mDataSource.fetchData(Api.getVenuesTrendingUrl(), new IOnResultCallback<List<Venue>>() {

            @Override
            public void onStart() {
                mPresenter.setProgress(true);
            }

            @Override
            public void onSuccess(final List<Venue> pVenueList) {
                mPresenter.setProgress(false);
                final List<VenueDisplayData> venueToShowList = new ArrayList<>(pVenueList.size());

                for (final Venue venue : pVenueList) {
                    venueToShowList.add(new VenueDisplayData(venue));
                }

                mPresenter.setProgress(false);
                mPresenter.presentVenueToShowData(venueToShowList);
            }

            @Override
            public void onError(final Exception e) {
                mPresenter.setProgress(false);
                mPresenter.onError(e);
            }
        });
    }
}
