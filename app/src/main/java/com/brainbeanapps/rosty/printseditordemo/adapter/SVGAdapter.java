package com.brainbeanapps.rosty.printseditordemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.holder.SVGHolder;
import com.brainbeanapps.rosty.printseditordemo.utile.Helper;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.util.List;

/**
 * Created by rosty on 7/16/2015.
 */
public class SVGAdapter extends BaseListAdapter<Integer, SVGHolder> {

    public interface OnSVGSelectedListener {

        void onSVGSelected(int svg);
    }

    private OnSVGSelectedListener onSvgSelectedListener;

    public SVGAdapter(Context context, List<Integer> data) {
        super(context, data);
    }

    public SVGAdapter(Context context, List<Integer> data, OnSVGSelectedListener onSvgSelectedListener) {
        super(context, data);
        this.onSvgSelectedListener = onSvgSelectedListener;
    }

    public void setOnSvgSelectedListener(OnSVGSelectedListener onSvgSelectedListener) {
        this.onSvgSelectedListener = onSvgSelectedListener;
    }

    @Override
    public SVGHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_svg, parent, false);
        return new SVGHolder(v);
    }

    @Override
    public void onBindViewHolder(SVGHolder holder, int position) {
        final int svgId = data.get(position);

        Resources res = context.getResources();

        int blackColor = res.getColor(android.R.color.black);
        int grayColor = res.getColor(R.color.divider);

        SVG svg = SVGParser.getSVGFromResource(context.getResources(), svgId, blackColor, grayColor);

        if (svg != null) {

            int size = Helper.dpToPx(80, context.getResources());

            Picture test = svg.getPicture();
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            canvas.drawPicture(test, new Rect(0, 0, size, size));

            holder.getSvgImage().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            holder.getSvgImage().setImageBitmap(bitmap);

            holder.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSvgSelectedListener != null) {
                        onSvgSelectedListener.onSVGSelected(svgId);
                    }
                }
            });
        }
    }
}
