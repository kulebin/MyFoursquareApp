package com.github.kulebin.myfoursquareapp.http;

import android.content.Context;

import java.io.IOException;

public interface IHttpErrorHandler {

    void handleError(final IOException e);

    final class Impl {

        public static IHttpErrorHandler newInstance(final Context pContext) {
            return new HttpErrorHandler(pContext);
        }
    }
}
