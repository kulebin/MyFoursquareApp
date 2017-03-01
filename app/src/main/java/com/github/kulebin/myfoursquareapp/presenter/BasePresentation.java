package com.github.kulebin.myfoursquareapp.presenter;

public interface BasePresentation {

    void onError(Exception e);

    void setProgress(boolean isVisible);

}
