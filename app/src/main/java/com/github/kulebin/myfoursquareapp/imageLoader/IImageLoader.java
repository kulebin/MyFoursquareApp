package com.github.kulebin.myfoursquareapp.imageLoader;

import android.widget.ImageView;

import com.github.kulebin.myfoursquareapp.app.App;

public interface IImageLoader {

    String APP_SERVICE_KEY = "imageLoader";

    void draw(String pUrl, ImageView pView);

    void draw(String pUrl, ImageView pView, int pWidth, int pHeight);

    final class Impl {

        public static IImageLoader get() {
            return App.get(APP_SERVICE_KEY);
        }

        public static IImageLoader newInstance() {
            return new ImageLoader();
        }
    }
}
