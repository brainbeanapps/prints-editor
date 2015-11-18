package com.brainbeanapps.rosty.printseditordemo.ui.fragment.editor;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.FontsAdapter;
import com.brainbeanapps.rosty.printseditordemo.ui.HorizontalSpaceItemDecoration;
import com.brainbeanapps.rosty.printseditordemo.ui.decorator.GravityButton;
import com.brainbeanapps.rosty.printseditordemo.ui.decorator.GravityButton.*;
import com.brainbeanapps.rosty.printseditordemo.ui.decorator.MultipleStateButton;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.view.ColorBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public class EditTextFragment extends BaseFragment {

    public static final String TAG = EditTextFragment.class.getSimpleName();

    public static final int RESULT_GRAVITY = 0x121;
    public static final int RESULT_SIZE = 0x221;
    public static final int RESULT_COLOR = 0x321;
    public static final int RESULT_FONT = 0x421;

    public static final String RESULT_GRAVITY_DATA = "RESULT_GRAVITY_DATA";
    public static final String RESULT_SIZE_DATA = "RESULT_SIZE_DATA";
    public static final String RESULT_COLOR_DATA = "RESULT_COLOR_DATA";
    public static final String RESULT_FONT_DATA = "RESULT_FONT_DATA";

    private GravityButton btnGravityHr;
    private GravityButton btnGravityVr;
    private ImageButton btnSizeB;
    private ImageButton btnSizeS;

    private ColorBar colorBar;
    private RecyclerView fontListView;

    private int currentHrState;
    private int currentVrState;

    public static EditTextFragment newInstance(Fragment targetFragment, int requestCode){
        EditTextFragment fragment = new EditTextFragment();
        fragment.setTargetFragment(targetFragment, requestCode);
        return fragment;
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_editor_text, container, false);
    }

    @Override
    public void setupView() {
        btnSizeB = (ImageButton) rootView.findViewById(R.id.edit_btn_size_b);
        btnSizeS = (ImageButton) rootView.findViewById(R.id.edit_btn_size_s);

        colorBar = (ColorBar) rootView.findViewById(R.id.edit_color_bar);
        colorBar.setupView(colorsChangeListener);

        fontListView = (RecyclerView) rootView.findViewById(R.id.edit_font_list);
        setupFontList();

        ImageButton btnGravityHr = (ImageButton) rootView.findViewById(R.id.edit_btn_gravity_hr);
        ImageButton btnGravityVr = (ImageButton) rootView.findViewById(R.id.edit_btn_gravity_vr);

        this.btnGravityHr = new GravityButton(btnGravityHr, getHrGravityList(), getActivity());
        this.btnGravityVr = new GravityButton(btnGravityVr, getVrGravityList(), getActivity());

        this.btnGravityHr.setListener(hrListener);
        this.btnGravityVr.setListener(vrListener);
        this.btnGravityHr.setCurrentState(0);
        this.btnGravityVr.setCurrentState(2);

        btnSizeS.setOnClickListener(onSizeChangeListener);
        btnSizeB.setOnClickListener(onSizeChangeListener);
    }

    private void setupFontList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fontListView.setLayoutManager(layoutManager);

        FontsAdapter adapter = new FontsAdapter(getActivity(), getFontList());
        adapter.setOnFontSelectedListener(onFontSelectedListener);
        fontListView.setAdapter(adapter);

        int space = (int) getResources().getDimension(R.dimen.content_space);
        HorizontalSpaceItemDecoration itemDecoration = new HorizontalSpaceItemDecoration(space, adapter.getItemCount());
        fontListView.addItemDecoration(itemDecoration);
    }

    private GravityButton.OnStateChangeListener hrListener = new MultipleStateButton.OnStateChangeListener() {
        @Override
        public void onStateChanged(int state) {
            currentHrState = state;
            sendGravityData();
        }
    };

    private GravityButton.OnStateChangeListener vrListener = new MultipleStateButton.OnStateChangeListener() {
        @Override
        public void onStateChanged(int state) {
            currentVrState = state;
            sendGravityData();
        }
    };

    private View.OnClickListener onSizeChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.edit_btn_size_b:
                    sendSizeResult(1);
                    break;

                case R.id.edit_btn_size_s:
                    sendSizeResult(-1);
                    break;
            }
        }
    };

    private ColorBar.OnColorsChangeListener colorsChangeListener = new ColorBar.OnColorsChangeListener() {
        @Override
        public void onColorChanged(int color) {
            sendColorData(color);
        }
    };

    private FontsAdapter.OnFontSelectedListener onFontSelectedListener = new FontsAdapter.OnFontSelectedListener() {
        @Override
        public void onFontSelected(String font) {
            sendFontData(font);
        }
    };

    private void sendSizeResult(int result){
        Intent intent = new Intent();
        intent.putExtra(RESULT_SIZE_DATA, result);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_TEXT, RESULT_SIZE, intent);
    }

    private void sendGravityData(){
        Intent intent = new Intent();
        intent.putExtra(RESULT_GRAVITY_DATA, currentHrState | currentVrState);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_TEXT, RESULT_GRAVITY, intent);
    }

    private void sendColorData(int color){
        Intent intent = new Intent();
        intent.putExtra(RESULT_COLOR_DATA, color);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_TEXT, RESULT_COLOR, intent);
    }

    private void sendFontData(String font){
        Intent intent = new Intent();
        intent.putExtra(RESULT_FONT_DATA, font);
        getTargetFragment().onActivityResult(PrintEditorFragment.REQUEST_EDIT_TEXT, RESULT_FONT, intent);
    }

    private List<GravityState> getHrGravityList(){
        List<GravityState> states = new ArrayList<>();

        states.add(new GravityState(Gravity.CENTER, R.drawable.ic_editor_format_align_center));
        states.add(new GravityState(Gravity.LEFT, R.drawable.ic_editor_format_align_left));
        states.add(new GravityState(Gravity.RIGHT, R.drawable.ic_editor_format_align_right));

        return states;
    }

    private List<GravityState> getVrGravityList(){
        List<GravityState> states = new ArrayList<>();

        states.add(new GravityState(Gravity.CENTER, R.drawable.ic_editor_border_horizontal));
        states.add(new GravityState(Gravity.TOP, R.drawable.ic_editor_border_top));
        states.add(new GravityState(Gravity.BOTTOM, R.drawable.ic_editor_border_bottom));

        return states;
    }

    private List<String> getFontList(){
        TypedArray ta = getResources().obtainTypedArray(R.array.font_list);
        List<String> fonts = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            fonts.add(ta.getString(i));
        }
        ta.recycle();
        return fonts;
    }
}
