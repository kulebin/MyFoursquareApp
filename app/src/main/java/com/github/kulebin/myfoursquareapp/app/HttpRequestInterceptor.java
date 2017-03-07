package com.github.kulebin.myfoursquareapp.app;

import android.net.Uri;

import com.github.kulebin.myfoursquareapp.BuildConfig;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.IInterceptor;

class HttpRequestInterceptor implements IInterceptor.IRequestIntercept {

    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_CLIENT_SECRET = "client_secret";

    @Override
    public HttpRequest interceptRequest(final HttpRequest request) {
        return new HttpRequest.Builder()
                .setRequestType(request.getRequestType())
                .setUrl(appendAccessCredentials(request.getUrl()))
                .setHeaders(request.getHeaders())
                .setBody(request.getBody())
                .build();
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
