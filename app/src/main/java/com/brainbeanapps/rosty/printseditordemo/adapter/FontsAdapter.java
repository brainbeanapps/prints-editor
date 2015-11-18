package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.holder.FontsHolder;
import com.brainbeanapps.rosty.printseditorlib.utile.FontCash;

import java.util.List;

/**
 * Created by rosty on 7/10/2015.
 */
public class FontsAdapter extends BaseListAdapter<String, FontsHolder> {

    public interface OnFontSelectedListener {

        void onFontSelected(String font);
    }

    private OnFontSelectedListener onFontSelectedListener;

    public FontsAdapter(Context context, List<String> data) {
        super(context, data);
    }

    public void setOnFontSelectedListener(OnFontSelectedListener onFontSelectedListener) {
        this.onFontSelectedListener = onFontSelectedListener;
    }

    @Override
    public FontsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_font, parent, false);
        return new FontsHolder(v);
    }

    @Override
    public void onBindViewHolder(FontsHolder holder, final int position) {
        Typeface font = FontCash.get(data.get(position), context);
        holder.getFont().setTypeface(font);

        holder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFontSelectedListener != null) {
                    onFontSelectedListener.onFontSelected(data.get(position));
                }
            }
        });
    }
}

