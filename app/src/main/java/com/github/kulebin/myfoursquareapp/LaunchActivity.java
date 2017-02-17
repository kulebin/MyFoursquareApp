package com.github.kulebin.myfoursquareapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    private static final int SPLASH_SHOW_TIME = 1000;

    private Handler mHandler;
    private final Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            final Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mHandler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(mRunnable, SPLASH_SHOW_TIME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }
}
