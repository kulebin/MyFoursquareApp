package com.github.kulebin.myfoursquareapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
    public static final String LIST_STATE_KEY = "LIST_STATE_KEY";

    private final VenueListContract.Presentation mListPresenter = new VenueListPresenter(this);
    private Callback mListener;
    private RecyclerView mRecyclerView;
    private Parcelable mListState;

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

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }

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
            throw new RuntimeException(context.getClass().getCanonicalName() + " must implement " + Callback.class.getCanonicalName());
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(LIST_STATE_KEY, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void restoreListState() {
        if (mListState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
        }
    }

    private void initRecyclerView(final View pView) {
        mRecyclerView = (RecyclerView) pView.findViewById(R.id.recycler_view_venue_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mListPresenter.getVenueListAdapter());
    }
}
