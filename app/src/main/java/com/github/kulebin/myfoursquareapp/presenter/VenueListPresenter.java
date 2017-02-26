package com.github.kulebin.myfoursquareapp.presenter;

import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.useCase.ShowDetailVenueUseCase;
import com.github.kulebin.myfoursquareapp.view.IViewCallback;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;
import com.github.kulebin.myfoursquareapp.view.VenueItemView;
import com.github.kulebin.myfoursquareapp.adapter.VenueListAdapter;
import com.github.kulebin.myfoursquareapp.useCase.ShowVenueListUseCase;

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
        view.displayLocation(venueDisplayData.getLocation());
        view.displayRating(String.valueOf(venueDisplayData.getRating()));
    }

    @Override
    public void onViewCreated() {
        final IDataSource dataSource = IDataSource.Impl.newInstance();
        new ShowVenueListUseCase(dataSource, this).showVenueList();

        //todo just to check, should be deleted in the future
        new ShowDetailVenueUseCase(dataSource, new VenueDetailPresentation() {

            @Override
            public void presentVenueToShowData(VenueDisplayData venueToShowData) {

            }

            @Override
            public String getVenueId() {
                return "4fe17dfbe4b0bd44616280d7";
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void setProgress(boolean isVisible) {

            }
        }).showDetailVenue();
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
