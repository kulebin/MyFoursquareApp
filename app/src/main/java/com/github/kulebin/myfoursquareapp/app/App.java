package com.github.kulebin.myfoursquareapp.app;

import android.app.Application;
import android.content.Context;

import com.github.kulebin.myfoursquareapp.dataSource.IDataSource;
import com.github.kulebin.myfoursquareapp.http.IHttpClient;
import com.github.kulebin.myfoursquareapp.thread.IThreadManager;

public class App extends Application {

    private IThreadManager mThreadManager;
    private IHttpClient mHttpClient;
    private IDataSource mDataSource;

    @Override
    public void onCreate() {
        super.onCreate();

        mThreadManager = IThreadManager.Impl.newInstance();
        initHttpClient();
        mDataSource = IDataSource.Impl.newInstance();
        ContextHolder.set(this);
    }

    @Override
    public Object getSystemService(final String pServiceKey) {
        if (IThreadManager.APP_SERVICE_KEY.equals(pServiceKey)) {
            return mThreadManager;
        } else if (IHttpClient.APP_SERVICE_KEY.equals(pServiceKey)) {
            return mHttpClient;
        } else if (IDataSource.APP_SERVICE_KEY.equals(pServiceKey)) {
            return mDataSource;
        }
        return super.getSystemService(pServiceKey);
    }

    public static <T> T get(final Context pContext, final String pServiceKey) {
        if (pContext == null || pServiceKey == null) {
            throw new IllegalArgumentException();
        }

        T systemService = (T) pContext.getSystemService(pServiceKey);

        if (systemService == null) {
            final Context context = pContext.getApplicationContext();
            systemService = (T) context.getSystemService(pServiceKey);
        }

        if (systemService == null) {
            throw new IllegalStateException(pServiceKey + " not available");
        }

        return systemService;
    }

    public static <T> T get(final String pServiceKey) {
        return get(ContextHolder.get(), pServiceKey);
    }

    private void initHttpClient() {
        mHttpClient = IHttpClient.Impl.newInstance();
        mHttpClient.setRequestInterceptor(new HttpRequestInterceptor());
        mHttpClient.setResponseInterceptor(new HttpResponseInterceptor());
    }
}
