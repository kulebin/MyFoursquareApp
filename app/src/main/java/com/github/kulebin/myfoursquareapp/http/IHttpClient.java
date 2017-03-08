package com.github.kulebin.myfoursquareapp.http;

import com.github.kulebin.myfoursquareapp.app.App;

import java.io.IOException;
import java.io.InputStream;

public interface IHttpClient {

    String APP_SERVICE_KEY = "httpClient";

    interface IOnResult<Result> {

        void onSuccess(Result result);

        void onError(IOException e);
    }

    void doRequest(String pUrl, IOnResult pIOnResult);

    void doRequest(String pUrl, IOnResult pIOnResult, IOnResultConvert pOnResultConvert);

    void doRequest(HttpRequest pHttpRequest, IOnResult pIOnResult);

    void doRequest(HttpRequest pHttpRequest, IOnResult pIOnResult, IOnResultConvert pOnResultConvert);

    void setRequestInterceptor(IInterceptor.IRequestIntercept pInterceptor);

    void setResponseInterceptor(IInterceptor.IResponseIntercept pInterceptor);

    public interface IOnResultConvert<Result> {

        Result convert(InputStream inputStream) throws IOException;
    }

    final class Impl {

        public static IHttpClient get() {
            return App.get(APP_SERVICE_KEY);
        }

        public static IHttpClient newInstance() {
            return new HttpClient();
        }
    }
}