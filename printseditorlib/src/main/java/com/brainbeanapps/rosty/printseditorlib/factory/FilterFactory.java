package com.brainbeanapps.rosty.printseditorlib.factory;

import android.content.Context;
import android.graphics.Bitmap;

import com.brainbeanapps.rosty.printseditorlib.enums.ImageFilter;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;


/**
 * This class is filter factory that helps to apply right filter on Bitmap
 * It is use a {@link GPUImage)
 */

public class FilterFactory {

    public static Bitmap setImageFilter(Bitmap bitmap, ImageFilter filter, Context context, float scale){

        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);

        switch (filter){

            case NORMAL:
                return bitmap;

            case SEPIA:
                gpuImage.setFilter(new GPUImageSepiaFilter());
                break;

            case GRAY:
                gpuImage.setFilter(new GPUImageGrayscaleFilter());
                break;

            case PIXEL:
                GPUImagePixelationFilter pixFilter = new GPUImagePixelationFilter();
                pixFilter.setPixel(7f * scale);
                gpuImage.setFilter(pixFilter);
                break;

            case BLUR:
                gpuImage.setFilter(new GPUImageBoxBlurFilter(1.3f * scale));
                break;

            case SKETCH:
                gpuImage.setFilter(new GPUImageSketchFilter());
                break;
        }

        return gpuImage.getBitmapWithFilterApplied();
    }

    public static Bitmap setImageFilter(Bitmap bitmap, ImageFilter filter, Context context){
        return setImageFilter(bitmap, filter, context, 1);
    }
}
