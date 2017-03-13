package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.adapter.VenueListAdapter;
import com.github.kulebin.myfoursquareapp.useCase.ShowVenueListUseCase;
import com.github.kulebin.myfoursquareapp.view.IViewCallback;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;
import com.github.kulebin.myfoursquareapp.view.VenueItemView;

import java.util.List;

public class VenueListPresenter implements VenueListPresentation {

    private List<VenueDisplayData> mVenueDisplayList;
    private IViewCallback mView;
    private final VenueListAdapter mVenueListAdapter = new VenueListAdapter(this);

    public VenueListPresenter(final IViewCallback pIViewCallback) {
        this.mView = pIViewCallback;
    }

    @Override
    public void presentVenueToShowData(final List<VenueDisplayData> venueToShowData) {
        this.mVenueDisplayList = venueToShowData;
        mVenueListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mVenueDisplayList == null ? 0 : mVenueDisplayList.size();
    }

    @Override
    public void onBindView(final VenueItemView view, final int position) {
        final VenueDisplayData venueDisplayData = mVenueDisplayList.get(position);
        view.displayName(venueDisplayData.getName());
        view.displayAddress(venueDisplayData.getAddress());
        view.displayRating(String.valueOf(venueDisplayData.getRating()));
        view.displayImage(venueDisplayData.getImageUrl());
    }

    @Override
    public void onViewCreated() {
        new ShowVenueListUseCase(this).showVenueList();
    }

    @Override
    public void onError(final Exception e) {
        mView.showError(e.getMessage());
    }

    @Override
    public void setProgress(final boolean isVisible) {
        mView.showProgress(isVisible);
    }

    public VenueListAdapter getVenueListAdapter() {
        return mVenueListAdapter;
    }
}
