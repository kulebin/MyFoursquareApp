package com.github.kulebin.myfoursquareapp.thread;

public interface IThreadManager {

    String APP_SERVICE_KEY = "thread:manager";

    <Params, Progress, Result> void execute(final ITask<Params, Progress, Result> task, final Params param, final OnResultCallback<Result, Progress> onResultCallback);

    final class Impl {

        public static IThreadManager newInstance() {
            return new ThreadManager();
        }
    }
}
