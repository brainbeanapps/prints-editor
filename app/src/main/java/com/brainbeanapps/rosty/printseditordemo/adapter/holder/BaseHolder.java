package com.brainbeanapps.rosty.printseditordemo.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rosty on 7/7/2015.
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private View container;

    public BaseHolder(View container) {
        super(container);

        this.container = container;
    }

    public View getContainer() {
        return container;
    }
}