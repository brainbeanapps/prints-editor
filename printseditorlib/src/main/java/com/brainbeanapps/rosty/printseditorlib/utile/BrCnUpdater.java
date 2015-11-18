package com.brainbeanapps.rosty.printseditorlib.utile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.brainbeanapps.rosty.printseditorlib.model.FilterDataSet;

/**
 * The helper class that apply brightness and contrast filters on image
 * and saving this params
 */

public class BrCnUpdater {

    private float brightness;
    private float contrast;

    public BrCnUpdater() {
        this.brightness = FilterDataSet.DEFAULT_BRIGHTNESS;
        this.contrast = FilterDataSet.DEFAULT_CONTRAST;
    }

    public Bitmap changeBitmapContrast(float contrast, Bitmap image){
        this.contrast = contrast;

        return setCurrentBrCn(image);
    }

    public Bitmap changeBitmapBrightness(float brightness, Bitmap image){
        this.brightness = brightness;

        return setCurrentBrCn(image);
    }

    public FilterDataSet getResult(FilterDataSet dataSet){
        dataSet.setBrightness(brightness);
        dataSet.setContrast(contrast);
        return dataSet;
    }

    public void reset(){
        this.brightness = FilterDataSet.DEFAULT_BRIGHTNESS;
        this.contrast = FilterDataSet.DEFAULT_CONTRAST;
    }

    public Bitmap setCurrentBrCn(Bitmap image){
        return changeBitmapContrastBrightness(image, contrast, brightness);
    }

    private Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {

        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
}
