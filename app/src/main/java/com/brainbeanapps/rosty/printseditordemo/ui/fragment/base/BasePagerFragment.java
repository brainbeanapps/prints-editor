package com.brainbeanapps.rosty.printseditordemo.ui.fragment.base;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/15/2015.
 */
public abstract class BasePagerFragment extends BaseFragment {

    public static final String TAG = BasePagerFragment.class.getSimpleName();

    protected ViewPager viewPager;
    protected TabLayout tabLayout;

    @Override
    public void setupView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        viewPager.setAdapter(getAdapter());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
    }

    public abstract FragmentPagerAdapter getAdapter();
}