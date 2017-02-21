package com.github.kulebin.myfoursquareapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.kulebin.myfoursquareapp.R;
import com.github.kulebin.myfoursquareapp.view.VenueItemView;
import com.github.kulebin.myfoursquareapp.presenter.VenueListPresenter;

public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueItemViewHolder> {

    private final VenueListPresenter mPresenter;

    public VenueListAdapter(final VenueListPresenter pVenueListPresenter) {
        this.mPresenter = pVenueListPresenter;
    }

    @Override
    public VenueItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_venue_list_item, parent, false);
        return new VenueItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VenueItemViewHolder holder, final int position) {
        mPresenter.onBindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getCount();
    }

    public static class VenueItemViewHolder extends RecyclerView.ViewHolder implements VenueItemView {

        TextView nameView;
        TextView locationView;
        TextView ratingView;

        public VenueItemViewHolder(final View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById(R.id.venueNameTextView);
            this.locationView = (TextView) itemView.findViewById(R.id.venueLocationTextView);
            this.ratingView = (TextView) itemView.findViewById(R.id.venueRatingTextViewView);
        }

        @Override
        public void displayName(final String name) {
            nameView.setText(name);
        }

        @Override
        public void displayLocation(final String location) {
            locationView.setText(location);
        }

        @Override
        public void displayRating(final String rating) {
            ratingView.setText(rating);
        }

        @Override
        public void displayImage(final String imageUrl) {
            //todo load and show image
        }
    }

}
