package com.github.kulebin.myfoursquareapp;

import android.app.Application;

import com.github.kulebin.myfoursquareapp.thread.IThreadManager;

public class App extends Application {

    private IThreadManager mThreadManager;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.set(this);
    }

    @Override
    public Object getSystemService(final String pName) {
        if (IThreadManager.APP_SERVICE_KEY.equals(pName)) {
            if (mThreadManager == null) {
                mThreadManager = IThreadManager.Impl.newInstance();
            }
            return mThreadManager;
        }
        return super.getSystemService(pName);
    }

}
