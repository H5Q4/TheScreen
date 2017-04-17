package com.github.jupittar.commlib.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommonPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public CommonPagerAdapter(FragmentManager fm) {
        this(fm, new ArrayList<Fragment>());
    }

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
        mTitles = new ArrayList<>();
    }

    public void addPage(Fragment fragment, String title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    public void addPage(Fragment fragment) {
        addPage(fragment, " ");
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
