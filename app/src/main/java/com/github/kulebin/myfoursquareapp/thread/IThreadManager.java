package com.github.kulebin.myfoursquareapp.thread;

import com.github.kulebin.myfoursquareapp.app.App;
import com.github.kulebin.myfoursquareapp.app.ContextHolder;

public interface IThreadManager {

    String APP_SERVICE_KEY = "thread:manager";

    <Params, Progress, Result> void execute(final ITask<Params, Progress, Result> task, final Params param, final OnResultCallback<Result, Progress> onResultCallback);

    final class Impl {

        public static IThreadManager get() {
            return App.get(ContextHolder.get(), APP_SERVICE_KEY);
        }

        public static IThreadManager newInstance() {
            return new ThreadManager();
        }
    }
}
