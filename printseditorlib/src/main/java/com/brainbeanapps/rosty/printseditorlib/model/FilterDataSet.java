package com.brainbeanapps.rosty.printseditorlib.model;

import com.google.gson.Gson;

/**
 * This class is a filter values holder. This values is used to recreate user print
 * Is a part of {@link PrintDataSet)
 */

public class FilterDataSet {

    public static final float DEFAULT_BRIGHTNESS = 0;
    public static final float DEFAULT_CONTRAST = 1;
    public static final int DEFAULT_SVG_COLOR = 0;

    private int svgColor;
    private float contrast;
    private float brightness;

    private int template;
    private int filter;

    private MatrixValues matrixValues;


    public FilterDataSet() {
        this.brightness = DEFAULT_BRIGHTNESS;
        this.contrast = DEFAULT_CONTRAST;
        this.svgColor = DEFAULT_SVG_COLOR;
    }

    public int getSvgColor() {
        return svgColor;
    }

    public void setSvgColor(int svgColor) {
        this.svgColor = svgColor;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public MatrixValues getMatrixValues() {
        return matrixValues;
    }

    public String getMatrixValuesString() {
        return new Gson().toJson(matrixValues);
    }

    public void setMatrixValues(MatrixValues matrixValues) {
        this.matrixValues = matrixValues;
    }

    public void setMatrixValues(String matrixValues) {
        this.matrixValues = new Gson().fromJson(matrixValues, MatrixValues.class);
    }


}
