package com.github.kulebin.myfoursquareapp.http;

public interface IInterceptor {

    interface IRequestIntercept {

        void interceptRequest(HttpRequest request);
    }

    interface IResponseIntercept {

        void interceptResponse(int statusCode, String url);
    }
}
