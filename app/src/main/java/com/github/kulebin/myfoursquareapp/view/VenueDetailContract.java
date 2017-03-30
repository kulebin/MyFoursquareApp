package com.github.kulebin.myfoursquareapp.view;

public interface VenueDetailContract {

    interface View extends BaseContract.View{

        void displayData(CompleteVenueDisplayData venueToShowData);
    }

    interface Presentation extends BaseContract.Presentation {

        void presentVenueToShowData(CompleteVenueDisplayData venueToShowData);

        void onViewCreated(String venueId);

    }

}
