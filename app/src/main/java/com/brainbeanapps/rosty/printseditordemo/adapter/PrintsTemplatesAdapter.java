package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.holder.PrintsTemplatesHolder;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorBackground;
import com.brainbeanapps.rosty.printseditordemo.factory.EditorSpaceFactory;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;
import com.brainbeanapps.rosty.printseditorlib.exaption.EditorException;
import com.brainbeanapps.rosty.printseditorlib.listener.EditorMeasuredListener;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintBackgroundView;
import com.brainbeanapps.rosty.printseditorlib.widget.TemplateView;

import java.util.List;

/**
 * Created by rosty on 7/8/2015.
 */
public class PrintsTemplatesAdapter extends BaseListAdapter<EditorTemplate, PrintsTemplatesHolder> implements
        EditorMeasuredListener{

    public static final String TAG = PrintsTemplatesAdapter.class.getSimpleName();

    public interface OnTemplateSelectedListener {

        void onTemplateSelected(EditorTemplate template);
    }

    private OnTemplateSelectedListener onTemplateSelectedListener;

    private int height;
    private int width;
    private boolean isMeasured;

    private Drawable templateImage;

    public PrintsTemplatesAdapter(Context context, List<EditorTemplate> data) {
        super(context, data);
    }

    public PrintsTemplatesAdapter(Context context, List<EditorTemplate> data, OnTemplateSelectedListener onTemplateSelectedListener) {
        super(context, data);
        this.onTemplateSelectedListener = onTemplateSelectedListener;

        templateImage = context.getResources().getDrawable(R.drawable.test_image);
    }

    public void setOnTemplateSelectedListener(OnTemplateSelectedListener onTemplateSelectedListener) {
        this.onTemplateSelectedListener = onTemplateSelectedListener;
    }

    @Override
    public PrintsTemplatesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_print_template, parent, false);

        return new PrintsTemplatesHolder(v);
    }

    @Override
    public void onBindViewHolder(PrintsTemplatesHolder holder, final int position) {

        PrintBackgroundView backgroundView = holder.getPrintBackgroundView();
        EditorTemplate template = data.get(position);

        if (isMeasured){
            backgroundView.setChildContainerSize(height, width);
            backgroundView.removeEditorMeasuredListener(this);
        }

        if (position == 0 && !isMeasured){
            backgroundView.addEditorMeasuredListener(this);
        }

        try {
            TemplateView templateView = holder.getPrintBackgroundView().getTemplateView();
            templateView.setTemplateImage(R.drawable.test_template);

            if (template.getMask() != 0){
                templateView.setMask(template.getMask());
            }

            if (isMeasured && template.isSquare()){
                int size = width > height ? height : width;

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(size, size, Gravity.TOP);
                templateView.setLayoutParams(lp);
            }else if (!template.isSquare()){
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, Gravity.TOP);
                templateView.setLayoutParams(lp);
            }
        } catch (EditorException e) {
            Log.e(TAG, e.getMessage());
        }

        EditorSpaceFactory.setEditorSpace(backgroundView, EditorBackground.T_SHIRT);
        backgroundView.setBackground(context.getResources().getDrawable(R.drawable.test_bg_image));

        backgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTemplateSelectedListener!= null) {
                    onTemplateSelectedListener.onTemplateSelected(data.get(position));
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
