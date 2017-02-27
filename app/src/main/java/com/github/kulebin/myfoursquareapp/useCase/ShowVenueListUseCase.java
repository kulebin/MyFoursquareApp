package com.github.kulebin.myfoursquareapp.useCase;

import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.dataSource.IOnResultCallback;
import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.presenter.VenueListPresentation;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

import java.util.ArrayList;
import java.util.List;

public class ShowVenueListUseCase {

    private final VenueListPresentation mPresenter;

    public ShowVenueListUseCase(final VenueListPresentation pPresenter) {
        mPresenter = pPresenter;
    }

    public void showVenueList() {

        IDataSource.Impl.get().fetchVenueList(new IOnResultCallback<List<Venue>>() {

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
