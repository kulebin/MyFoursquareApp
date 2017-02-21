package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.kulebin.myfoursquareapp.view.IViewCallback;
import com.github.kulebin.myfoursquareapp.presenter.VenueListPresenter;

public class MainActivity extends AppCompatActivity implements IViewCallback {

    private final VenueListPresenter mListPresenter = new VenueListPresenter(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        mListPresenter.onViewCreated();
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.venueRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mListPresenter.getVenueListAdapter());
    }

    @Override
    public void showProgress(final boolean isVisible) {
        findViewById(R.id.progress).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
