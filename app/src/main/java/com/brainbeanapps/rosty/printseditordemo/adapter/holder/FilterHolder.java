package com.brainbeanapps.rosty.printseditordemo.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/13/2015.
 */
public class FilterHolder extends BaseHolder {

    private ImageView filterImage;

    public FilterHolder(View container) {
        super(container);

        filterImage = (ImageView) container.findViewById(R.id.image_filter);
    }

    public ImageView getFilterImage() {
        return filterImage;
    }
}
