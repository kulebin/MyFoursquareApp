package com.github.kulebin.myfoursquareapp.http;

public interface IInterceptor {

    interface IRequestIntercept {

        HttpRequest interceptRequest(HttpRequest request);
    }

    interface IResponseIntercept {

        void interceptResponse(int statusCode, String url);
    }
}
