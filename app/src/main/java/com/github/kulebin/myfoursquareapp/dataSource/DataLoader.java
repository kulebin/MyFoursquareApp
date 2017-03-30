package com.github.kulebin.myfoursquareapp.dataSource;

import android.os.Handler;

import com.github.kulebin.myfoursquareapp.dataSource.processor.IProcessor;
import com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.HttpRequestType;
import com.github.kulebin.myfoursquareapp.http.IHttpClient;

import java.io.IOException;

class DataLoader implements IDataLoader {

    @Override
    public <Result> void loadData(final String pUrl, final IOnResultCallback<Result> pOnResultCallback, final ProcessorType pProcessorType) {
        final HttpRequest request = new HttpRequest.Builder()
                .setRequestType(HttpRequestType.GET)
                .setUrl(pUrl)
                .build();
        loadData(request, pOnResultCallback, pProcessorType);
    }

    @Override
    public <Result> void loadData(final HttpRequest pRequest, final IOnResultCallback<Result> pOnResultCallback, final ProcessorType pType) {
        final Handler handler = new Handler();
        pOnResultCallback.onStart();

        new Thread(new Runnable() {

            @Override
            public void run() {
                IHttpClient.Impl.get().doRequest(pRequest, new IHttpClient.IOnResult<String>() {

                    @Override
                    public void onSuccess(final String result) {
                        try {
                            final Result data = IProcessor.Impl.get(pType).processData(result);

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
