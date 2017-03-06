package com.github.kulebin.myfoursquareapp.http;

public interface IInterceptor {

    <T> T onPreExecute(T request);

    void onRequestExecuted(int statusCode, String url);

    final class Impl {

        public static IInterceptor newInstance() {
            return new Interceptor();
        }
    }

}
