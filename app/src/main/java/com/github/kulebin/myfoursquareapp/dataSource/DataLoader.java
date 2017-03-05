package com.github.kulebin.myfoursquareapp.dataSource;

import android.os.Handler;

import com.github.kulebin.myfoursquareapp.dataSource.processor.IProcessor;
import com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType;
import com.github.kulebin.myfoursquareapp.http.IHttpClient;

import java.io.IOException;

class DataLoader implements IDataLoader {

    @Override
    public <Result> void loadData(final String url, final IOnResultCallback<Result> pOnResultCallback, final ProcessorType pProcessorType) {

        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                pOnResultCallback.onStart();
                IHttpClient.Impl.get().doRequest(url, new IHttpClient.IOnResult() {

                    @Override
                    public void onSuccess(final String result) {
                        try {
                            final Result data = IProcessor.Impl.get(pProcessorType).processData(result);

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    pOnResultCallback.onSuccess(data);
                                }
                            });
                        } catch (final Exception pE) {
                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    pOnResultCallback.onError(pE);
                                }
                            });
                        }
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
