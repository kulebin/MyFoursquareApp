package com.github.kulebin.myfoursquareapp.app;

import android.content.Context;

public enum ContextHolder {

    INSTANCE;

    private Context mContext;

    public static Context get() {
        return INSTANCE.mContext;
    }

    public static void set(final Context pContext) {
        INSTANCE.mContext = pContext;
    }

}
