package com.github.kulebin.myfoursquareapp.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

class HttpClient implements IHttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();
    private static final int READ_TIMEOUT = 5000;
    private static final int CONNECTION_TIMEOUT = 8000;

    @Override
    public void doRequest(final HttpRequest pHttpRequest, final IOnResult pIOnResult) {
        execute(pHttpRequest.getUrl(),
                pHttpRequest.getRequestType(),
                pHttpRequest.getHeaders(),
                pHttpRequest.getBody(),
                pIOnResult);
    }

    @Override
    public void doRequest(final String pUrl, final IOnResult pIOnResult) {
        execute(pUrl, HttpRequestType.GET, null, null, pIOnResult);
    }

    private void applyBody(final HttpURLConnection httpURLConnection, final String body) throws IOException {
        final byte[] outputInBytes = body.getBytes("UTF-8");
        final OutputStream os = httpURLConnection.getOutputStream();
        os.write(outputInBytes);
        os.close();
    }

    private void execute(final String pUrl, final HttpRequestType pRequestType, final Map<String, String> pHeaders, final String pBody, final IOnResult pIOnResult) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        IOException exception = null;
        String response = null;
        int responseCode = -1;
        boolean isSuccess;

        try {
            final URL reqUrl = new URL(pUrl);
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod(pRequestType.name());
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            if (pHeaders != null) {
                for (final String key : pHeaders.keySet()) {
                    connection.addRequestProperty(key, pHeaders.get(key));
                }
            }
            if (pBody != null) {
                applyBody(connection, pBody);
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

        if (isSuccess) {
            pIOnResult.onSuccess(response);
        } else if (exception == null) {
            final HttpRequest httpRequest = new HttpRequest.Builder()
                    .setRequestType(pRequestType)
                    .setUrl(pUrl)
                    .setHeaders(pHeaders)
                    .setBody(pBody)
                    .build();
            pIOnResult.onError(new HttpRequestException(responseCode, response, httpRequest, pIOnResult));
        } else {
            pIOnResult.onError(exception);
        }
    }
}
