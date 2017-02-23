package com.github.kulebin.myfoursquareapp.http;

import java.io.IOException;

public class HttpRequestException extends IOException {

    private static final long serialVersionUID = 5326458803268867891L;

    private final int mResponseCode;
    private final HttpRequest mHttpRequest;
    private final IHttpClient.IOnResult mIOnResult;

    public HttpRequestException(final int pResponseCode, final String pErrorMessage,
                                final HttpRequest pHttpRequest, final IHttpClient.IOnResult pIOnResult) {
        super(pErrorMessage);
        this.mResponseCode = pResponseCode;
        this.mHttpRequest = pHttpRequest;
        this.mIOnResult = pIOnResult;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public HttpRequest getHttpRequest() {
        return mHttpRequest;
    }

    public IHttpClient.IOnResult getIOnResult() {
        return mIOnResult;
    }
}
