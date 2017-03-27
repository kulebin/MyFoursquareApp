package com.github.kulebin.myfoursquareapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kulebin.myfoursquareapp.R;
import com.github.kulebin.myfoursquareapp.imageLoader.DisplayOptions;
import com.github.kulebin.myfoursquareapp.imageLoader.IImageLoader;
import com.github.kulebin.myfoursquareapp.presenter.VenueListPresenter;
import com.github.kulebin.myfoursquareapp.view.VenueItemView;

public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueItemViewHolder> {

    private final VenueListPresenter mPresenter;

    private static final int MAX_IMAGE_WIDTH = 300;
    private static final int MAX_IMAGE_HEIGHT = 300;

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
        TextView addressView;
        TextView ratingView;
        ImageView imageView;

        public VenueItemViewHolder(final View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById(R.id.venueNameTextView);
            this.addressView = (TextView) itemView.findViewById(R.id.venueLocationTextView);
            this.ratingView = (TextView) itemView.findViewById(R.id.venueRatingTextViewView);
            this.imageView = (ImageView) itemView.findViewById(R.id.venueImageView);
        }

        @Override
        public void displayName(final String name) {
            nameView.setText(name);
        }

        @Override
        public void displayAddress(final String address) {
            addressView.setText(address);
        }

        @Override
        public void displayRating(final String rating) {
            ratingView.setText(rating);
        }

        @Override
        public void displayImage(final String imageUrl) {
            final DisplayOptions options = new DisplayOptions(MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT, R.drawable.placeholder_foursquare);
            IImageLoader.Impl.get().draw(imageUrl, imageView, options);
        }

        @Override
        public void setOnClickListener(final View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }
}
