package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.adapter.VenueListAdapter;

import java.util.List;

public interface VenueListContract {

    interface View extends BaseContract.View {

    }

    interface Presentation extends BaseContract.Presentation {

        interface OnItemListener {

            void onClick(String venueId);
        }

        void presentVenueToShowData(List<VenueDisplayData> venueToShowData);

        int getCount();

        void onBindView(VenueItemView holder, int position);

        void onViewCreated();

        void setOnItemListener(OnItemListener itemListener);

        VenueListAdapter getVenueListAdapter();

        void restoreData();

    }
}
