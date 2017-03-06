package com.github.kulebin.myfoursquareapp.http;

import android.net.Uri;
import android.util.Log;

import com.github.kulebin.myfoursquareapp.BuildConfig;

class Interceptor implements IInterceptor {

    private static final String TAG = Interceptor.class.getSimpleName();
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_CLIENT_SECRET = "client_secret";

    @Override
    public <T> T onPreExecute(final T request) {
        if (request instanceof String) {
            return (T) appendAccessCredentials((String) request);
        } else if (request instanceof HttpRequest) {
            final HttpRequest httpRequest = (HttpRequest) request;

            return (T) new HttpRequest.Builder()
                    .setRequestType(httpRequest.getRequestType())
                    .setUrl(appendAccessCredentials(httpRequest.getUrl()))
                    .setHeaders(httpRequest.getHeaders())
                    .setBody(httpRequest.getBody())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void onRequestExecuted(final int pStatusCode, final String pUrl) {
        if (pStatusCode >= 200 && pStatusCode < 300) {
            Log.d(TAG, "Request: " + pUrl + " has been carried out successfully ");
        }
    }

    private String appendAccessCredentials(final String pUrl) {
        return Uri.parse(pUrl)
                .buildUpon()
                .appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.MY_4SQUARE_APP_CLIENT_ID)
                .appendQueryParameter(PARAM_CLIENT_SECRET, BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET)
                .build()
                .toString();
    }
}
