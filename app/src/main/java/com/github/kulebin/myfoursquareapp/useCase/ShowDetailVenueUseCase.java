package com.github.kulebin.myfoursquareapp.useCase;

import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.dataSource.IOnResultCallback;
import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.view.CompleteVenueDisplayData;

public class ShowDetailVenueUseCase {

    public interface IRecipient extends OnInteractionCallback {

        void presentVenueToShowData(CompleteVenueDisplayData venueToShowData);
    }

    private final IRecipient mRecipient;

    public ShowDetailVenueUseCase(final IRecipient pRecipient) {
        mRecipient = pRecipient;
    }

    public void showDetailVenue(final String pVenueId) {

        IDataSource.Impl.get().fetchVenueById(pVenueId, new IOnResultCallback<Venue>() {

            @Override
            public void onStart() {
                mRecipient.onStart();
            }

            @Override
            public void onSuccess(final Venue venue) {
                mRecipient.presentVenueToShowData(new CompleteVenueDisplayData(venue));
            }

            @Override
            public void onError(final Exception e) {
                mRecipient.onError(e);
            }
        });
    }
}
