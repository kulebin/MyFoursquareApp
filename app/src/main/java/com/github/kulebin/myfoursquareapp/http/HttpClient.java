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

    @Override
    public void doRequest(final HttpRequest pHttpRequest, final IOnResult pIOnResult) {
        execute(pHttpRequest, pIOnResult);
    }

    @Override
    public void doRequest(final String pUrl, final IOnResult pIOnResult) {
        final HttpRequest httpRequest = new HttpRequest.Builder()
                .setUrl(pUrl)
                .setRequestType(HttpRequestType.GET)
                .build();
        execute(httpRequest, pIOnResult);
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

    private void execute(final HttpRequest pHttpRequest, final IOnResult pIOnResult) {
        if (mRequestInterceptor != null) {
            mRequestInterceptor.interceptRequest(pHttpRequest);
        }

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        IOException exception = null;
        String response = null;
        int responseCode = -1;
        boolean isSuccess;

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

            final InputStream inputStream;
            responseCode = connection.getResponseCode();

            isSuccess = connection.getResponseCode() >= 200 && responseCode < 300;
            if (isSuccess) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            response = stringBuilder.toString();
            inputStream.close();

        } catch (final IOException e) {
            isSuccess = false;
            exception = e;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        if (mResponseInterceptor != null) {
            mResponseInterceptor.interceptResponse(responseCode, pHttpRequest.getUrl());
        }

        if (isSuccess) {
            pIOnResult.onSuccess(response);
        } else if (exception == null) {
            pIOnResult.onError(new HttpRequestException(responseCode, response, pHttpRequest, pIOnResult));
        } else {
            pIOnResult.onError(exception);
        }
    }
}
