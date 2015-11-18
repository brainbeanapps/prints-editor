package com.brainbeanapps.rosty.printseditorlib.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditorlib.enums.ImageFilter;
import com.brainbeanapps.rosty.printseditorlib.factory.FilterFactory;

import java.lang.ref.WeakReference;

/**
 * Created by rosty on 7/15/2015.
 */
public class BitmapFilterTask extends AsyncTask<ImageFilter, Void, Bitmap> {

    private final WeakReference<OnTaskCompleteListener> listenerReference;
    private final WeakReference<ImageView> imageViewReference;
    private Bitmap bitmap;
    private Context context;

    public BitmapFilterTask(ImageView imageView, Bitmap bitmap, Context context) {
        this.imageViewReference = new WeakReference<>(imageView);
        this.bitmap = bitmap;
        this.context = context;
        listenerReference = null;
    }

    public BitmapFilterTask(Bitmap bitmap, Context context, OnTaskCompleteListener listener) {
        this.listenerReference = new WeakReference<>(listener);
        this.bitmap = bitmap;
        this.context = context;
        this.imageViewReference = null;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(ImageFilter... params) {
        return  FilterFactory.setImageFilter(bitmap, params[0], context);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (listenerReference != null && bitmap != null){
            OnTaskCompleteListener<Bitmap> listener = listenerReference.get();
            if (listener != null){
                listener.onTaskComplete(bitmap);
            }

        }else if(imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}