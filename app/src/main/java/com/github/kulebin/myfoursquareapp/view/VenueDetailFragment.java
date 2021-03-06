package com.github.kulebin.myfoursquareapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kulebin.myfoursquareapp.AbstractBaseFragment;
import com.github.kulebin.myfoursquareapp.R;
import com.github.kulebin.myfoursquareapp.imageLoader.IImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenueDetailFragment extends AbstractBaseFragment implements VenueDetailContract.View {

    public static final String TAG = VenueDetailFragment.class.getSimpleName();
    private static final String VENUE_ID = "venueId";

    private final VenueDetailContract.Presentation mPresenter = new VenueDetailPresenter(this);
    @BindView(R.id.image_venue)
    ImageView mVenueImage;
    @BindView(R.id.text_venue_name)
    TextView mVenueNameText;
    @BindView(R.id.text_venue_address)
    TextView mVenueAddressText;
    @BindView(R.id.text_venue_contact)
    TextView mVenueContactText;
    @BindView(R.id.text_venue_description)
    TextView mVenueDescriptionText;

    public static VenueDetailFragment newInstance(final String pVenueId) {
        final VenueDetailFragment fragment = new VenueDetailFragment();
        final Bundle args = new Bundle();
        args.putString(VENUE_ID, pVenueId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mPresenter.onViewCreated(getArguments().getString(VENUE_ID));
        } else {
            Toast.makeText(getContext(), R.string.ERROR_TEXT_NO_VENUE_ID, Toast.LENGTH_LONG).show();
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_venue_detail;
    }

    @Override
    public void displayData(final CompleteVenueDisplayData venueToShowData) {
        IImageLoader.Impl.get().draw(venueToShowData.getImageUrl(), mVenueImage);
        mVenueNameText.setText(venueToShowData.getName());
        mVenueAddressText.setText(venueToShowData.getAddress());
        mVenueContactText.setText(venueToShowData.getContact());
        mVenueDescriptionText.setText(venueToShowData.getDescription());
    }
}
