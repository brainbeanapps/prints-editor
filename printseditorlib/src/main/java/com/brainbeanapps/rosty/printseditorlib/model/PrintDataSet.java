package com.brainbeanapps.rosty.printseditorlib.model;

/**
 * This class is a print values holder. This values is used to recreate user print
 */

public class PrintDataSet {

    private int id;

    private ImageDataSet imageDataSet;
    private TextDataSet textDataSet;
    private FilterDataSet filterDataSet;

    public PrintDataSet() {
    }

    public PrintDataSet(ImageDataSet imageDataSet, TextDataSet textDataSet, FilterDataSet filterDataSet) {
        this.imageDataSet = imageDataSet;
        this.textDataSet = textDataSet;
        this.filterDataSet = filterDataSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageDataSet getImageDataSet() {
        return imageDataSet;
    }

    public void setImageDataSet(ImageDataSet imageDataSet) {
        this.imageDataSet = imageDataSet;
    }

    public TextDataSet getTextDataSet() {
        return textDataSet;
    }

    public void setTextDataSet(TextDataSet textDataSet) {
        this.textDataSet = textDataSet;
    }

    public FilterDataSet getFilterDataSet() {
        return filterDataSet;
    }

    public void setFilterDataSet(FilterDataSet filterDataSet) {
        this.filterDataSet = filterDataSet;
    }
}
