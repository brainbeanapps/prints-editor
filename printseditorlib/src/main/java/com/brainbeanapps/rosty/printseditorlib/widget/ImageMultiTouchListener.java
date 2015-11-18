package com.brainbeanapps.rosty.printseditorlib.widget;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;
import com.brainbeanapps.rosty.printseditorlib.model.MatrixValues;

/**
 * The listener used for handle touch events and setup transformations
 * on specified image
 * Is used in {@link PrintBackgroundView)& {@link PrintEditorView)
 */

public class ImageMultiTouchListener implements View.OnTouchListener {

    public static final String TAG = ImageMultiTouchListener.class.getSimpleName();

    // these matrices will be used to move and zoom image
    private Matrix originMatrix = new Matrix();
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;

    // image that used for transformations
    private ImageView image;

    public ImageMultiTouchListener(ImageView image) {
        this.image = image;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //handle touch events here
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM && event.getPointerCount() == 2) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        matrix.postRotate(r,
                                image.getMeasuredWidth() / 2,
                                image.getMeasuredHeight() / 2);
                    }
                }
                break;
        }

        image.setImageMatrix(matrix);
        return true;
    }

    /**
     * Set matrix saved values to recreate matrix
     *
     * @param  values - simple values array holder
     */
    public void setMatrixValues(MatrixValues values){
        savedMatrix.set(matrix);
        matrix.setValues(values.getValues());
        image.setImageMatrix(matrix);
    }

    /**
     * Generates MatrixValues
     *
     * @return MatrixValues - container that holds values array
     */
    public MatrixValues getMatrixValues(){
        float[] values = new float[9];
        matrix.getValues(values);

        return new MatrixValues(values);
    }

    /**
     * Reset matrix to default state
     */
    public void  resetMatrix(){
        savedMatrix.set(originMatrix);
        matrix.set(originMatrix);
        image.setImageMatrix(originMatrix);
    }

    /**
     * Scale image matrix fit width
     *
     * @param scale - Scale ratio
     */
    public void scaleFitWidth(float scale){
        matrix = new Matrix();
        matrix.postScale(scale, scale);
        image.setImageMatrix(matrix);
        originMatrix.set(matrix);
    }

    /**
     * Determine the space between the first two fin
     *
     * @param event - On Touch Listener Event
     * @return Spacing
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     *
     * @param event - On Touch Listener Event
     * @param point - Simple two float coordinates holder
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event - On Touch Listener Event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}