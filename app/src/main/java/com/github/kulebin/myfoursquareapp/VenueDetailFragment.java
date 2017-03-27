package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class VenueDetailFragment extends AbstractBaseFragment {

    public static final String TAG = VenueDetailFragment.class.getSimpleName();
    private static final String VENUE_ID = "venueId";

    private String mVenueId;

    public static VenueDetailFragment newInstance(final String pVenueId) {
        final VenueDetailFragment fragment = new VenueDetailFragment();
        final Bundle args = new Bundle();
        args.putString(VENUE_ID, pVenueId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVenueId = getArguments().getString(VENUE_ID);
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.text_venue_id)).setText(mVenueId);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_venue_detail;
    }
}
