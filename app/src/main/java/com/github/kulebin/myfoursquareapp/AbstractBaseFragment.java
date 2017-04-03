package com.github.kulebin.myfoursquareapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.kulebin.myfoursquareapp.view.BaseContract;

public abstract class AbstractBaseFragment extends Fragment implements BaseContract.View {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(getViewLayout(), container, false);
    }

    @Override
    public void showProgress(final boolean isVisible) {
        final View progress = getActivity().findViewById(R.id.progress);
        if (progress != null) {
            progress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showError(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract int getViewLayout();
}
