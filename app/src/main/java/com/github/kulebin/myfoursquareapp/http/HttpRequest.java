package com.github.kulebin.myfoursquareapp.http;

import java.util.Map;

public final class HttpRequest {

    private HttpRequestType mRequestType;
    private String mUrl;
    private Map<String, String> mHeaders;
    private String mBody;

    private HttpRequest(final HttpRequestType pRequestType, final String pUrl, final Map<String, String> pHeaders, final String pBody) {
        mRequestType = pRequestType;
        mUrl = pUrl;
        mHeaders = pHeaders;
        mBody = pBody;
    }

    public HttpRequestType getRequestType() {
        return mRequestType;
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public String getBody() {
        return mBody;
    }

    public static class Builder {

        private HttpRequestType requestType;
        private String url;
        private Map<String, String> headers;
        private String body;

        public Builder setRequestType(final HttpRequestType pRequestType) {
            this.requestType = pRequestType;
            return this;
        }

        public Builder setUrl(final String pUrl) {
            this.url = pUrl;
            return this;
        }

        public Builder setHeaders(final Map<String, String> pHeaders) {
            this.headers = pHeaders;
            return this;
        }

        public Builder setBody(final String pBody) {
            this.body = pBody;
            return this;
        }

        public HttpRequest build() {

            return new HttpRequest(requestType, url, headers, body);
        }
    }
}
