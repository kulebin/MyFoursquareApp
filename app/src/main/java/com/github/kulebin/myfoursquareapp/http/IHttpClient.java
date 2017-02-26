package com.github.kulebin.myfoursquareapp.http;

import com.github.kulebin.myfoursquareapp.app.App;

import java.io.IOException;

public interface IHttpClient {

    String APP_SERVICE_KEY = "httpClient";

    interface IOnResult {

        void onSuccess(String result);

        void onError(IOException e);
    }

    void doRequest(HttpRequest pHttpRequest, IOnResult pIOnResult);

    void doRequest(String pUrl, IOnResult pIOnResult);

    final class Impl {

        public static IHttpClient get() {
            return App.get(APP_SERVICE_KEY);
        }

        public static IHttpClient newInstance() {
            return new HttpClient();
        }
    }
}