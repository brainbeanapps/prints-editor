package com.brainbeanapps.rosty.printseditordemo.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.brainbeanapps.rosty.printseditordemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosty on 7/10/2015.
 */
public class ColorBar extends LinearLayout implements View.OnTouchListener{

    public interface OnColorsChangeListener{

        void onColorChanged(int color);
    }

    private List<Integer> colors;
    private int itemSize;

    private OnColorsChangeListener colorsChangeListener;

    public ColorBar(Context context) {
        super(context);
    }

    public ColorBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int position = (int) Math.floor(event.getX() / itemSize);

        if (position < colors.size() - 1 && position >= 0) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    colorsChangeListener.onColorChanged(colors.get(position));
                    break;

                case MotionEvent.ACTION_MOVE:
                    colorsChangeListener.onColorChanged(colors.get(position));
                    break;
            }
        }
        return false;
    }


    public void setupView(List<Integer> colors, OnColorsChangeListener colorsChangeListener) {
        this.colors = colors;
        this.colorsChangeListener = colorsChangeListener;

        setupColorViews();
        getItemSize();
        setOnTouchListener(this);
    }

    public void setupView(OnColorsChangeListener colorsChangeListener) {
        setupView(getColorList(), colorsChangeListener);
    }


    public void setupColorViews(){
        for (Integer i: colors){
            View view = new View(getContext());
            view.setBackgroundColor(i);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f);
            view.setLayoutParams(param);
            addView(view);
        }
    }

    public void getItemSize(){
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    itemSize = getWidth() / colors.size();
                }
            });
        }
    }

    private List<Integer> getColorList(){
        int[] colors = getResources().getIntArray(R.array.color_list);
        List<Integer> colorList = new ArrayList<>();

        for (int i = 0; i < colors.length; i++){
            colorList.add(colors[i]);
        }

        return colorList;
    }
}
