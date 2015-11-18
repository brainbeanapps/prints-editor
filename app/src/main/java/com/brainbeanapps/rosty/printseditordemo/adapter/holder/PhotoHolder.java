package com.brainbeanapps.rosty.printseditordemo.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/15/2015.
 */
public class PhotoHolder extends BaseHolder {

    private ImageView photo;

    public PhotoHolder(View container) {
        super(container);

        photo = (ImageView) container.findViewById(R.id.photo_image);
    }

    public ImageView getPhoto() {
        return photo;
    }
}
