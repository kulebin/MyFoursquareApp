package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.useCase.ShowDetailVenueUseCase;
import com.github.kulebin.myfoursquareapp.view.CompleteVenueDisplayData;
import com.github.kulebin.myfoursquareapp.view.VenueDetailContract;

public class VenueDetailPresenter implements VenueDetailContract.Presentation {

    private final VenueDetailContract.View mView;

    public VenueDetailPresenter(final VenueDetailContract.View pView) {
        this.mView = pView;
    }

    @Override
    public void presentVenueToShowData(final CompleteVenueDisplayData pVenueToShowData) {
        mView.displayData(pVenueToShowData);
    }

    @Override
    public void onError(final Exception e) {
        mView.showError(e.getMessage());
    }

    @Override
    public void setProgress(final boolean isVisible) {
        mView.showProgress(isVisible);
    }

    @Override
    public void onViewCreated(final String pVenueId) {
        new ShowDetailVenueUseCase(this).showDetailVenue(pVenueId);
    }
}
