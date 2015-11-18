package com.brainbeanapps.rosty.printseditordemo.utile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/9/2015.
 */
public class FragmentHelper {

    private FragmentManager fragmentManager;
    private int fragmentsContainerId;
    private Fragment currentFragment;

    public FragmentHelper(FragmentManager fragmentManager, int fragmentsContainerId) {
        this.fragmentManager = fragmentManager;
        this.fragmentsContainerId = fragmentsContainerId;
    }

    public void replaceFragment(Fragment fragment, String tag){
        currentFragment = fragment;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.frag_in, R.anim.frag_out);
        ft.replace(fragmentsContainerId, currentFragment, tag);
        ft.commit();
    }

    public void clearContainer(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(currentFragment).commit();
    }
}
