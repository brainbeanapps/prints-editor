package com.brainbeanapps.rosty.printseditordemo.ui.fragment.dialog;

import android.content.Context;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseDialogFragment;

/**
 * Created by rosty on 7/24/2015.
 */
public class SaveDialogFragment extends BaseDialogFragment {

    public static final String TAG = SaveDialogFragment.class.getSimpleName();

    public static  SaveDialogFragment newInstance(Context context){

        String title = context.getString(R.string.d_save_title);
        String negative = context.getString(R.string.d_save_negative);
        String positive = context.getString(R.string.d_save_positive);

        SaveDialogFragment exitDialogFragment = new SaveDialogFragment();
        exitDialogFragment.build(title, negative, positive);

        return exitDialogFragment;
    }

    @Override
    public String getDialogTag() {
        return TAG;
    }
}
