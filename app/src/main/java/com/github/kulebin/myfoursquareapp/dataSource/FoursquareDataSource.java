package com.github.kulebin.myfoursquareapp.dataSource;

import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.model.Venue;

import java.util.List;

class FoursquareDataSource implements IDataSource {

    private final IDataLoader mIDataLoader = IDataLoader.Impl.newInstance();

    @Override
    public void fetchVenueList(final IOnResultCallback<List<Venue>> pOnResultCallback) {
        mIDataLoader.loadData(Api.getVenuesTrendingUrl(), pOnResultCallback);
    }

    @Override
    public void fetchVenueById(final String pVenueId, final IOnResultCallback<Venue> pOnResultCallback) {
        mIDataLoader.loadData(Api.getVenuesByIdUrl(pVenueId), pOnResultCallback);
    }
}
