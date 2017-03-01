package com.github.kulebin.myfoursquareapp.dataSource;

import android.os.Handler;

import com.github.kulebin.myfoursquareapp.http.IHttpClient;

import java.io.IOException;

class DataLoader implements IDataLoader {

    @Override
    public <Result> void loadData(final String url, final IOnResultCallback<Result> pOnResultCallback) {

        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                pOnResultCallback.onStart();
                IHttpClient.Impl.get().doRequest(url, new IHttpClient.IOnResult() {

                    @Override
                    public void onSuccess(final String result) {
                        //todo parse data and pass result in onSuccess callback
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pOnResultCallback.onSuccess(null);
                            }
                        });
                    }

                    @Override
                    public void onError(final IOException e) {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pOnResultCallback.onError(e);
                            }
                        });
                    }
                });
            }
        }).start();

    }
}
