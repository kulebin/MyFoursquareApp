package com.github.kulebin.myfoursquareapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.kulebin.myfoursquareapp.presenter.VenueListPresenter;
import com.github.kulebin.myfoursquareapp.view.IVenueListViewCallback;

public class VenueListFragment extends AbstractBaseFragment implements IVenueListViewCallback {

    public interface Callback {

        void onItemSelected(String venueId);
    }

    public static final String TAG = VenueListFragment.class.getSimpleName();

    private final VenueListPresenter mListPresenter = new VenueListPresenter(this);
    private Callback mListener;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        initRecyclerView(view);
        mListPresenter.onViewCreated();

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

    @Override
    public void onVenueSelected(final String pVenueId) {
        mListener.onItemSelected(pVenueId);
    }

    private void initRecyclerView(final View pView) {
        final RecyclerView recyclerView = (RecyclerView) pView.findViewById(R.id.venueRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mListPresenter.getVenueListAdapter());
    }
}
