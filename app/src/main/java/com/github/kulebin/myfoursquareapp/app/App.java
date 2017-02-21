package com.github.kulebin.myfoursquareapp.app;

import android.app.Application;
import android.content.Context;

import com.github.kulebin.myfoursquareapp.thread.IThreadManager;

public class App extends Application {

    private IThreadManager mThreadManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mThreadManager = IThreadManager.Impl.newInstance();
        ContextHolder.set(this);
    }

    @Override
    public Object getSystemService(final String pName) {
        if (IThreadManager.APP_SERVICE_KEY.equals(pName)) {
            return mThreadManager;
        }
        return super.getSystemService(pName);
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

}
