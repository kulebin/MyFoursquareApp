package com.github.kulebin.myfoursquareapp.app;

import android.app.Application;
import android.content.Context;

import com.github.kulebin.myfoursquareapp.http.IHttpClient;
import com.github.kulebin.myfoursquareapp.thread.IThreadManager;

public class App extends Application {

    private IThreadManager mThreadManager;
    private IHttpClient mHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mThreadManager = IThreadManager.Impl.newInstance();
        mHttpClient = IHttpClient.Impl.newInstance();
        ContextHolder.set(this);
    }

    @Override
    public Object getSystemService(final String pServiceKey) {
        if (IThreadManager.APP_SERVICE_KEY.equals(pServiceKey)) {
            return mThreadManager;
        } else if (IHttpClient.APP_SERVICE_KEY.equals(pServiceKey)) {
            return mHttpClient;
        }
        return super.getSystemService(pServiceKey);
    }

    public static <T> T get(Context pContext, final String pServiceKey) {
        if (pContext == null || pServiceKey == null) {
            throw new IllegalArgumentException();
        }

        T systemService = (T) pContext.getSystemService(pServiceKey);

        if (systemService == null) {
            pContext = pContext.getApplicationContext();
            systemService = (T) pContext.getSystemService(pServiceKey);
        }

        if (systemService == null) {
            throw new IllegalStateException(pServiceKey + " not available");
        }

        return systemService;
    }

    public static <T> T get(final String pServiceKey) {
        if (pServiceKey == null) {
            throw new IllegalArgumentException();
        }

        T systemService = (T) ContextHolder.get().getSystemService(pServiceKey);

        if (systemService == null) {
            throw new IllegalStateException(pServiceKey + " not available");
        }

        return systemService;
    }

}
