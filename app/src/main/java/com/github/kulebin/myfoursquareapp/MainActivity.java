package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements VenueListFragment.Callback {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .add(new VenueListFragment(), VenueListFragment.TAG)
                .commit();
    }

    @Override
    public void onItemSelected(final String pVenueId) {
        getSupportFragmentManager().beginTransaction()
                .add(VenueDetailFragment.newInstance(pVenueId), VenueDetailFragment.TAG)
                .commit();
    }
}
