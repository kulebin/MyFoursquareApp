package com.github.kulebin.myfoursquareapp;

import com.github.kulebin.myfoursquareapp.thread.OnResultCallback;

public interface EntityGateway {

    void fetchVenueList(OnResultCallback pOnResultCallback);

    final class Impl {

        public static EntityGateway newInstance() {
            return new VenueDummyDataSource();
        }
    }

}
