package com.github.kulebin.myfoursquareapp.thread;

public interface ITask<Params, Progress, Result> {

    Result perform(Params params, ProgressCallback<Progress> progressCallback) throws Exception;
}
