package com.brainbeanapps.rosty.printseditorlib.model;

import android.view.Gravity;

import com.brainbeanapps.rosty.printseditorlib.R;

/**
 * This class is a text values holder. This values is used to recreate user print
 * Is a part of {@link PrintDataSet)
 */

public class TextDataSet {

    private static final int DEFAULT_TEXT_SIZE = R.dimen.text_title;
    private static final int DEFAULT_TEXT_COLOR = R.color.text_main;
    private static final int DEFAULT_TEXT_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private String text;
    private String textFont;

    private float textSize;
    private int textColor;
    private int textGravity;

    public TextDataSet() {
        this.textSize = DEFAULT_TEXT_SIZE;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.textGravity = DEFAULT_TEXT_GRAVITY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextFont() {
        return textFont;
    }

    public void setTextFont(String textFont) {
        this.textFont = textFont;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextGravity() {
        return textGravity;
    }

    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
    }
}
