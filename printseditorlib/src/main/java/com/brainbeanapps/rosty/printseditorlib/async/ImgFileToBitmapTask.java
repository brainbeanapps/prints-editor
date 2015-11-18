package com.brainbeanapps.rosty.printseditorlib.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by rosty on 7/20/2015.
 */
public class ImgFileToBitmapTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewWeakReference;
    private final WeakReference<OnTaskCompleteListener> listenerReference;

    public ImgFileToBitmapTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<>(imageView);
        this.listenerReference = null;
    }

    public ImgFileToBitmapTask(OnTaskCompleteListener listener) {
        this.listenerReference = new WeakReference<>(listener);
        this.imageViewWeakReference = null;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        File image = new File(params[0]);
        return  BitmapFactory.decodeFile(image.getAbsolutePath());
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (listenerReference != null && bitmap != null){
            OnTaskCompleteListener<Bitmap> listener = listenerReference.get();
            if (listener != null){
                listener.onTaskComplete(bitmap);
            }

        }else if (imageViewWeakReference != null && bitmap != null) {
            ImageView view = imageViewWeakReference.get();
            if (view != null) {
                view.setImageBitmap(bitmap);
            }
        }
    }
}