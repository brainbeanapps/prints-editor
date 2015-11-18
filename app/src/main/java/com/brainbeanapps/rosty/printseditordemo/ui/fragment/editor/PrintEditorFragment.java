package com.brainbeanapps.rosty.printseditordemo.ui.fragment.editor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.BasePagerAdapter;
import com.brainbeanapps.rosty.printseditordemo.adapter.PhotoAdapter;
import com.brainbeanapps.rosty.printseditordemo.data.PrintsDataSource;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorAction;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorBackground;
import com.brainbeanapps.rosty.printseditordemo.factory.EditorSpaceFactory;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BasePagerFragment;
import com.brainbeanapps.rosty.printseditordemo.utile.FileManager;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorState;
import com.brainbeanapps.rosty.printseditorlib.model.PrintDataSet;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;
import com.brainbeanapps.rosty.printseditorlib.enums.ImageFilter;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintBackgroundView;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintEditorView;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

/**
 * Created by rosty on 7/7/2015.
 */
public class PrintEditorFragment extends BasePagerFragment implements
        TabLayout.OnTabSelectedListener,
        PrintsEditorActivity.OnEditorActionListener,
        PhotoAdapter.OnPhotoSelectedListener{

    public static final String TAG = PrintEditorFragment.class.getSimpleName();

    public static final String KEY_PRINT_TEMPLATE = "KEY_PRINT_TEMPLATE";

    public static final int REQUEST_EDIT_IMAGE = 0x123;
    public static final int REQUEST_EDIT_TEXT = 0x124;
    public static final int REQUEST_EDIT_SVG = 0x15;

    private static final int EDIT_TEXT_TAB = 0;
    private static final int EDIT_IMAGE_TAB = 1;
    private static final int EDIT_SVG_TAB = 2;

    private PrintEditorView printEditorView;
    private PrintBackgroundView printBackgroundView;

    private PrintsDataSource dataSource;

    public static PrintEditorFragment getInstance(EditorTemplate template){
        PrintEditorFragment fragment = new PrintEditorFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_PRINT_TEMPLATE, template);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void setupView() {
        super.setupView();

        EditorTemplate template = (EditorTemplate) getArguments().getSerializable(KEY_PRINT_TEMPLATE);

        printEditorView = (PrintEditorView) rootView.findViewById(R.id.prints_editor);
        printBackgroundView = (PrintBackgroundView) rootView.findViewById(R.id.prints_editor_bg);

        printEditorView.setEditImage(R.drawable.test_image, 0);
        printEditorView.setTemplate(template);
        printBackgroundView.setBackgroundImage(getResources().getDrawable(R.drawable.test_bg_image));

        EditorSpaceFactory.setEditorSpace(printBackgroundView, EditorBackground.T_SHIRT);

        dataSource = new PrintsDataSource(getActivity());

        setupTabs();
    }

    @Override
    public void onResume() {
        super.onResume();

        dataSource.open();
    }

    @Override
    public void onPause() {
        super.onPause();

        dataSource.close();
    }

    @Override
    public FragmentPagerAdapter getAdapter() {
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager());

        adapter.addFragment(EditTextFragment.newInstance(this, REQUEST_EDIT_TEXT));
        adapter.addFragment(EditImageFragment.newInstance(this, REQUEST_EDIT_IMAGE));
        adapter.addFragment(EditSvgFragment.newInstance(this, REQUEST_EDIT_SVG));

        return adapter;
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

    public void setupTabs() {
        tabLayout.getTabAt(EDIT_TEXT_TAB).setIcon(getResources().getDrawable(R.drawable.ic_tab_text));
        tabLayout.getTabAt(EDIT_IMAGE_TAB).setIcon(getResources().getDrawable(R.drawable.ic_tab_image));
        tabLayout.getTabAt(EDIT_SVG_TAB).setIcon(getResources().getDrawable(R.drawable.ic_tab_svg));

        tabLayout.setOnTabSelectedListener(this);
        viewPager.setCurrentItem(EDIT_IMAGE_TAB);
        viewPager.setCurrentItem(EDIT_TEXT_TAB);
        tabLayout.getTabAt(EDIT_TEXT_TAB).select();
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_prints_editor, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_EDIT_IMAGE:
                switch (resultCode) {

                    case EditImageFragment.RESULT_BRIGHTNESS:
                        int brightness = data.getIntExtra(EditImageFragment.RESULT_BRIGHTNESS_DATA, 0);
                        printEditorView.setImageBrightness((brightness - 100) * 1f);
                        break;

                    case EditImageFragment.RESULT_CONTRAST:
                        int contrast = data.getIntExtra(EditImageFragment.RESULT_CONTRAST_DATA, 0);
                        if (contrast >= 50) {
                            printEditorView.setImageContrast(((contrast - 40) * 1f) / 10);
                        } else {
                            printEditorView.setImageContrast((contrast * 2f) / 100);
                        }
                        break;

                    case EditImageFragment.RESULT_FILTER:
                        ImageFilter filter = (ImageFilter) data.getSerializableExtra(EditImageFragment.RESULT_FILTER_DATA);
                        printEditorView.setImageFilter(filter);
                        break;

                }
                break;

            case REQUEST_EDIT_TEXT:
                switch (resultCode) {

                    case EditTextFragment.RESULT_GRAVITY:
                        int gravity = data.getIntExtra(EditTextFragment.RESULT_GRAVITY_DATA, Gravity.CENTER);
                        printEditorView.setTextGravity(gravity);
                        break;

                    case EditTextFragment.RESULT_SIZE:
                        int size = data.getIntExtra(EditTextFragment.RESULT_SIZE_DATA, 1);
                        printEditorView.updateTextSize(size);
                        break;

                    case EditTextFragment.RESULT_COLOR:
                        int color = data.getIntExtra(EditTextFragment.RESULT_COLOR_DATA, 1);
                        printEditorView.setTextColor(color);
                        break;

                    case EditTextFragment.RESULT_FONT:
                        String font = data.getStringExtra(EditTextFragment.RESULT_FONT_DATA);
                        printEditorView.setTextFont(font);
                        break;
                }
                break;

            case REQUEST_EDIT_SVG:

                int blackColor = getResources().getColor(android.R.color.black);
                int grayColor = getResources().getColor(R.color.divider);

                switch (resultCode) {

                    case EditSvgFragment.RESULT_SVG:
                        int svgId1 = data.getIntExtra(EditSvgFragment.RESULT_SVG_DATA, Gravity.CENTER);
                        SVG svg1 = SVGParser.getSVGFromResource(getResources(), svgId1, blackColor, grayColor);
                        printEditorView.setSVGEditImage(svg1, 0);
                        break;

                    case EditSvgFragment.RESULT_COLOR:
                        int color = data.getIntExtra(EditSvgFragment.RESULT_COLOR_DATA, 1);
                        int svgId2 = data.getIntExtra(EditSvgFragment.RESULT_SVG_DATA, 1);
                        SVG svg2 = SVGParser.getSVGFromResource(getResources(), svgId2, blackColor, color);
                        printEditorView.setSVGEditImage(svg2, 0);
                        break;

                }
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){

            case EDIT_IMAGE_TAB:
                onImageTabSelected();
                break;

            case EDIT_TEXT_TAB:
                onTextTabSelected();
                break;

            case EDIT_SVG_TAB:
                onSVGTabSelected();
                break;
        }
    }

    private void onTextTabSelected(){
        printEditorView.setEditorState(EditorState.EDIT_TEXT);

        viewPager.setCurrentItem(EDIT_TEXT_TAB);
        ((PrintsEditorActivity)getActivity()).setPrintsEditorTextState();
    }

    private void onImageTabSelected(){
        printEditorView.setEditorState(EditorState.EDIT_IMAGE);

        viewPager.setCurrentItem(EDIT_IMAGE_TAB);
        ((PrintsEditorActivity)getActivity()).setPrintsEditorImageState();
    }

    private void onSVGTabSelected(){
        printEditorView.setEditorState(EditorState.EDIT_IMAGE);

        viewPager.setCurrentItem(EDIT_SVG_TAB);
        ((PrintsEditorActivity) getActivity()).setPrintsEditorSVGState();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onAction(EditorAction action) {

        switch (action){

            case SAVE_RESULT:
                Bitmap print = printEditorView.getResultImage();
                String path = FileManager.saveImage(print);


                if (path != null){

                    PrintDataSet printDataSet = printEditorView.getExportResult();
                    printDataSet.getImageDataSet().setResultImagePath(path);

                    dataSource.createPrint(printDataSet);
                }
                break;

            case ADD_IMAGE:
                break;

            case RESET_IMAGE:
                printEditorView.resetImage();
                break;
        }
    }

    @Override
    public void onPhotoSelected(Bitmap image) {
        printEditorView.resetImage();
        printEditorView.setEditImage(image, 0);
    }
}
