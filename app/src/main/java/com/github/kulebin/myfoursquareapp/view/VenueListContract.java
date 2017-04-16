package com.github.kulebin.myfoursquareapp.view;

import com.github.kulebin.myfoursquareapp.adapter.VenueListAdapter;

public interface VenueListContract {

    interface View extends BaseContract.View {

    }

    interface Presentation {

        interface OnItemListener {

            void onClick(String venueId);
        }

        int getCount();

        void onBindView(VenueItemView holder, int position);

        void onViewCreated();

        void setOnItemListener(OnItemListener itemListener);

        VenueListAdapter getVenueListAdapter();

        void restoreData();

    }
}
