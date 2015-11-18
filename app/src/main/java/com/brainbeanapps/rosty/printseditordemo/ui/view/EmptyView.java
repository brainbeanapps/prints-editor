package com.brainbeanapps.rosty.printseditordemo.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/7/2015.
 */
public class EmptyView extends LinearLayout {

    private static final String TAG = EmptyView.class.getSimpleName();

    private ImageView emptyImage;
    private TextView emptyText;

    public EmptyView(Context context) {
        super(context);
        initViews(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupViews(context , attrs);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupViews(context, attrs);
    }

    private void initViews(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true);

        emptyImage = (ImageView) findViewById(R.id.empty_image);
        emptyText = (TextView) findViewById(R.id.empty_text);
    }

    private void setupViews(Context context, AttributeSet attrs){
        initViews(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyView, 0, 0);

        Drawable d = a.getDrawable(R.styleable.EmptyView_empty_image);
        if (d != null) emptyImage.setImageDrawable(d);

        String s = a.getString(R.styleable.EmptyView_empty_text);
        if (s != null) emptyText.setText(s);

        a.recycle();
    }

    public void setEmptyImage(Drawable drawable){
        emptyImage.setImageDrawable(drawable);
    }

    public void setEmptyText(String text){
        emptyText.setText(text);
    }
}
