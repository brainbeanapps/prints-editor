package com.brainbeanapps.rosty.printseditorlib.enums;

import com.brainbeanapps.rosty.printseditorlib.R;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintEditorView;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintBackgroundView;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum of print editor templates.
 * Is used for setting spaces in  {@link PrintBackgroundView) and masks in {@link PrintEditorView )
 */

public enum EditorTemplate {
    SIMPLE_TEMPLATE(0, 0, false),

    STAR_CAT(1, R.drawable.mask_cat, false),
    STAR_GRID(2, R.drawable.mask_grid, false),
    STAR_DREAM(3, R.drawable.mask_dream, false),
    OVAL_TEMPLATE(4, R.drawable.mask_oval, true),
    STAR_TEMPLATE(5, R.drawable.mask_star, true),
    STAR_DOTS(6, R.drawable.mask_dots, true),
    STAR_LIGHTING(7, R.drawable.mask_lighting, false),
    STAR_MOUNTAIN(8, R.drawable.mask_mountain, false),
    STAR_BETWEEN(9, R.drawable.mask_between, false),
    STAR_HUMAN(10, R.drawable.mask_human, false);

    private int id;
    private int mask;
    private boolean isSquare;

    private static final Map<Integer, EditorTemplate> lookupTemplate = new HashMap<>();

    static {
        for (EditorTemplate t : EditorTemplate.values()) {
            lookupTemplate .put(t.getId(), t);
        }
    }

    EditorTemplate(int id, int mask, boolean isSquare) {
        this.id = id;
        this.mask = mask;
        this.isSquare = isSquare;
    }

    public int getId() {
        return id;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public int getMask() {
        return mask;
    }

    public static EditorTemplate  get(Integer id){
        return lookupTemplate.get(id);
    }
}
