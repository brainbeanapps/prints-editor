package com.brainbeanapps.rosty.printseditordemo.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/16/2015.
 */
public class SVGHolder extends BaseHolder {

    private ImageView svgImage;

    public SVGHolder(View container) {
        super(container);

        svgImage = (ImageView) container.findViewById(R.id.svg_image);
    }

    public ImageView getSvgImage() {
        return svgImage;
    }
}
