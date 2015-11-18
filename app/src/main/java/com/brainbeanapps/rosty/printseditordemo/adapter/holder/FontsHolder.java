package com.brainbeanapps.rosty.printseditordemo.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/10/2015.
 */
public class FontsHolder extends BaseHolder {

    private TextView font;

    public FontsHolder(View container) {
        super(container);

        font = (TextView) container.findViewById(R.id.font_text);
    }

    public TextView getFont() {
        return font;
    }
}
