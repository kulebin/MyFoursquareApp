package com.github.kulebin.myfoursquareapp.thread;

public interface OnResultCallback<Result, Progress> extends ProgressCallback<Progress> {

    void onStart();

    void onSuccess(Result result);

    void onError(Exception e);
}

