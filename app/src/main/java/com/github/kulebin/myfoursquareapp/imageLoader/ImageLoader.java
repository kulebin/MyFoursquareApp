package com.github.kulebin.myfoursquareapp.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.github.kulebin.myfoursquareapp.http.IHttpClient;
import com.github.kulebin.myfoursquareapp.util.LifoBlockingDeque;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ImageLoader implements IImageLoader {

    interface ISizeParams {

        int getWidth();

        int getHeight();
    }

    private static final String TAG = ImageLoader.class.getSimpleName();
    private static final int CHUNK_SIZE_POWER = 16; //chunk size = 2^x
    private static final int MAX_THREAD_NUMBER = 3;

    private final Executor mExecutor = new ThreadPoolExecutor(MAX_THREAD_NUMBER, MAX_THREAD_NUMBER, 1000, TimeUnit.MILLISECONDS, new LifoBlockingDeque<Runnable>());
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Lock mLock = new ReentrantLock();
    private final BitmapLruCache mLruCache = new BitmapLruCache();
    private ISizeParams mSizeParamsCallback;

    private final IHttpClient.IOnResultConvert<Bitmap> mBitmapConverter = new IHttpClient.IOnResultConvert<Bitmap>() {

        @Override
        public Bitmap convert(final InputStream inputStream) throws IOException {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
            final BufferedInputStream buff = new BufferedInputStream(inputStream, 1 << CHUNK_SIZE_POWER);
            int byteValue;

            while ((byteValue = buff.read()) != -1) {
                byteArrayOutputStream.write(byteValue);
            }

            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
            options.inSampleSize = calculateInSampleSize(options, mSizeParamsCallback.getWidth(), mSizeParamsCallback.getHeight());
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        }
    };

    @Override
    public void draw(final String pUrl, final ImageView pView) {
        draw(pUrl, pView, null);
    }

    @Override
    public void draw(final String pUrl, final ImageView pView, final DisplayOptions pOptions) {
        if (pUrl == null) {
            setPlaceholder(pView, pOptions.getPlaceholderId());

            return;
        }

        mLock.lock();
        try {
            final Bitmap bitmap = mLruCache.getBitmapFromCache(pUrl);
            if (bitmap != null) {
                pView.setImageBitmap(bitmap);
                pView.setTag(null);
                return;
            }
        } finally {
            mLock.unlock();
        }

        if (pOptions == null || pOptions.getWidth() == 0 || pOptions.getHeight() == 0) {
            final DisplayOptions options;

            if (pOptions == null) {
                options = new DisplayOptions(0);
            } else {
                options = pOptions;
            }

            pView.getRootView().post(new Runnable() {

                @Override
                public void run() {
                    options.setWidth(pView.getMeasuredWidth());
                    options.setHeight(pView.getMeasuredHeight());
                    execute(pUrl, pView, options);
                }
            });
        } else {
            execute(pUrl, pView, pOptions);
        }
    }

    private void execute(final String pUrl, final ImageView pView, @NonNull final DisplayOptions pOptions) {
        final WeakReference<ImageView> imageViewReference = new WeakReference<>(pView);
        setPlaceholder(pView, pOptions.getPlaceholderId());
        pView.setTag(pUrl);

        mExecutor.execute(new Runnable() {

            @Override
            public void run() {
                if (imageViewReference.get() == null || !pUrl.equals(imageViewReference.get().getTag())) {
                    return;
                }

                mSizeParamsCallback = initSizeParamsCallback(pOptions.getWidth(), pOptions.getHeight());

                IHttpClient.Impl.get().doRequest(pUrl, new IHttpClient.IOnResult<Bitmap>() {

                            @Override
                            public void onSuccess(Bitmap pBitmap) {
                                final Bitmap bitmap = centerCropBitmap(pBitmap, pOptions.getWidth(), pOptions.getHeight());
                                pBitmap = null;

                                mLock.lock();
                                mLruCache.addBitmapToCache(pUrl, bitmap);
                                mLock.unlock();

                                mHandler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        final ImageView imageView = imageViewReference.get();
                                        if (imageView != null) {
                                            final String tag = (String) imageView.getTag();

                                            if (tag != null && tag.equals(pUrl)) {
                                                imageView.setImageBitmap(bitmap);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onError(final IOException e) {
                                mHandler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        Log.d(TAG, "Error! Image has not been loaded. URL: " + pUrl);
                                    }
                                });
                            }
                        },
                        mBitmapConverter);
            }

            private ISizeParams initSizeParamsCallback(final int pWidth, final int pHeight) {
                return new ISizeParams() {

                    @Override
                    public int getWidth() {
                        return pWidth;
                    }

                    @Override
                    public int getHeight() {
                        return pHeight;
                    }
                };
            }
        });
    }

    private int calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private Bitmap centerCropBitmap(final Bitmap pBitmap, final int pWidth, final int pHeight) {
        if (pWidth > 0 && pWidth < pBitmap.getWidth()
                || pHeight > 0 && pHeight < pBitmap.getHeight()) {
            final int x = (pBitmap.getWidth() - pWidth) / 2;
            final int y = (pBitmap.getHeight() - pHeight) / 2;

            return Bitmap.createBitmap(pBitmap, x > 0 ? x : 0, y > 0 ? y : 0,
                    pWidth < pBitmap.getWidth() ? pWidth : pBitmap.getWidth(),
                    pHeight < pBitmap.getHeight() ? pHeight : pBitmap.getHeight());
        } else {
            return pBitmap;
        }
    }

    private void setPlaceholder(final ImageView pView, final int pResId) {
        if (pResId > 0) {
            pView.setImageResource(pResId);
        } else {
            pView.setImageResource(android.R.color.transparent);
        }
    }
}