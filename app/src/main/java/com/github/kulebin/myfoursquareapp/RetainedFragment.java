package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class RetainedFragment extends Fragment {

    private Object mDataObject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void setData(final Object pDataObject) {
        this.mDataObject = pDataObject;
    }

    public Object getData() {
        return mDataObject;
    }
}
