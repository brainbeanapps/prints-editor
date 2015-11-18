package com.brainbeanapps.rosty.printseditordemo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.BaseListAdapter;
import com.brainbeanapps.rosty.printseditordemo.adapter.UserPrintsAdapter;
import com.brainbeanapps.rosty.printseditordemo.data.PrintsDataSource;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseListFragment;
import com.brainbeanapps.rosty.printseditorlib.model.PrintDataSet;

import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public class UserPrintsFragment extends BaseListFragment<PrintDataSet> {

    public static final String TAG = UserPrintsFragment.class.getSimpleName();

    private PrintsDataSource dataSource;
    private UserPrintsAdapter adapter;

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public BaseListAdapter getAdapter() {
        adapter =  new UserPrintsAdapter(getActivity(), null);
        adapter.setOnPrintSelectedListener(onPrintSelectedListener);
        return adapter;
    }

    @Override
    public List<PrintDataSet> getData() {
        if (dataSource == null) {
            dataSource = new PrintsDataSource(getActivity());
            dataSource.open();
        }
        return dataSource.getAllPrints();
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_user_prints, container, false);
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

    private UserPrintsAdapter.OnPrintSelectedListener onPrintSelectedListener = new UserPrintsAdapter.OnPrintSelectedListener() {
        @Override
        public void onPrintSelected(PrintDataSet printDataSet) {
            ((PrintsEditorActivity) getActivity()).setPrintExportState(printDataSet.getId());
        }
    };
}
