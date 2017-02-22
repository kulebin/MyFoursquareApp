package com.github.kulebin.myfoursquareapp.http;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static java.net.HttpURLConnection.HTTP_UNAVAILABLE;

class HttpErrorHandler implements IHttpErrorHandler {

    private static final String TAG = HttpErrorHandler.class.getSimpleName();

    @Override
    public void handleError(final IOException pException) {
        if (pException instanceof HttpRequestException) {
            final HttpRequestException httpRequestException = (HttpRequestException) pException;
            switch (httpRequestException.getResponseCode()) {
                case HTTP_UNAUTHORIZED:
                    // handle error
                    break;
                case HTTP_INTERNAL_ERROR:
                    // handle error
                    break;
                case HTTP_UNAVAILABLE:
                    // handle error
                    break;
                default:
                    // handle error
            }
        } else {
            // handle IO Exception
        }
    }
}
