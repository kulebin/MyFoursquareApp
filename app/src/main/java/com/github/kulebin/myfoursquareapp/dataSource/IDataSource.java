package com.github.kulebin.myfoursquareapp.dataSource;

import com.github.kulebin.myfoursquareapp.app.App;
import com.github.kulebin.myfoursquareapp.model.Venue;

import java.util.List;

public interface IDataSource {

    String APP_SERVICE_KEY = "dataSource";

    void fetchVenueList(IOnResultCallback<List<Venue>> pOnResultCallback);

    void fetchVenueById(String pVenueId, IOnResultCallback<Venue> pOnResultCallback);

    final class Impl {

        public static IDataSource newInstance() {
            return new FoursquareDataSource();
        }

        public static IDataSource get() {
            return App.get(APP_SERVICE_KEY);
        }
    }

}
