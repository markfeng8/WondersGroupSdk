/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wondersgroup.android.sdk.base.MvpBaseFragment;

import java.util.List;

/**
 * Created by x-sir on 2017/5/24 :)
 * Function:Fragment Pager的适配器
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<MvpBaseFragment> mFragments;
    private String[] mTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<MvpBaseFragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
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
        return (mTitles != null && mTitles.length > 0) ? mTitles[position] : "";
    }
}
