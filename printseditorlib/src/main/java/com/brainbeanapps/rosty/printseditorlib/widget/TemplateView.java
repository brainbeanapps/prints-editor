package com.brainbeanapps.rosty.printseditorlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditorlib.R;
import com.brainbeanapps.rosty.printseditorlib.async.DrawableToBitmapTask;
import com.christophesmet.android.views.maskableframelayout.MaskableFrameLayout;

/**
 * The simple view to display templates
 * Can be used in combination with {@link PrintBackgroundView)
 */

public class TemplateView extends MaskableFrameLayout {

    private ImageView templateImage;

    public TemplateView(Context context) {
        super(context);

        setupView(context);
    }

    public TemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView(context);
    }

    public TemplateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setupView(context);
    }

    private void setupView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_print_template, this, true);

        templateImage = (ImageView) findViewById(R.id.template_image);
    }

    public void setTemplateImage(int image){
        new DrawableToBitmapTask(templateImage, getContext()).execute(image);
    }
}
