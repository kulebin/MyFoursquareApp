package com.github.kulebin.myfoursquareapp;

import java.util.List;

public interface EntityGateway {

    List<Venue> fetchVenueList();

    final class Impl {

        public static EntityGateway newInstance() {
            return new VenueDummyDataSource();
        }
    }

}
