package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.useCase.ShowDetailVenueUseCase;

public class VenueDetailPresenter implements VenueDetailContract.Presentation, ShowDetailVenueUseCase.IRecipient {

    private final VenueDetailContract.View mView;

    public VenueDetailPresenter(final VenueDetailContract.View pView) {
        this.mView = pView;
    }

    @Override
    public void presentVenueToShowData(final CompleteVenueDisplayData pVenueToShowData) {
        mView.showProgress(false);
        mView.displayData(pVenueToShowData);
    }

    @Override
    public void onStart() {
        mView.showProgress(true);
    }

    @Override
    public void onError(final Exception e) {
        mView.showProgress(false);
        mView.showError(e.getMessage());
    }

    @Override
    public void onViewCreated(final String pVenueId) {
        new ShowDetailVenueUseCase(this).showDetailVenue(pVenueId);
    }
}
