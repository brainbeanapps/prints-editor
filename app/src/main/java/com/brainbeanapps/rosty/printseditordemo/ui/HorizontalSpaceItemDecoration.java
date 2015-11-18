package com.brainbeanapps.rosty.printseditordemo.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rosty on 7/10/2015.
 */
public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int dataSize;

    public HorizontalSpaceItemDecoration(int space, int dataSize) {
        this.dataSize = dataSize;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,RecyclerView parent, RecyclerView.State state) {

        outRect.right = space;
        outRect.left = space;

        if (parent.getChildLayoutPosition(view) + 1 == dataSize){
            outRect.right = 0;
        }

        if (parent.getChildLayoutPosition(view) == 0){
            outRect.left = 0;
        }
    }
}