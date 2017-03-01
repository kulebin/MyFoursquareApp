package com.github.kulebin.myfoursquareapp.dataSource;

public interface IOnResultCallback<Result> {

    void onStart();

    void onSuccess(Result result);

    void onError(Exception e);
}
