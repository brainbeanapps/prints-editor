package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.holder.UserPrintsHolder;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorBackground;
import com.brainbeanapps.rosty.printseditordemo.factory.EditorSpaceFactory;
import com.brainbeanapps.rosty.printseditorlib.async.ImgFileToBitmapTask;
import com.brainbeanapps.rosty.printseditorlib.exaption.EditorException;
import com.brainbeanapps.rosty.printseditorlib.listener.EditorMeasuredListener;
import com.brainbeanapps.rosty.printseditorlib.model.PrintDataSet;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintBackgroundView;

import java.util.List;

/**
 * Created by rosty on 7/8/2015.
 */
public class UserPrintsAdapter extends BaseListAdapter<PrintDataSet, UserPrintsHolder> implements
        EditorMeasuredListener{

    public static final String TAG = UserPrintsAdapter.class.getSimpleName();

    public interface OnPrintSelectedListener {

        void onPrintSelected(PrintDataSet printDataSet);
    }

    private int height;
    private int width;
    private boolean isMeasured;

    private OnPrintSelectedListener onPrintSelectedListener;

    public UserPrintsAdapter(Context context, List<PrintDataSet> data) {
        super(context, data);
    }

    public UserPrintsAdapter(Context context, List<PrintDataSet> data, OnPrintSelectedListener onPrintSelectedListener) {
        super(context, data);
        this.onPrintSelectedListener = onPrintSelectedListener;
    }

    public void setOnPrintSelectedListener(OnPrintSelectedListener onPrintSelectedListener) {
        this.onPrintSelectedListener = onPrintSelectedListener;
    }

    @Override
    public UserPrintsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_print, parent, false);
        return new UserPrintsHolder(v);
    }

    @Override
    public void onBindViewHolder(UserPrintsHolder holder, final int position) {
        PrintDataSet print = data.get(position);

        PrintBackgroundView backgroundView = holder.getPrintBackgroundView();

        if (isMeasured){
            backgroundView.setChildContainerSize(height, width);
        }

        if (position == 0){
            backgroundView.addEditorMeasuredListener(this);
        }

        try {
            ImageView imageView = holder.getPrintBackgroundView().getPreviewView();
            new ImgFileToBitmapTask(imageView).execute(print.getImageDataSet().getResultImagePath());
        } catch (EditorException e) {
            Log.e(TAG, e.getMessage());
        }

        EditorSpaceFactory.setEditorSpace(backgroundView, EditorBackground.T_SHIRT);
        backgroundView.setBackground(context.getResources().getDrawable(R.drawable.test_bg_image));

        backgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPrintSelectedListener != null) {
                    onPrintSelectedListener.onPrintSelected(data.get(position));
                }
            }
        });
    }

    @Override
    public void onMeasured(int height, int width) {
        this.height = height;
        this.width = width;
        isMeasured = true;
    }
}
