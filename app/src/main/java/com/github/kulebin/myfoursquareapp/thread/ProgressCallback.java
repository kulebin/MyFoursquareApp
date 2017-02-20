package com.github.kulebin.myfoursquareapp.thread;

public interface ProgressCallback<Progress> {

    void onProgressChanged(Progress progress);
}
