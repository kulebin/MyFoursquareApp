package com.github.kulebin.myfoursquareapp.useCase;

import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.dataSource.IOnResultCallback;
import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.view.VenueDisplayData;

import java.util.ArrayList;
import java.util.List;

public class ShowVenueListUseCase {

    public interface IRecipient extends OnInteractionCallback {

        void presentVenueToShowData(List<VenueDisplayData> venueToShowData);
    }

    private final IRecipient mRecipient;

    public ShowVenueListUseCase(final IRecipient pRecipient) {
        mRecipient = pRecipient;
    }

    public void showVenueList() {

        IDataSource.Impl.get().fetchVenueList(new IOnResultCallback<List<Venue>>() {

            @Override
            public void onStart() {
                mRecipient.onStart();
            }

            @Override
            public void onSuccess(final List<Venue> pVenueList) {
                final List<VenueDisplayData> venueToShowList = new ArrayList<>(pVenueList.size());

                for (final Venue venue : pVenueList) {
                    venueToShowList.add(new VenueDisplayData(venue));
                }

                mRecipient.presentVenueToShowData(venueToShowList);
            }

            @Override
            public void onError(final Exception e) {
                mRecipient.onError(e);
            }
        });
    }
}
