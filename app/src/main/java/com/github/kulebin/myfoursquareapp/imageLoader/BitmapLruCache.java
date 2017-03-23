package com.github.kulebin.myfoursquareapp.imageLoader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

class BitmapLruCache {

    private static final int MAX_CACHE_SIZE = 32 * 1000 * 1000;

    private LruCache<String, Bitmap> mLruCache;

    public BitmapLruCache() {
        initLruCache(getCacheSize(MAX_CACHE_SIZE));
    }

    public BitmapLruCache(final int pCacheSize) {
        initLruCache(getCacheSize(pCacheSize));
    }

    public void addBitmapToCache(final String pUrl, final Bitmap pBitmap) {
        mLruCache.put(pUrl, pBitmap);
    }

    public Bitmap getBitmapFromCache(final String pUrl) {
        return mLruCache.get(pUrl);
    }

    private void initLruCache(final int pCacheSize) {
        mLruCache = new LruCache<String, Bitmap>(pCacheSize) {

            @Override
            protected int sizeOf(final String key, final Bitmap value) {
                return key.length() + value.getByteCount();
            }
        };
    }

    private int getCacheSize(final int pMaxCacheSize) {
        return Math.min((int) (Runtime.getRuntime().maxMemory() / 6), pMaxCacheSize);
    }
}
