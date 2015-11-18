package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.holder.FilterHolder;
import com.brainbeanapps.rosty.printseditordemo.utile.Helper;
import com.brainbeanapps.rosty.printseditorlib.async.BitmapFilterTask;
import com.brainbeanapps.rosty.printseditorlib.enums.ImageFilter;

import java.util.List;

/**
 * Created by rosty on 7/13/2015.
 */
public class FiltersAdapter extends BaseListAdapter<ImageFilter, FilterHolder> {

    public interface OnFilterSelectedListener {

        void onFilterSelected(ImageFilter filter);
    }

    private OnFilterSelectedListener onFontChosenListener;
    private Bitmap image;

    public FiltersAdapter(Context context, List<ImageFilter> data) {
        super(context, data);
    }

    public FiltersAdapter(Context context, List<ImageFilter> data, Bitmap image) {
        super(context, data);

        setImage(image);
    }

    public FiltersAdapter(Context context, List<ImageFilter> data, OnFilterSelectedListener onFontChosenListener, Bitmap image) {
        super(context, data);
        this.onFontChosenListener = onFontChosenListener;
        setImage(image);
    }

    public void setImage(Bitmap image) {

        int size = Helper.dpToPx(64, context.getResources());
        this.image = com.brainbeanapps.rosty.printseditorlib.utile.Helper.getResizedBitmap(image, size, size);

        notifyDataSetChanged();
    }

    public void setOnFilterChosenListener(OnFilterSelectedListener onFontChosenListener) {
        this.onFontChosenListener = onFontChosenListener;
    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filter, parent, false);
        return new FilterHolder(v);
    }

    @Override
    public void onBindViewHolder(final FilterHolder holder, final int position) {

        ImageFilter filter = data.get(position);

        if (filter != null) {
            new BitmapFilterTask(holder.getFilterImage(), image, context).execute(filter);

            holder.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFontChosenListener != null) {
                        onFontChosenListener.onFilterSelected(data.get(position));
                    }
                }
            });
        }
    }


}
