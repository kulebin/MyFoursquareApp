package com.github.kulebin.myfoursquareapp.imageLoader;

import android.support.annotation.DrawableRes;

public class DisplayOptions {

    private int width;
    private int height;
    @DrawableRes
    private int placeholderId;

    public DisplayOptions(@DrawableRes final int pPlaceholderId) {
        placeholderId = pPlaceholderId;
    }

    public DisplayOptions(final int pWidth, final int pHeight) {
        width = pWidth;
        height = pHeight;
    }

    public DisplayOptions(final int pWidth, final int pHeight, @DrawableRes final int pPlaceholderId) {
        width = pWidth;
        height = pHeight;
        placeholderId = pPlaceholderId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlaceholderId() {
        return placeholderId;
    }

    public void setWidth(final int pWidth) {
        width = pWidth;
    }

    public void setHeight(final int pHeight) {
        height = pHeight;
    }

    public void setPlaceholderId(@DrawableRes final int pPlaceholderId) {
        placeholderId = pPlaceholderId;
    }
}
