package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.github.kulebin.myfoursquareapp.view.VenueDetailFragment;
import com.github.kulebin.myfoursquareapp.view.VenueListFragment;

public class MainActivity extends AppCompatActivity implements VenueListFragment.Callback {

    private boolean isTwoPaneMode;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentByTag(VenueListFragment.TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new VenueListFragment(), VenueListFragment.TAG)
                    .commit();
        }

        if (findViewById(R.id.container_venue_detail) != null) {
            isTwoPaneMode = true;
        }
    }

    @Override
    public void onItemSelected(final String pVenueId) {
        if (isTwoPaneMode) {
            findViewById(R.id.text_no_venue_selected).setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_venue_detail, VenueDetailFragment.newInstance(pVenueId, true), VenueDetailFragment.TAG)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VenueDetailFragment.newInstance(pVenueId), VenueDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportFragmentManager().popBackStack();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
