package com.brainbeanapps.rosty.printseditordemo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.BaseListAdapter;
import com.brainbeanapps.rosty.printseditordemo.adapter.PrintsTemplatesAdapter;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseListFragment;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public class PrintsTemplatesFragment extends BaseListFragment <EditorTemplate>{

    public static final String TAG = PrintsTemplatesFragment.class.getSimpleName();

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public BaseListAdapter getAdapter() {
        return new PrintsTemplatesAdapter(getActivity(), null, onTemplateSelectedListener);
    }

    @Override
    public List<EditorTemplate> getData() {
        ArrayList list = new ArrayList<>(EnumSet.allOf(EditorTemplate.class));
        list.remove(0);
        return list;
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_prints_templates, container, false);
    }

    private PrintsTemplatesAdapter.OnTemplateSelectedListener onTemplateSelectedListener = new PrintsTemplatesAdapter.OnTemplateSelectedListener() {
        @Override
        public void onTemplateSelected(EditorTemplate template) {
            ((PrintsEditorActivity)getActivity()).onTemplateSelected(template);
        }
    };
}
