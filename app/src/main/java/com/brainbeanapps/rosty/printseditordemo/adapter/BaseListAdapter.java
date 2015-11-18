package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.brainbeanapps.rosty.printseditordemo.adapter.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public abstract class BaseListAdapter<T, VH extends BaseHolder> extends RecyclerView.Adapter<VH>{

    protected static final int ANIMATION_POSITION = 5;

    protected int lastPosition;
    protected Context context;

    protected List<T> data;
    protected View.OnClickListener onClickListener;

    public BaseListAdapter(Context context, List<T> data) {
        if (data == null)
            data = new ArrayList();

        this.context = context;
        this.data = data;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void updateData(List<T> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}