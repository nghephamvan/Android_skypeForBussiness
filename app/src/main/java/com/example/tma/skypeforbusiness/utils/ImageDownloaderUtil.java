package com.example.tma.skypeforbusiness.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.tma.skypeforbusiness.R;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tmvien on 2/24/17.
 */

public class ImageDownloaderUtil extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = ImageDownloaderUtil.class.getSimpleName();
    private final WeakReference<ImageView> imageViewReference;

    public ImageDownloaderUtil(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.mipmap.image_user_picture_default);
                    imageView.setImageDrawable(placeholder);
                }
            }
        }
    }

    private Bitmap downloadBitmap(String urlRequest) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(urlRequest);
            Log.i(TAG, "Request to: " + urlRequest.toString());
            urlConnection = (HttpURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            String authValue = "Bearer " + InternalDataUtil.getInstance().getToken();
            urlConnection.setRequestProperty("Authorization", authValue);
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w(TAG, "Error downloading image from " + urlRequest);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
