package com.github.kulebin.myfoursquareapp.http;

import java.io.IOException;

public interface IHttpClient {

    interface IOnResult {

        void onSuccess(String result);

        void onError(IOException e);
    }

    void doRequest(HttpRequest pHttpRequest, IOnResult pIOnResult);

    final class Impl {

        public static IHttpClient newInstance() {
            return new HttpClient();
        }
    }
}