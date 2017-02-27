package com.github.kulebin.myfoursquareapp.dataSource;

import android.os.Handler;

import com.github.kulebin.myfoursquareapp.http.IHttpClient;

import java.io.IOException;

class DataLoader implements IDataLoader {

    //todo should be initialized or pass as param
    private IDataCache mDataCache;
    private IProcessor mProcessor;

    @Override
    public <Result> void loadData(final String url, final IOnResultCallback<Result> pOnResultCallback) {

        if (mDataCache.isDataCached(url)) {
            //todo fetch data from cache

        } else {
            final Handler handler = new Handler();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    pOnResultCallback.onStart();
                    IHttpClient.Impl.get().doRequest(url, new IHttpClient.IOnResult() {

                        @Override
                        public void onSuccess(final String result) {
                            final Result data = mProcessor.processData(result);

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    pOnResultCallback.onSuccess(data);
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
}
