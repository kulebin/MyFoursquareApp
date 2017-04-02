package com.github.kulebin.myfoursquareapp.dataSource;

import android.os.Handler;
import android.os.Message;

import com.github.kulebin.myfoursquareapp.dataSource.processor.IProcessor;
import com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.HttpRequestType;
import com.github.kulebin.myfoursquareapp.http.IHttpClient;

import java.io.IOException;

class DataLoader implements IDataLoader {

    private static final int START_MESSAGE = 0;
    private static final int SUCCESS_MESSAGE = 1;
    private static final int ERROR_MESSAGE = 2;

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
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(final Message msg) {
                switch (msg.what) {
                    case START_MESSAGE:
                        pOnResultCallback.onStart();

                        break;
                    case SUCCESS_MESSAGE:
                        pOnResultCallback.onSuccess((Result) msg.obj);

                        break;
                    case ERROR_MESSAGE:
                        pOnResultCallback.onError((Exception) msg.obj);

                        break;
                }
            }
        };

        handler.sendEmptyMessage(START_MESSAGE);

        new Thread(new Runnable() {

            @Override
            public void run() {
                IHttpClient.Impl.get().doRequest(pRequest, new IHttpClient.IOnResult<String>() {

                    Message message;

                    @Override
                    public void onSuccess(final String result) {
                        try {
                            final Result data = IProcessor.Impl.get(pType).processData(result);

                            if (data != null) {
                                message = handler.obtainMessage(SUCCESS_MESSAGE, data);
                                handler.sendMessage(message);
                            } else {
                                message = handler.obtainMessage(ERROR_MESSAGE, new Exception("Error occurred during parsing: " + result));
                            }
                        } catch (final Exception pE) {
                            message = handler.obtainMessage(ERROR_MESSAGE, pE);
                        }
                    }

                    @Override
                    public void onError(final IOException pE) {
                        message = handler.obtainMessage(ERROR_MESSAGE, pE);
                    }
                });
            }
        }).start();
    }
}
