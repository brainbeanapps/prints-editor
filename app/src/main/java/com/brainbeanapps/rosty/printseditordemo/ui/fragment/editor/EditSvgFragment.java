package com.brainbeanapps.rosty.printseditordemo.ui.fragment.editor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.SVGAdapter;
import com.brainbeanapps.rosty.printseditordemo.ui.HorizontalSpaceItemDecoration;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.view.ColorBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosty on 7/10/2015.
 */
public class EditSvgFragment extends BaseFragment {

    public static final String TAG = EditSvgFragment.class.getSimpleName();

    public static final int RESULT_SVG = 0x321;
    public static final int RESULT_COLOR = 0x121;

    public static final String RESULT_COLOR_DATA = "RESULT_COLOR_DATA";
    public static final String RESULT_SVG_DATA = "RESULT_SVG_DATA";

    private RecyclerView svgListView;
    private ColorBar colorBar;

    private int currentSVG;

    public static EditSvgFragment newInstance(Fragment targetFragment, int requestCode){
        EditSvgFragment fragment = new EditSvgFragment();
        fragment.setTargetFragment(targetFragment, requestCode);
        return fragment;
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_editor_svg, container, false);
    }

    @Override
    public void setupView() {
        svgListView = (RecyclerView) rootView.findViewById(R.id.edit_svg_list);
        setupSvgList();

        colorBar = (ColorBar) rootView.findViewById(R.id.edit_color_bar);
        colorBar.setupView(colorsChangeListener);
    }

    private void setupSvgList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        svgListView.setLayoutManager(layoutManager);

        SVGAdapter adapter = new SVGAdapter(getActivity(), getSVGList());
        adapter.setOnSvgSelectedListener(onSVGSelectedListener);
        svgListView.setAdapter(adapter);

        HorizontalSpaceItemDecoration itemDecoration = new HorizontalSpaceItemDecoration(0, adapter.getItemCount());
        svgListView.addItemDecoration(itemDecoration);
    }

    private List<Integer> getSVGList(){
        List<Integer> svgList = new ArrayList<>();
        svgList.add(R.raw.ic_android_black_48px);
        svgList.add(R.raw.ic_store_black_48px);
        svgList.add(R.raw.ic_theaters_black_48px);
        svgList.add(R.raw.ic_flag_black_48px);
        svgList.add(R.raw.ic_toys_black_48px);
        svgList.add(R.raw.ic_airballoon);
        svgList.add(R.raw.ic_broom);
        svgList.add(R.raw.ic_beer);
        svgList.add(R.raw.ic_cake);
        return svgList;
    }

    private SVGAdapter.OnSVGSelectedListener onSVGSelectedListener = new SVGAdapter.OnSVGSelectedListener() {
        @Override
        public void onSVGSelected(int svg) {
            currentSVG = svg;
            sendSVGData(svg);
        }
    };

    private ColorBar.OnColorsChangeListener colorsChangeListener = new ColorBar.OnColorsChangeListener() {
        @Override
        public void onColorChanged(int color) {
            if (currentSVG != 0) {
                sendColorData(color);
            }
        }
    };

    private void sendSVGData(int svgId){
        Intent intent = new Intent();
        intent.putExtra(RESULT_SVG_DATA, svgId);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_SVG, RESULT_SVG, intent);
    }

    private void sendColorData(int color){
        Intent intent = new Intent();
        intent.putExtra(RESULT_COLOR_DATA, color);
        intent.putExtra(RESULT_SVG_DATA, currentSVG);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_SVG, RESULT_COLOR, intent);
    }
}
