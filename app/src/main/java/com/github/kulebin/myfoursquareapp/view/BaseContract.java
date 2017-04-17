package com.github.kulebin.myfoursquareapp.view;

public interface BaseContract {

    interface View {

        void showProgress(boolean isVisible);

        void showError(String message);

    }
}
