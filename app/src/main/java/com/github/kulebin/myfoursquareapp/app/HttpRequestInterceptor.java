package com.github.kulebin.myfoursquareapp.app;

import android.net.Uri;

import com.github.kulebin.myfoursquareapp.BuildConfig;
import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.IInterceptor;

import static com.github.kulebin.myfoursquareapp.http.HttpRequestType.POST;

class HttpRequestInterceptor implements IInterceptor.IRequestIntercept {

    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_CLIENT_SECRET = "client_secret";
    private static final String PARAM_TEMPLATE = "&%s=%s";

    @Override
    public void interceptRequest(final HttpRequest request) {
        final Uri uri = Uri.parse(request.getUrl());

        if (Api.AUTHORITY.equals(uri.getAuthority())) {
            if (request.getRequestType() == POST) {
                request.setBody(appendAccessCredentialsToBody(request.getBody()));
            } else {
                request.setUrl(appendAccessCredentials(uri));
            }
        }
    }

    private String appendAccessCredentials(final Uri pUri) {
        return pUri.buildUpon()
                .appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.MY_4SQUARE_APP_CLIENT_ID)
                .appendQueryParameter(PARAM_CLIENT_SECRET, BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET)
                .build()
                .toString();
    }

    private String appendAccessCredentialsToBody(final String pBody) {
        return pBody + String.format(PARAM_TEMPLATE, PARAM_CLIENT_ID, BuildConfig.MY_4SQUARE_APP_CLIENT_ID)
                + String.format(PARAM_TEMPLATE, PARAM_CLIENT_SECRET, BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET);
    }
}
