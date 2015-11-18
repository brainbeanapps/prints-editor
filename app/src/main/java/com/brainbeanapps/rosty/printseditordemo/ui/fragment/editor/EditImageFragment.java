package com.brainbeanapps.rosty.printseditordemo.ui.fragment.editor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.FiltersAdapter;
import com.brainbeanapps.rosty.printseditordemo.adapter.PhotoAdapter;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorAction;
import com.brainbeanapps.rosty.printseditordemo.ui.HorizontalSpaceItemDecoration;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.view.Slider;
import com.brainbeanapps.rosty.printseditorlib.enums.ImageFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public class EditImageFragment extends BaseFragment implements
        PrintsEditorActivity.OnEditorActionListener,
        PhotoAdapter.OnPhotoSelectedListener{

    public static final String TAG = EditImageFragment.class.getSimpleName();

    public static final int RESULT_BRIGHTNESS = 0x221;
    public static final int RESULT_CONTRAST = 0x321;
    public static final int RESULT_FILTER = 0x121;

    public static final String RESULT_BRIGHTNESS_DATA = "RESULT_RIGHTNESS_DATA";
    public static final String RESULT_CONTRAST_DATA = "RESULT_CONTRAST_DATA";
    public static final String RESULT_FILTER_DATA = "RESULT_FILTER_DATA";

    public static final int DEFAULT_BRIGHTNESS = 100;
    public static final int DEFAULT_CONTRAST = 50;

    private Slider sliderCn;
    private Slider sliderBr;

    private RecyclerView filterListView;
    private FiltersAdapter adapter;

    public static EditImageFragment newInstance(Fragment targetFragment, int requestCode){
        EditImageFragment fragment = new EditImageFragment();
        fragment.setTargetFragment(targetFragment, requestCode);
        return fragment;
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_editor_image, container, false);
    }

    @Override
    public void setupView() {
        sliderBr = (Slider) rootView.findViewById(R.id.slider_br);
        sliderCn = (Slider) rootView.findViewById(R.id.slider_cn);

        sliderBr.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                sendBrightnessResult(value);
            }
        });

        sliderCn.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                sendContrastsResult(value);
            }
        });

        filterListView = (RecyclerView) rootView.findViewById(R.id.edit_filter_list);
        setupFilterList();
        setupSliderValue(DEFAULT_BRIGHTNESS, sliderBr);
        setupSliderValue(DEFAULT_CONTRAST, sliderCn);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((PrintsEditorActivity) getActivity()).addActionListener(this);
        ((PrintsEditorActivity) getActivity()).addPhotoSelectedListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((PrintsEditorActivity) getActivity()).removeActionListener(this);
        ((PrintsEditorActivity) getActivity()).removePhotoSelectedListener(this);
    }

    private void setupFilterList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        filterListView.setLayoutManager(layoutManager);

        Bitmap image = (BitmapFactory.decodeResource(getResources(), R.drawable.test_image));
        adapter = new FiltersAdapter(getActivity().getApplicationContext(), getFilters(), image);
        adapter.setOnFilterChosenListener(onFilterSelectedListener);
        filterListView.setAdapter(adapter);

        int space = (int) getResources().getDimension(R.dimen.content_space)/2;
        HorizontalSpaceItemDecoration itemDecoration = new HorizontalSpaceItemDecoration(space, adapter.getItemCount());
        filterListView.addItemDecoration(itemDecoration);
    }

    private List<ImageFilter> getFilters(){
        return Arrays.asList(ImageFilter.values());
    }

    private void sendBrightnessResult(int result){
        Intent intent = new Intent();
        intent.putExtra(RESULT_BRIGHTNESS_DATA, result);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_IMAGE, RESULT_BRIGHTNESS, intent);
    }

    private void sendContrastsResult(int result){
        Intent intent = new Intent();
        intent.putExtra(RESULT_CONTRAST_DATA, result);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_IMAGE, RESULT_CONTRAST, intent);
    }

    private void sendFilterResult(ImageFilter result){
        Intent intent = new Intent();
        intent.putExtra(RESULT_FILTER_DATA, result);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_IMAGE, RESULT_FILTER, intent);
    }

    private FiltersAdapter.OnFilterSelectedListener onFilterSelectedListener = new FiltersAdapter.OnFilterSelectedListener() {
        @Override
        public void onFilterSelected(ImageFilter filter) {
             sendFilterResult(filter);
        }
    };

    private void setupSliderValue(final int value, final Slider slider) {
        ViewTreeObserver viewTreeObserver = slider.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    slider.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    slider.setValue(value);
                }
            });
        }
    }

    @Override
    public void onAction(EditorAction action) {
        switch (action){

            case RESET_IMAGE:
                sliderBr.setValue(DEFAULT_BRIGHTNESS);
                sliderCn.setValue(DEFAULT_CONTRAST);
                break;
        }
    }

    @Override
    public void onPhotoSelected(Bitmap image) {
        adapter.setImage(image);
    }
}
