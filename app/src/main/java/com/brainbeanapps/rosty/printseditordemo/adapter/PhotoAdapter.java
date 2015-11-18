package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.holder.PhotoHolder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by rosty on 7/15/2015.
 */
public class PhotoAdapter extends BaseListAdapter<String, PhotoHolder> {


    public interface OnPhotoSelectedListener {

        void onPhotoSelected(Bitmap image);
    }

    private OnPhotoSelectedListener onPhotoSelectedListener;

    public PhotoAdapter(Context context, List<String> data) {
        super(context, data);
    }

    public PhotoAdapter(Context context, List<String> data, OnPhotoSelectedListener onPhotoSelectedListener) {
        super(context, data);
        this.onPhotoSelectedListener = onPhotoSelectedListener;
    }

    public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
        this.onPhotoSelectedListener = onPhotoSelectedListener;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new PhotoHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {

        final String path = data.get(position);

        if (path != null) {
            Picasso.with(context)
                    .load(new File(path))
                    .resize(400, 400)
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.bg_empty_holder))
                    .into(holder.getPhoto());

            holder.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPhotoSelectedListener != null) {
                        Bitmap image = BitmapFactory.decodeFile(path);
                        if (image != null){
                            onPhotoSelectedListener.onPhotoSelected(image);
                        }
                    }
                }
            });
        }
    }
}
