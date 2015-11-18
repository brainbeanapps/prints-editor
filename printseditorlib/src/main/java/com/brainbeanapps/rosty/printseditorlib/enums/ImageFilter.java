package com.brainbeanapps.rosty.printseditorlib.enums;

import com.brainbeanapps.rosty.printseditorlib.widget.PrintEditorView;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum of image filters.
 * Is used in {@link PrintEditorView )
 */

public enum  ImageFilter {
    NORMAL(0),
    SEPIA(1),
    GRAY(2),
    PIXEL(3),
    SKETCH(4),
    BLUR(5);

    private int id;

    private static final Map<Integer, ImageFilter> lookupFilter = new HashMap<>();

    static {
        for (ImageFilter f : ImageFilter.values()) {
            lookupFilter .put(f.getId(), f);
        }
    }

    ImageFilter(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ImageFilter  get(Integer id){
        return lookupFilter.get(id);
    }
}
