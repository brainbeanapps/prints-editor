package com.brainbeanapps.rosty.printseditorlib.model;

/**
 * This class is a background spaces values holder. This values is used for templates
 */

public class BackgroundDataSet {

    public static final int SPACE = 1;
    public static final int SPACE_TOP = 0;
    public static final int SPACE_RIGHT = 0;

    private float editableSpaceHeight;
    private float editableSpaceWidth;
    private float editableTopSpace;
    private float editableRightSpace;

    public BackgroundDataSet() {
        this.editableSpaceHeight = BackgroundDataSet.SPACE;
        this.editableSpaceWidth = BackgroundDataSet.SPACE;
        this.editableTopSpace = BackgroundDataSet.SPACE_TOP;
        this.editableRightSpace = BackgroundDataSet.SPACE_RIGHT;
    }

    public BackgroundDataSet(float editableSpaceHeight, float editableSpaceWidth, float editableTopSpace, float editableRightSpace) {
        this.editableSpaceHeight = editableSpaceHeight;
        this.editableSpaceWidth = editableSpaceWidth;
        this.editableTopSpace = editableTopSpace;
        this.editableRightSpace = editableRightSpace;
    }

    public float getEditableSpaceHeight() {
        return editableSpaceHeight;
    }

    public void setEditableSpaceHeight(float editableSpaceHeight) {
        this.editableSpaceHeight = editableSpaceHeight;
    }

    public float getEditableTopSpace() {
        return editableTopSpace;
    }

    public void setEditableTopSpace(float editableTopSpace) {
        this.editableTopSpace = editableTopSpace;
    }

    public float getEditableRightSpace() {
        return editableRightSpace;
    }

    public void setEditableRightSpace(float editableRightSpace) {
        this.editableRightSpace = editableRightSpace;
    }

    public float getEditableSpaceWidth() {
        return editableSpaceWidth;
    }

    public void setEditableSpaceWidth(float editableSpaceWidth) {
        this.editableSpaceWidth = editableSpaceWidth;
    }
}
