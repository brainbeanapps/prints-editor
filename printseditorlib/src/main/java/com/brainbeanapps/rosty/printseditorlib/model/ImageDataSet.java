package com.brainbeanapps.rosty.printseditorlib.model;

/**
 * This class is a image values holder. This values is used to recreate user print
 * Is a part of {@link PrintDataSet)
 */

public class ImageDataSet {

    private int originImageId;
    private String originImageUrl;
    private String originImagePath;
    private String resultImagePath;

    private float scale;

    public int getOriginImageId() {
        return originImageId;
    }

    public void setOriginImageId(int originImageId) {
        this.originImageId = originImageId;
    }

    public String getOriginImageUrl() {
        return originImageUrl;
    }

    public void setOriginImageUrl(String originImageUrl) {
        this.originImageUrl = originImageUrl;
    }

    public String getOriginImagePath() {
        return originImagePath;
    }

    public void setOriginImagePath(String originImagePath) {
        this.originImagePath = originImagePath;
    }

    public String getResultImagePath() {
        return resultImagePath;
    }

    public void setResultImagePath(String resultImagePath) {
        this.resultImagePath = resultImagePath;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
