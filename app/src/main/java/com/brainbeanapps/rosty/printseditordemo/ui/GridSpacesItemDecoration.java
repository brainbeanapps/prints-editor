package com.brainbeanapps.rosty.printseditordemo.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rosty on 7/15/2015.
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {


    private int space;
    private int columnNumber;

    public GridSpacesItemDecoration(int columnNumber, int space) {
        this.columnNumber = columnNumber;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;
        outRect.right = space;
        outRect.left = space;
        outRect.top = space;

        if (parent.getChildLayoutPosition(view) < columnNumber) {
            outRect.top = space * 4;
        }
    }
}