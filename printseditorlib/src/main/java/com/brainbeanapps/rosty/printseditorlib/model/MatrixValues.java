package com.brainbeanapps.rosty.printseditorlib.model;

/**
 * This class is a matrix values holder. This values is used to recreate user print
 * Is a part of {@link FilterDataSet)
 */

public class MatrixValues {

    private float[] values;

    public MatrixValues(float[] values) {
        this.values = values;
    }

    public float[] getValues() {
        return values;
    }
}
