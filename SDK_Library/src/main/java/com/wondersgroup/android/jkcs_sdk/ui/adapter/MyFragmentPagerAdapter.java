package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wondersgroup.android.jkcs_sdk.base.BaseFragment;

import java.util.List;

/**
 * Created by x-sir on 2017/5/24 :)
 * Function:Fragment Pager的适配器
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;
    private String[] mTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> mFragments, String[] mTitles) {
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
