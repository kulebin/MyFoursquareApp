package com.github.kulebin.myfoursquareapp.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpClient implements IHttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();
    private static final int READ_TIMEOUT = 5000;
    private static final int CONNECTION_TIMEOUT = 8000;

    private IInterceptor.IRequestIntercept mRequestInterceptor;
    private IInterceptor.IResponseIntercept mResponseInterceptor;

    private final IOnResultConvert<String> mOnResultStringConvert = new IOnResultConvert<String>() {

        @Override
        public String convert(final InputStream pInputStream) throws IOException {
            BufferedReader reader = null;
            StringBuilder stringBuilder = null;

            try {
                reader = new BufferedReader(new InputStreamReader(pInputStream));
                stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error occurred during BufferedReader closing", e);
                    }
                }
            }
            return stringBuilder.toString();
        }
    };

    @Override
    public void doRequest(final String pUrl, final IOnResult pIOnResult) {
        final HttpRequest httpRequest = new HttpRequest.Builder()
                .setUrl(pUrl)
                .setRequestType(HttpRequestType.GET)
                .build();
        doRequest(httpRequest, pIOnResult);
    }

    @Override
    public void doRequest(final String pUrl, final IOnResult pIOnResult, final IOnResultConvert pOnResultConvert) {
        final HttpRequest httpRequest = new HttpRequest.Builder()
                .setUrl(pUrl)
                .setRequestType(HttpRequestType.GET)
                .build();
        doRequest(httpRequest, pIOnResult, pOnResultConvert);
    }

    @Override
    public void doRequest(final HttpRequest pHttpRequest, final IOnResult pIOnResult) {
        doRequest(pHttpRequest, pIOnResult, mOnResultStringConvert);
    }

    @Override
    public void doRequest(final HttpRequest pHttpRequest, final IOnResult pIOnResult, final IOnResultConvert pOnResultConvert) {
        execute(pHttpRequest, pIOnResult, pOnResultConvert);
    }

    @Override
    public void setRequestInterceptor(final IInterceptor.IRequestIntercept pInterceptor) {
        this.mRequestInterceptor = pInterceptor;
    }

    @Override
    public void setResponseInterceptor(final IInterceptor.IResponseIntercept pInterceptor) {
        this.mResponseInterceptor = pInterceptor;
    }

    private void applyBody(final HttpURLConnection httpURLConnection, final String body) throws IOException {
        final byte[] outputInBytes = body.getBytes("UTF-8");
        final OutputStream os = httpURLConnection.getOutputStream();
        os.write(outputInBytes);
        os.close();
    }

    private <Result> void execute(final HttpRequest pHttpRequest, final IOnResult<Result> pIOnResult, final IOnResultConvert<Result> pOnResultConvert) {
        if (mRequestInterceptor != null) {
            mRequestInterceptor.interceptRequest(pHttpRequest);
        }

        HttpURLConnection connection = null;
        Result response = null;
        IOException exception = null;
        InputStream inputStream = null;
        int responseCode = -1;
        boolean isSuccess;
        String errorMessage = null;

        try {
            final URL reqUrl = new URL(pHttpRequest.getUrl());
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod(pHttpRequest.getRequestType().name());
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            if (pHttpRequest.getHeaders() != null) {
                for (final String key : pHttpRequest.getHeaders().keySet()) {
                    connection.addRequestProperty(key, pHttpRequest.getHeaders().get(key));
                }
            }

            if (pHttpRequest.getBody() != null) {
                applyBody(connection, pHttpRequest.getBody());
            }

            responseCode = connection.getResponseCode();
            isSuccess = connection.getResponseCode() >= 200 && responseCode < 300;

            if (isSuccess) {
                inputStream = connection.getInputStream();
                response = pOnResultConvert.convert(inputStream);
            } else {
                inputStream = connection.getErrorStream();
                errorMessage = mOnResultStringConvert.convert(inputStream);
            }
        } catch (final IOException e) {
            isSuccess = false;
            exception = e;

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException pE) {
                    Log.e(TAG, "Error closing stream", pE);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (mResponseInterceptor != null) {
            mResponseInterceptor.interceptResponse(responseCode, pHttpRequest.getUrl());
        }

        if (isSuccess) {
            pIOnResult.onSuccess(response);
        } else if (exception == null) {
            pIOnResult.onError(new HttpRequestException(responseCode, errorMessage, pHttpRequest, pIOnResult));
        } else {
            pIOnResult.onError(exception);
        }
    }
}