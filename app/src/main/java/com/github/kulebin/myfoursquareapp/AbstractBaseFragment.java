package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.kulebin.myfoursquareapp.view.IViewCallback;

public abstract class AbstractBaseFragment extends Fragment implements IViewCallback {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(getViewLayout(), container, false);
    }

    @Override
    public void showProgress(final boolean isVisible) {
        if (getView() != null) {
            getView().findViewById(R.id.progress).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showError(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract int getViewLayout();
}
