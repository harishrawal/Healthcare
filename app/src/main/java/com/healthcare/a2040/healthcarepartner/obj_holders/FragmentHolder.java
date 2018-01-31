package com.healthcare.a2040.healthcarepartner.obj_holders;

import android.support.v4.app.Fragment;

/**
 * Created by Amit-PC on 1/18/2018.
 */

public class FragmentHolder {
    private Fragment fragment;
    private String fragmentTitle;

    public Fragment getFragment() {
        return fragment;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }
}
