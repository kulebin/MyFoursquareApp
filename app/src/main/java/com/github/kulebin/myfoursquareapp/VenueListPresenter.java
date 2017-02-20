package com.github.kulebin.myfoursquareapp;

import java.util.List;

public class VenueListPresenter implements VenueListPresentation {

    private List<VenueDisplayData> mVenueDisplayList;
    private IView mView;
    private final VenueListAdapter mVenueListAdapter = new VenueListAdapter(this);

    public VenueListPresenter(final IView pIView) {
        this.mView = pIView;
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
        view.displayLocation(venueDisplayData.getLocation());
        view.displayRating(String.valueOf(venueDisplayData.getRating()));
    }

    @Override
    public void onViewCreated() {
        final EntityGateway entityGateway = EntityGateway.Impl.newInstance();
        new ShowVenueListUseCase(entityGateway, this).showVenueList();
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
