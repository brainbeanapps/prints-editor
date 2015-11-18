package com.brainbeanapps.rosty.printseditordemo.ui.fragment.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.BaseListAdapter;
import com.brainbeanapps.rosty.printseditordemo.ui.view.EmptyView;
import com.brainbeanapps.rosty.printseditordemo.ui.widget.RecyclerViewEmptySupport;

import java.util.List;

/**
 * Created by rosty on 7/7/2015.
 */
public abstract class BaseListFragment <T> extends BaseFragment {

    public static final String TAG = BaseListFragment.class.getSimpleName();

    protected RecyclerViewEmptySupport recyclerView;
    protected SwipeRefreshLayout swipeRefresh;
    protected EmptyView emptyView;

    protected List<T> dataList;

    protected BaseListAdapter adapter;

    @Override
    public void setupView() {
        recyclerView = (RecyclerViewEmptySupport) rootView.findViewById(R.id.recycler_view);
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        emptyView = (EmptyView) rootView.findViewById(R.id.empty_view);

        dataList = getData();
        adapter = getAdapter();
        adapter.updateData(dataList);

        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);

        setRefreshing(false);
    }

    public abstract RecyclerView.LayoutManager getLayoutManager();
    public abstract BaseListAdapter getAdapter();
    public abstract List<T> getData();

    protected void setRefreshing(final boolean refreshing){
        swipeRefresh.setEnabled(false);
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(refreshing);
            }
        });
    }
}
