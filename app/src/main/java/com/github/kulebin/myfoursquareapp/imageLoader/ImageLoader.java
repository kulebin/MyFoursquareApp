package com.github.kulebin.myfoursquareapp.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.github.kulebin.myfoursquareapp.http.IHttpClient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

class ImageLoader implements IImageLoader {

    interface ISizeParams {

        int getWidth();

        int getHeight();
    }

    private static final int CHUNK_SIZE_POWER = 16; //chunk size = 2^x

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
        draw(pUrl, pView, pView.getWidth(), pView.getHeight());
    }

    @Override
    public void draw(final String pUrl, final ImageView pView, final int pWidth, final int pHeight) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                mSizeParamsCallback = initSizeParamsCallback(pWidth, pHeight);

                IHttpClient.Impl.get().doRequest(pUrl, new IHttpClient.IOnResult<Bitmap>() {

                            @Override
                            public void onSuccess(final Bitmap pBitmap) {
                                final Bitmap bitmap = resizeBitmap(pBitmap, pWidth, pHeight);
                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        pView.setImageBitmap(bitmap);
                                    }
                                });
                            }

                            @Override
                            public void onError(final IOException e) {
                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO: 3/11/2017 handle error
                                    }
                                });
                            }
                        },
                        mBitmapConverter);
            }
        }).start();
    }

    private int calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
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

    private Bitmap resizeBitmap(final Bitmap pBitmap, final int pWidth, final int pHeight) {
        return Bitmap.createBitmap(pBitmap, 0, 0,
                pWidth < pBitmap.getWidth() ? pWidth : pBitmap.getWidth(),
                pHeight < pBitmap.getHeight() ? pHeight : pBitmap.getHeight());
    }
}


