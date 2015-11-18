package com.brainbeanapps.rosty.printseditorlib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditorlib.R;
import com.brainbeanapps.rosty.printseditorlib.async.DrawableToBitmapTask;
import com.brainbeanapps.rosty.printseditorlib.async.ImgFileToBitmapTask;
import com.brainbeanapps.rosty.printseditorlib.async.OnTaskCompleteListener;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorState;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;
import com.brainbeanapps.rosty.printseditorlib.enums.ImageFilter;
import com.brainbeanapps.rosty.printseditorlib.factory.FilterFactory;
import com.brainbeanapps.rosty.printseditorlib.listener.EditorMeasuredListener;
import com.brainbeanapps.rosty.printseditorlib.listener.EditorStateListener;
import com.brainbeanapps.rosty.printseditorlib.model.FilterDataSet;
import com.brainbeanapps.rosty.printseditorlib.model.ImageDataSet;
import com.brainbeanapps.rosty.printseditorlib.model.MatrixValues;
import com.brainbeanapps.rosty.printseditorlib.model.PrintDataSet;
import com.brainbeanapps.rosty.printseditorlib.model.TextDataSet;
import com.brainbeanapps.rosty.printseditorlib.utile.BrCnUpdater;
import com.brainbeanapps.rosty.printseditorlib.utile.FontCash;
import com.brainbeanapps.rosty.printseditorlib.utile.Helper;
import com.christophesmet.android.views.maskableframelayout.MaskableFrameLayout;
import com.larvalabs.svgandroid.SVG;

import java.io.File;

/**
 * The view which contains methods for editing images and text
 * Can be used in combination with {@link PrintBackgroundView)
 */

public class PrintEditorView extends FrameLayout implements
        EditorMeasuredListener,
        OnTaskCompleteListener<Bitmap>{

    public static final String TAG = PrintEditorView.class.getSimpleName();

    private ImageView editImage;
    private EditText editText;

    private MaskableFrameLayout maskableFrameLayout;        //Used for creating templates

    private boolean isMeasured;
    private boolean isSVG;                                  //It may be a SVG or a raster image from drawable/file/bitmap/url
    private boolean isSquare;                               //It may be a square or a rectangle

    private int height;
    private int width;

    private Bitmap currentImage;                            //Used for applying filters
    private Bitmap originImage;                             //Used to reset the filter

    private ImageDataSet imageDataSet;                      //Data are generated for storage
    private TextDataSet textDataSet;                        //in the database and sending
    private FilterDataSet filterDataSet;                    //on the server

    private BrCnUpdater brCnUpdater;                        //Tool that help to apply brightness and contrast filters

    private ImageMultiTouchListener multiTouchListener;     //Used to manipulate the image
    private EditorStateListener editorStateListener;        //It helps setup view under the current state

    //===========================================================================================//

    public PrintEditorView(Context context) {
        super(context);

        setupView(context);
    }

    public PrintEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView(context, attrs);
    }

    public PrintEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setupView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PrintEditorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setupView(context, attrs);
    }

    //===========================================================================================//

    private void setupView(Context context, AttributeSet attrs){

        setupView(context);
        setupAttributes(context, attrs);
    }

    private void setupView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_print_editor, this, true);

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        setLayoutParams(layoutParams);

        editImage = (ImageView) findViewById(R.id.edit_image);
        editText = (EditText) findViewById(R.id.edit_text);
        maskableFrameLayout = (MaskableFrameLayout) findViewById(R.id.frm_mask);

        brCnUpdater = new BrCnUpdater();

        textDataSet = new TextDataSet();
        filterDataSet = new FilterDataSet();
        imageDataSet = new ImageDataSet();

        multiTouchListener = new ImageMultiTouchListener(editImage);
    }

    private void setupAttributes(Context context, AttributeSet attrs){

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PrintEditorView);

        for(int i = 0; i < a.getIndexCount(); i++){

            int styleableId = a.getIndex(i);

            if (styleableId == R.styleable.PrintEditorView_editor_image){
                Drawable drawable = a.getDrawable(styleableId);

                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                originImage = bitmap;
                currentImage = bitmap;

                editImage.setImageBitmap(currentImage);
            }

            if (styleableId == R.styleable.PrintEditorView_editor_text){
                String text = a.getString(styleableId);
                editText.setText(text);
            }
        }
        a.recycle();
    }

    private void setupImage(int size, Bitmap image){
        if (isMeasured) {

            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            float scale = (float) (size) / (imageWidth) * 2f;
            imageDataSet.setScale(scale);

            image = Helper.getResizedBitmap(image,
                    imageWidth * scale,
                    imageHeight * scale);

            brCnUpdater.reset();
            currentImage = image;
            originImage = image;

            editImage.setImageBitmap(currentImage);
            multiTouchListener.scaleFitWidth(0.5f);
        }
    }

    private void setupMask(boolean isSquare){
        if (isMeasured && isSquare){
            int size = width > height ? height : width;

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(size, size, Gravity.TOP);
            maskableFrameLayout.setLayoutParams(lp);
        }else if (!isSquare){
            //TODO: check
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.TOP);
//            maskableFrameLayout.setLayoutParams(lp);
        }
    }

    @Override
    public void onMeasured(int height, int width) {
        this.height = height;
        this.width = width;

        isMeasured = true;
        setupMask(isSquare);

        if (originImage != null) {
            setupImage(width, originImage);
        }
    }

    //===========================================================================================//

    /**
     * @return ImageMultiTouchListener - listener that used for editable image transformation
     */
    public ImageMultiTouchListener getMultiTouchListener() {
        return multiTouchListener;
    }

    /**
     * Methods that is used to set EditorStateListener
     *
     * @param editorStateListener - listener that used for editable image transformation
     */
    public void setEditorStateListener(EditorStateListener editorStateListener) {
        this.editorStateListener = editorStateListener;
    }

    //===========================================================================================//

    /**
     * Changing PrintEditor state and setup View for it
     *
     * @param state - text/image/svg
     */
    public void setEditorState(EditorState state){

        if (editorStateListener != null){
            editorStateListener.onStateChange(state);
        }else {
            this.setOnTouchListener(
                    state == EditorState.EDIT_TEXT
                            ? null
                            : multiTouchListener);
        }

        switch (state){

            case EDIT_IMAGE:
            case EDIT_SVG:
                editText.setEnabled(false);
                editText.setClickable(false);

                Helper.hideKeyboard(getContext(), this);
                break;

            case EDIT_TEXT:
                editText.setEnabled(true);
                editText.setClickable(true);
                break;
        }
    }

    /**
     * Setup image mask
     *
     * @param template - editor template
     */
    public void setTemplate(EditorTemplate template){
        filterDataSet.setTemplate(template.getId());
        isSquare = template.isSquare();

        setupMask(isSquare);

        if (template.getMask() != 0) {
            maskableFrameLayout.setMask(template.getMask());
        }
    }

    //===========================================================================================//

    /**
     * Changing editable image to raster  from Image File
     * Enabled raster image filters
     *
     * @param file - image file
     */
    public void setEditImage(File file) {
        imageDataSet.setOriginImagePath(file.getAbsolutePath());

        new ImgFileToBitmapTask(this).execute(file.getAbsolutePath());
    }

    /**
     * Changing editable image to raster  from Image from server
     * Enabled raster image filters
     *
     * @param url - image url
     */
    public void setEditImage(String url) {
        //TODO: set image from server
    }

    /**
     * Changing editable image to raster from Bitmap
     * Enabled raster image filters
     *
     * @param image- bitmap image
     * @param id - this param associated with the id on the server/
     *           if its a user image must be -1
     */
    public void setEditImage(Bitmap image, int id){
        imageDataSet.setOriginImageId(id);
        setEditImage(image);
    }

    /**
     * Changing editable image to raster from rasDrawable resource
     * Enabled raster image filters
     *
     * @param drawableId - drawable resource id
     * @param id - this param associated with the id on the server/
     *           if its a user image must be -1
     */
    public void setEditImage(int drawableId, int id){
        imageDataSet.setOriginImageId(id);

        new DrawableToBitmapTask(this, getContext()).execute(drawableId);
    }

    private void setEditImage(Bitmap image){
        originImage = image;
        isSVG = false;
        setupImage(width, originImage);
    }

    @Override
    public void onTaskComplete(Bitmap image) {
        setEditImage(image);
    }

    /**
     * Changing editable image to svg and saved its color in data
     * must me used for not default colored svg
     * Enabled svg image filters
     *
     * @param svg - svg image
     * @param color - fill color
     * @param id - this param associated with the id on the server/
     *           if its a user image must be -1
     */
    public void setSVGColored(SVG svg, int color, int id){
        setSVGEditImage(svg, id);
        filterDataSet.setSvgColor(color);
    }

    /**
     * Changing editable image to svg
     * Enabled svg image filters
     *
     * @param svg - svg image
     * @param id - this param associated with the id on the server/
     *           if its a user image must be -1
     */
    public void setSVGEditImage(SVG svg, int id) {
        imageDataSet.setOriginImageId(id);
        int size = editImage.getWidth() * 4;

        Picture image = svg.getPicture();
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(image, new Rect(0, 0, size, size));

        isSVG = true;
        currentImage = bitmap;
        originImage = bitmap;

        brCnUpdater.reset();
        editImage.setImageBitmap(currentImage);
    }

    /**
     * Changing print label text
     *
     * @param text - new print label text
     */
    public void setEditText(String text) {
        editText.setText(text);
        textDataSet.setText(text);
    }

    //===========================================================================================//

    /**
     * Apply contrast filter on editable image
     *
     * @param value - contrast value
     */
    public void setImageContrast(float value) {
        if (currentImage != null) {
            Bitmap image = brCnUpdater.changeBitmapContrast(value, currentImage);
            editImage.setImageBitmap(image);
            filterDataSet.setContrast(value);
        }
    }

    /**
     * Apply brightness filter on editable image
     *
     * @param value - brightness value
     */
    public void setImageBrightness(float value) {
        if (currentImage != null) {
            Bitmap image = brCnUpdater.changeBitmapBrightness(value, currentImage);
            editImage.setImageBitmap(image);
            filterDataSet.setBrightness(value);
        }
    }

    /**
     * Apply texture filter on editable image
     *
     * @param filter - new texture filter
     */
    public void setImageFilter(ImageFilter filter) {
        if (!isSVG) {
            currentImage = FilterFactory.setImageFilter(originImage, filter, getContext());
            editImage.setImageBitmap(brCnUpdater.setCurrentBrCn(currentImage));
            filterDataSet.setFilter(filter.getId());
        }
    }

    /**
     * Returns editable image to original state
     */
    public void resetImage() {
        if (currentImage != null) {
            multiTouchListener.resetMatrix();
            currentImage = originImage;

            brCnUpdater.reset();
            editImage.setImageBitmap(currentImage);
        }
    }

    //===========================================================================================//

    /**
     * Changing print label color
     *
     * @param color - new color from  resources
     */
    public void setTextColor(int color) {
        editText.setTextColor(color);
        textDataSet.setTextColor(color);
    }

    /**
     * Changing print label size
     *
     * @param size - new size in px
     */
    public void setTextSize(float size) {
        editText.setTextSize(size);
        textDataSet.setTextSize(size);
    }

    /**
     * Modify print label size
     *
     * @param updateOn - this size will be added to the current label size
     */
    public void updateTextSize(int updateOn) {
        float currentSize = Helper.convertPixelsToDp(editText.getTextSize(), getContext());
        if (currentSize + updateOn >= 1) {
            float newSize = currentSize + updateOn;
            editText.setTextSize(newSize);
            textDataSet.setTextSize(newSize);
        }
    }

    /**
     * Changing print label text font
     *
     * @param font - new text font
     */
    public void setTextFont(String font) {
        if (font != null) {
            Typeface myFont = FontCash.get(font, getContext());
            editText.setTypeface(myFont);
            textDataSet.setTextFont(font);
        }
    }

    /**
     * Changing test gravity
     *
     * @param gravity - view gravity param
     */
    public void setTextGravity(int gravity) {
        editText.setGravity(gravity);
        textDataSet.setTextGravity(gravity);
    }

    //===========================================================================================//

    /**
     * Exporting print result as Bitmap
     *
     * @return Bitmap - edited image with background
     */
    public Bitmap getResultImage() {
        setEditorState(EditorState.EDIT_IMAGE);

        Bitmap result = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        draw(canvas);

        return result;
    }

    /**
     * @return Bitmap - original image
     */
    public Bitmap getOriginImage(){
        return originImage;
    }

    /**
     * Exporting print result as DataSet for server
     *
     * @return PrintDataSet - data that needed to recreate print in good quality
     */
    public PrintDataSet getExportResult(){
        MatrixValues values = multiTouchListener.getMatrixValues();
        filterDataSet.setMatrixValues(values);
        filterDataSet = brCnUpdater.getResult(filterDataSet);

        textDataSet.setText(editText.getText().toString());

        return new PrintDataSet(imageDataSet, textDataSet, filterDataSet);
    }
}
