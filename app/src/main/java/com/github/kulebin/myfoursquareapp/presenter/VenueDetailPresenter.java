package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.view.IViewCallback;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

public class VenueDetailPresenter implements VenueDetailPresentation {

    private final IViewCallback mView;
    private VenueDisplayData mDisplayData;

    public VenueDetailPresenter(final IViewCallback pIViewCallback) {
        this.mView = pIViewCallback;
    }

    @Override
    public void presentVenueToShowData(final VenueDisplayData pVenueToShowData) {
        mDisplayData = pVenueToShowData;
    }

    @Override
    public String getVenueId() {
        //todo: some logic to get selected Venue ID should be implemented
        return "4fe17dfbe4b0bd44616280d7";
    }

    @Override
    public void onError(final Exception e) {
        mView.showError(e.getMessage());
    }

    @Override
    public void setProgress(final boolean isVisible) {
        mView.showProgress(isVisible);
    }
}
