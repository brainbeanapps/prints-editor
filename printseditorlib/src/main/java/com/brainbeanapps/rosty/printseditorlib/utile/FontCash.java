package com.brainbeanapps.rosty.printseditorlib.utile;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * The class that must be used for storing text fonts in cache
 */

public class FontCash {

    public static final String FONTS_FOLDER = "fonts/";

    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), FONTS_FOLDER + name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}