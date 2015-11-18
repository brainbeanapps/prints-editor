package com.brainbeanapps.rosty.printseditordemo.adapter.holder;

import android.view.View;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintBackgroundView;

/**
 * Created by rosty on 7/8/2015.
 */
public class BasePrintsHolder extends BaseHolder {

    private PrintBackgroundView printBackgroundView;

    public BasePrintsHolder(View container) {
        super(container);

        printBackgroundView = (PrintBackgroundView) container.findViewById(R.id.prints_background);
    }

    public PrintBackgroundView getPrintBackgroundView() {
        return printBackgroundView;
    }
}
