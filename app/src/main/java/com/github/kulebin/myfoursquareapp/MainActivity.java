package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.kulebin.myfoursquareapp.view.VenueDetailFragment;
import com.github.kulebin.myfoursquareapp.view.VenueListFragment;

public class MainActivity extends AppCompatActivity implements VenueListFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    private boolean isTwoPaneMode;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawer;
    private FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {

        @Override
        public void onBackStackChanged() {
            if (!isTwoPaneMode && getSupportFragmentManager().getBackStackEntryCount() == 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                setDrawerState(true);
            }

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavigationMenu();

        final FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(mOnBackStackChangedListener);

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
                    .replace(R.id.container_venue_detail, VenueDetailFragment.newInstance(pVenueId), VenueDetailFragment.TAG)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VenueDetailFragment.newInstance(pVenueId), VenueDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();

            setDrawerState(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void initNavigationMenu() {
        initMenuToggle();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initMenuToggle() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void setDrawerState(final boolean isEnabled) {
        if (isEnabled) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mToggle.setDrawerIndicatorEnabled(true);
            mToggle.syncState();
        } else {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mToggle.setDrawerIndicatorEnabled(false);
            mToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (mToggle.getToolbarNavigationClickListener() == null) {
                mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        getSupportFragmentManager().popBackStack();
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        setDrawerState(true);
                    }
                });
            }
        }
    }
}
