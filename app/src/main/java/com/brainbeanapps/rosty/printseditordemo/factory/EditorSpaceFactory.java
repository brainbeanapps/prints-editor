package com.brainbeanapps.rosty.printseditordemo.factory;

import com.brainbeanapps.rosty.printseditordemo.enums.EditorBackground;
import com.brainbeanapps.rosty.printseditorlib.model.BackgroundDataSet;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintBackgroundView;

/**
 * Created by rosty on 7/17/2015.
 */
public class EditorSpaceFactory {

    public static final float EDITABLE_SPACE_HEIGHT = 0.45f;
    public static final float EDITABLE_SPACE_WIDTH = 0.35f;
    public static final float EDITABLE_TOP_SPACE = 0.25f;
    public static final float EDITABLE_RIGHT_SPACE = 0.3f;

    public static void setEditorSpace(PrintBackgroundView backgroundView, EditorBackground background){

        switch (background){

            case T_SHIRT:
                backgroundView.setDataSet(new BackgroundDataSet(
                        EDITABLE_SPACE_HEIGHT,
                        EDITABLE_SPACE_WIDTH,
                        EDITABLE_TOP_SPACE,
                        EDITABLE_RIGHT_SPACE
                ));
                break;

            case CUP:
                break;

            case BOTTLE:
                break;
        }
    }
}
