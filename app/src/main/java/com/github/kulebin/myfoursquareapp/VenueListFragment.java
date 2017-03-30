package com.github.kulebin.myfoursquareapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.kulebin.myfoursquareapp.presenter.VenueListPresenter;
import com.github.kulebin.myfoursquareapp.view.VenueListContract;

public class VenueListFragment extends AbstractBaseFragment implements VenueListContract.View {

    public interface Callback {

        void onItemSelected(String venueId);
    }

    public static final String TAG = VenueListFragment.class.getSimpleName();

    private final VenueListContract.Presentation mListPresenter = new VenueListPresenter(this);
    private Callback mListener;

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        initRecyclerView(view);
        mListPresenter.onViewCreated();
        mListPresenter.setOnItemListener(new VenueListContract.Presentation.OnItemListener() {

            @Override
            public void onClick(final String pVenueId) {
                mListener.onItemSelected(pVenueId);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_venue_list;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mListener = (Callback) context;
        } else {
            throw new RuntimeException(context + " must implement Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecyclerView(final View pView) {
        final RecyclerView recyclerView = (RecyclerView) pView.findViewById(R.id.recycler_view_venue_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mListPresenter.getVenueListAdapter());
    }
}
