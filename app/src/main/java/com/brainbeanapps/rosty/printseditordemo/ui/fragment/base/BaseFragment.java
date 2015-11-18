package com.brainbeanapps.rosty.printseditordemo.ui.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rosty on 7/7/2015.
 */
public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = setRootView(inflater, container);
        setupView();

        return rootView;
    }


    public abstract View setRootView(LayoutInflater inflater, ViewGroup container);
    public abstract void setupView();
//
//    @Override
//    public void onSaveInstanceState(final Bundle outState) {
//        setTargetFragment(this , -1);
//    }
}
