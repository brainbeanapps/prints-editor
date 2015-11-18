package com.brainbeanapps.rosty.printseditorlib.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by rosty on 7/20/2015.
 */
public class DrawableToBitmapTask extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<OnTaskCompleteListener> listenerReference;
    private final WeakReference<ImageView> imageViewReference;
    private Context context;

    public DrawableToBitmapTask(OnTaskCompleteListener listener, Context context) {
        this.listenerReference = new WeakReference<>(listener);
        this.context = context;
        this.imageViewReference = null;
    }

    public DrawableToBitmapTask(ImageView imageViewReference, Context context) {
        this.imageViewReference = new WeakReference<>(imageViewReference);
        this.context = context;
        this.listenerReference = null;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        return  BitmapFactory.decodeResource(context.getResources(), params[0]);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (listenerReference != null && bitmap != null) {
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

