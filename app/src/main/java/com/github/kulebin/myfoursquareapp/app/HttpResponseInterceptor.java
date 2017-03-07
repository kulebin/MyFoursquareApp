package com.github.kulebin.myfoursquareapp.app;

import android.util.Log;

import com.github.kulebin.myfoursquareapp.http.IInterceptor;

public class HttpResponseInterceptor implements IInterceptor.IResponseIntercept {

    private static final String TAG = HttpResponseInterceptor.class.getSimpleName();

    @Override
    public void interceptResponse(final int pStatusCode, final String pUrl) {
        if (pStatusCode >= 200 && pStatusCode < 300) {
            Log.d(TAG, "Request: " + pUrl + " has been carried out successfully ");
        }
    }
}
