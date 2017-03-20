package com.github.kulebin.myfoursquareapp.app;

import com.github.kulebin.myfoursquareapp.BuildConfig;
import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.IInterceptor;

import static com.github.kulebin.myfoursquareapp.http.HttpRequestType.POST;

class HttpRequestInterceptor implements IInterceptor.IRequestIntercept {

    private static final String PARAM_TEMPLATE = "&%s=%s";
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_CLIENT_SECRET = "client_secret";
    private static final String FORMATTED_PARAM_CLIENT_ID = String.format(PARAM_TEMPLATE, PARAM_CLIENT_ID, BuildConfig.MY_4SQUARE_APP_CLIENT_ID);
    private static final String FORMATTED_PARAM_CLIENT_SECRET = String.format(PARAM_TEMPLATE, PARAM_CLIENT_SECRET, BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET);

    @Override
    public void interceptRequest(final HttpRequest request) {
        if (request.getUrl().startsWith(Api.BASE_URL)) {
            if (request.getRequestType() == POST) {
                request.setBody(appendAccessCredentials(request.getBody()));
            } else {
                request.setUrl(appendAccessCredentials(request.getUrl()));
            }
        }
    }

    private String appendAccessCredentials(final String pBase) {
        return pBase + FORMATTED_PARAM_CLIENT_ID + FORMATTED_PARAM_CLIENT_SECRET;
    }
}
