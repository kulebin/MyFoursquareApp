package com.github.kulebin.myfoursquareapp.app;

import android.net.Uri;

import com.github.kulebin.myfoursquareapp.BuildConfig;
import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.IInterceptor;

import static com.github.kulebin.myfoursquareapp.http.HttpRequestType.POST;

class HttpRequestInterceptor implements IInterceptor.IRequestIntercept {

    private static final String PARAM_TEMPLATE = "&%s=%s";
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_CLIENT_SECRET = "client_secret";
    private static final String APP_CLIENT_ID = BuildConfig.MY_4SQUARE_APP_CLIENT_ID;
    private static final String APP_CLIENT_SECRET = BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET;
    private static final String FORMATTED_PARAM_CLIENT_ID = String.format(PARAM_TEMPLATE, PARAM_CLIENT_ID, BuildConfig.MY_4SQUARE_APP_CLIENT_ID);
    private static final String FORMATTED_PARAM_CLIENT_SECRET = String.format(PARAM_TEMPLATE, PARAM_CLIENT_SECRET, BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET);

    @Override
    public void interceptRequest(final HttpRequest request) {
        if (Api.AUTHORITY.equals(request.getUrl().getAuthority())) {
            if (request.getRequestType() == POST) {
                request.setBody(appendAccessCredentialsToBody(request.getBody()));
            } else {
                request.setUrl(appendAccessCredentials(request.getUrl()));
            }
        }
    }

    private Uri appendAccessCredentials(final Uri pUrl) {
        return pUrl.buildUpon()
                .appendQueryParameter(PARAM_CLIENT_ID, APP_CLIENT_ID)
                .appendQueryParameter(PARAM_CLIENT_SECRET, APP_CLIENT_SECRET)
                .build();
    }

    private String appendAccessCredentialsToBody(final String pBody) {
        return pBody + FORMATTED_PARAM_CLIENT_ID + FORMATTED_PARAM_CLIENT_SECRET;
    }
}
