package com.brainbeanapps.rosty.printseditorlib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditorlib.R;
import com.brainbeanapps.rosty.printseditorlib.enums.BackgroundState;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorState;
import com.brainbeanapps.rosty.printseditorlib.exaption.EditorException;
import com.brainbeanapps.rosty.printseditorlib.listener.EditorMeasuredListener;
import com.brainbeanapps.rosty.printseditorlib.listener.EditorStateListener;
import com.brainbeanapps.rosty.printseditorlib.model.BackgroundDataSet;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * The view which contains a print background prints
 * With it you can create different templates {@link EditorTemplate)
 * May contain 3 types of children's {@link PrintEditorView){@link TemplateView){@link ImageView)
 */

public class PrintBackgroundView extends FrameLayout implements
        EditorStateListener {

    public static final String TAG = PrintBackgroundView.class.getSimpleName();

    private FrameLayout childContainer;                             //Container for PrintEditorView/TemplateView/ImageView
    private View imageEditorTouchView;                              //Touchable area for manipulating with editable image from PrintEditorView
    private ImageView backgroundImageView;                          //View for prints background

    private String childViewTag;                                    //Tag with the help of which we find PrintEditorView
    private boolean isMeasured;

    private BackgroundState backgroundState;                        //It helps setup view under the current state
    private BackgroundDataSet dataSet;                              //Used for exporting result

    private ImageMultiTouchListener multiTouchListener;
    private List<EditorMeasuredListener> editorMeasuredListeners;   //Return user measure information

    //===========================================================================================//

    public PrintBackgroundView(Context context) {
        super(context);

        setupView(context);
    }

    public PrintBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView(context, attrs);
    }

    public PrintBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setupView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PrintBackgroundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setupView(context, attrs);
    }

    //===========================================================================================//

    private void setupView(Context context, AttributeSet attrs){

        setupView(context);
        setupAttributes(context, attrs);
    }

    private void setupView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_print_background, this, true);

        childContainer = (FrameLayout) findViewById(R.id.editor_container);
        imageEditorTouchView = findViewById(R.id.image_editor_touch);
        backgroundImageView = (ImageView) findViewById(R.id.background_image);

        getViewTreeObserver().addOnGlobalLayoutListener(sizObserver);
        backgroundState = BackgroundState.NO_CHILD;
    }

    private void setupAttributes(Context context, AttributeSet attrs){

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PrintBackgroundView);
        dataSet = new BackgroundDataSet();

        for(int i = 0; i < a.getIndexCount(); i++){

            int styleableId = a.getIndex(i);

            if(styleableId == R.styleable.PrintBackgroundView_editor_bg){
                Drawable drawable = a.getDrawable(styleableId);
                backgroundImageView.setImageDrawable(drawable);
            }

            if(styleableId == R.styleable.PrintBackgroundView_editor_child_tag){
                childViewTag = a.getString(styleableId);
            }

            if(styleableId == R.styleable.PrintBackgroundView_editor_space) {
                float space = a.getFloat(styleableId, BackgroundDataSet.SPACE);
                dataSet.setEditableSpaceHeight(space);
                dataSet.setEditableSpaceWidth(space);
            }

            if (styleableId == R.styleable.PrintBackgroundView_editor_space_top){
                float spaceTop = a.getFloat(styleableId, BackgroundDataSet.SPACE_TOP);
                dataSet.setEditableTopSpace(spaceTop);
            }

            if (styleableId == R.styleable.PrintBackgroundView_editor_space_right){
                float spaceRight = a.getFloat(styleableId, BackgroundDataSet.SPACE_RIGHT);
                dataSet.setEditableRightSpace(spaceRight);
            }
        }

        a.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (childViewTag != null) {
            View childView = getRootView().findViewWithTag(childViewTag);

            if (childView != null) {

                removeView(childView);

                if (childView instanceof ImageView) {
                    addPreviewView((ImageView) childView);
                }

                if (childView instanceof PrintEditorView) {
                    addEditorView((PrintEditorView) childView);
                }

                if (childView instanceof TemplateView) {
                    addTemplateView((TemplateView) childView);
                }
            }
        }
    }

    private void setupEditorContainer(BackgroundDataSet dataSet, int viewHeight, int viewWidth){
        if(isMeasured){
            int height = (int)(viewHeight * dataSet.getEditableSpaceHeight());
            int width = (int)(viewWidth * dataSet.getEditableSpaceWidth());

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height, Gravity.RIGHT);

            layoutParams.topMargin = (int) (viewHeight * dataSet.getEditableTopSpace());
            layoutParams.rightMargin = (int) (viewWidth * dataSet.getEditableRightSpace());

            childContainer.setLayoutParams(layoutParams);
            notifyMeasuredListeners(height, width);
        }
    }

    @Override
    public void onStateChange(EditorState state) {
        enableImageEditor(state != EditorState.EDIT_TEXT);
    }

    //===========================================================================================//

    /**
     * If mode is Preview returns child ImageView
     *
     * @return ImageView - view that is used for print preview image
     */
    public ImageView getPreviewView() throws EditorException {
        if (checkException(BackgroundState.PREVIEW)){
            return ((ImageView) childContainer.getChildAt(0));
        }else{
            throw new EditorException("The view not in preview state");
        }
    }

    /**
     * If mode is Editor returns child PrintEditorView
     *
     * @return PrintEditorView - view that is used for edit prints
     */
    public PrintEditorView getEditorView() throws EditorException {
        if (checkException(BackgroundState.EDITOR)){
            return  ((PrintEditorView) childContainer.getChildAt(0));
        }else{
            throw new EditorException("The view not in editor state");
        }
    }

    /**
     * If mode is Template returns child TemplateView
     *
     * @return TemplateView - view that is used for displaying templates
     */
    public TemplateView getTemplateView() throws EditorException {
        if (checkException(BackgroundState.TEMPLATE)){
            return  ((TemplateView) childContainer.getChildAt(0));
        }else{
            throw new EditorException("The view not in template state");
        }
    }

    /**
     * Setup height and width of child view
     *
     * @param height - child view height in px
     * @param width - child view width in px
     */
    public void setChildContainerSize(int height, int width){
        notifyMeasuredListeners(height, width);
    }

    /**
     * Sets child view position from saved data
     *
     * @param dataSet - holder of space values for background
     */
    public void setDataSet(BackgroundDataSet dataSet) {
        this.dataSet = dataSet;
        setupEditorContainer(dataSet, getHeight(), getWidth());
    }

    /**
     * Methods that is used to set Print background image from Drawable
     *
     * @param backgroundImage - image that will be displayed as print background
     */
    public void setBackgroundImage(Drawable backgroundImage){
        backgroundImageView.setImageDrawable(backgroundImage);
    }

    /**
     * Sets Print background image from Bitmap
     *
     * @param backgroundImage - image that will be displayed as print background
     */
    public void setBackgroundImage(Bitmap backgroundImage){
        backgroundImageView.setImageBitmap(backgroundImage);
    }

    /**
     * Removes current child view and adding PrintEditorView as a new child,
     * view state changing to EDITOR
     *
     * @param editorView - new child for view
     */
    public void addEditorView(PrintEditorView editorView){
        addChildView(editorView);

        multiTouchListener = editorView.getMultiTouchListener();
        editorView.setEditorStateListener(this);
        addEditorMeasuredListener(editorView);

        backgroundState = BackgroundState.EDITOR;
    }

    /**
     * Removes current child view and adding TemplateView as a new child,
     * view state changing to TEMPLATE
     *
     * @param templateView - new child for view
     */
    public void addTemplateView(TemplateView templateView){
        addChildView(templateView);

        backgroundState = BackgroundState.TEMPLATE;
        enableImageEditor(false);
    }

    /**
     * Removes current child view and adding ImageView as a new child,
     * view state changing to PREVIEW
     *
     * @param previewView - new child for view
     */
    public void addPreviewView(ImageView previewView){
        addChildView(previewView);

        backgroundState = BackgroundState.PREVIEW;
        enableImageEditor(false);
    }

    /**
     * Is used in PREVIEW state to set preview image from Drawable
     *
     * @param previewImage - image that will be displayed as print preview
     */
    public void setPreviewImage(Drawable previewImage)throws EditorException{
        if (checkException(BackgroundState.PREVIEW)){
            ((ImageView) childContainer.getChildAt(0)).setImageDrawable(previewImage);
        }else{
            throw new EditorException("The view not in preview mode");
        }
    }

    /**
     * Is used in PREVIEW state to set preview image from Bitmap
     *
     * @param previewImage - image that will be displayed as print preview
     */
    public void setPreviewImage(Bitmap previewImage)throws EditorException{
        if (checkException(BackgroundState.PREVIEW)){
            ((ImageView) childContainer.getChildAt(0)).setImageBitmap(previewImage);
        }else{
            throw new EditorException("The view not in preview mode");
        }
    }

    /**
     * Adding a new EditorMeasuredListener
     *
     * @param measuredListener - listener that will be notify user on viewMeasured(height, width)
     */
    public void addEditorMeasuredListener(EditorMeasuredListener measuredListener){
        if(editorMeasuredListeners == null){
            editorMeasuredListeners = new ArrayList<>();
        }

        editorMeasuredListeners.add(measuredListener);
    }

    /**
     * Removing EditorMeasuredListener if it was added before
     *
     * @param measuredListener - listener that will be removed
     */
    public void removeEditorMeasuredListener(EditorMeasuredListener measuredListener){
        if(editorMeasuredListeners != null) {

            if (editorMeasuredListeners.contains(measuredListener)) {
                editorMeasuredListeners.remove(measuredListener);
            }
        }
    }

    //===========================================================================================//

    private void addChildView(View view){
        childContainer.removeAllViewsInLayout();
        childContainer.addView(view);
    }

    private boolean checkException(BackgroundState state){
        if (backgroundState == state) {
            return true;
        }else {
            return false;
        }
    }

    private void enableImageEditor(boolean enable){
        imageEditorTouchView.setVisibility(enable ? VISIBLE : GONE);
        imageEditorTouchView.setOnTouchListener(enable ? multiTouchListener : null);
    }

    private void notifyMeasuredListeners(int height, int width){
        if(editorMeasuredListeners != null) {

            for (EditorMeasuredListener l: editorMeasuredListeners){
                l.onMeasured(height, width);
            }
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener sizObserver = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);

            isMeasured = true;
            setupEditorContainer(dataSet, getHeight(), getWidth());
        }
    };
}
