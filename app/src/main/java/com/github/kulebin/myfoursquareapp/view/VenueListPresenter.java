package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.adapter.VenueListAdapter;
import com.github.kulebin.myfoursquareapp.useCase.ShowVenueListUseCase;

import java.util.List;

public class VenueListPresenter implements VenueListContract.Presentation, ShowVenueListUseCase.IRecipient {

    private List<VenueDisplayData> mVenueDisplayList;
    private final VenueListContract.View mView;
    private final VenueListAdapter mVenueListAdapter = new VenueListAdapter(this);
    private OnItemListener mOnItemListener;

    public VenueListPresenter(final VenueListContract.View pView) {
        this.mView = pView;
    }

    @Override
    public void presentVenueToShowData(final List<VenueDisplayData> venueToShowData) {
        this.mVenueDisplayList = venueToShowData;
        mView.showProgress(false);
        mVenueListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mVenueDisplayList == null ? 0 : mVenueDisplayList.size();
    }

    @Override
    public void onBindView(final VenueItemView view, final int position) {
        final VenueDisplayData venueDisplayData = mVenueDisplayList.get(position);
        view.setVenueId(venueDisplayData.getId());
        view.displayName(venueDisplayData.getName());
        view.displayAddress(venueDisplayData.getAddress());
        view.displayRating(String.valueOf(venueDisplayData.getRating()));
        view.displayImage(venueDisplayData.getImageUrl());
        view.setOnItemListener(mOnItemListener);
    }

    @Override
    public void onViewCreated() {
        new ShowVenueListUseCase(this).showVenueList();
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
    public VenueListAdapter getVenueListAdapter() {
        return mVenueListAdapter;
    }

    @Override
    public void restoreData() {
        mVenueListAdapter.notifyDataSetChanged();
    }

    public void setOnItemListener(final OnItemListener pOnItemListener) {
        mOnItemListener = pOnItemListener;
    }
}