package com.github.kulebin.myfoursquareapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kulebin.myfoursquareapp.R;
import com.github.kulebin.myfoursquareapp.app.ContextHolder;
import com.github.kulebin.myfoursquareapp.imageLoader.DisplayOptions;
import com.github.kulebin.myfoursquareapp.imageLoader.IImageLoader;
import com.github.kulebin.myfoursquareapp.view.VenueItemView;
import com.github.kulebin.myfoursquareapp.view.VenueListContract;
import com.github.kulebin.myfoursquareapp.view.VenueListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueItemViewHolder> {

    private static final int MAX_IMAGE_WIDTH = (int) ContextHolder.get().getResources().getDimension(R.dimen.venue_list_adapter_image_width);
    private static final int MAX_IMAGE_HEIGHT = (int) ContextHolder.get().getResources().getDimension(R.dimen.venue_list_adapter_image_height);

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

        String venueId;
        @BindView(R.id.text_venue_name)
        TextView nameView;
        @BindView(R.id.text_venue_location)
        TextView addressView;
        @BindView(R.id.text_venue_rating)
        TextView ratingView;
        @BindView(R.id.image_venue)
        ImageView imageView;
        VenueListContract.Presentation.OnItemListener itemListener;

        public VenueItemViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    if (itemListener != null) {
                        itemListener.onClick(venueId);
                    }
                }
            });
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
        public void setVenueId(final String id) {
            venueId = id;
        }

        @Override
        public void setOnItemListener(final VenueListContract.Presentation.OnItemListener listener) {
            itemListener = listener;
        }
    }
}
