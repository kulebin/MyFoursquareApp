package com.github.kulebin.myfoursquareapp.thread;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadManager implements IThreadManager {

    private static final int MAX_THREAD_NUMBER = 4;
    private final ExecutorService executorService;

    public ThreadManager() {
        this.executorService = Executors.newFixedThreadPool(MAX_THREAD_NUMBER);
    }

    public <Params, Progress, Result> void execute(final ITask<Params, Progress, Result> task, final Params param, final OnResultCallback<Result, Progress> onResultCallback) {
        final Handler handler = new Handler();
        onResultCallback.onStart();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    final Result result = task.perform(param, new ProgressCallback<Progress>() {

                        @Override
                        public void onProgressChanged(final Progress progress) {
                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    onResultCallback.onProgressChanged(progress);
                                }
                            });
                        }
                    });
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            onResultCallback.onSuccess(result);
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            onResultCallback.onError(e);
                        }
                    });
                }
            }
        });
    }
}
