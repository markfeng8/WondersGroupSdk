package com.wondersgroup.android.jkcs_sdk.ui.payrecord.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.flyco.tablayout.SlidingTabLayout;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseFragment;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.MyFragmentPagerAdapter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付记录页面
 */
public class FeeRecordActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private List<MvpBaseFragment> mFragments;
    private String[] topTitles = new String[2];
    private static final String TAG = "FeeRecordActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_pay_record);
        findViews();
        initData();
        initFragment();
        initListener();
    }

    private void initListener() {

    }

    private void findViews() {
        viewPager = findViewById(R.id.viewPager);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new FinishedOrderFragment());
        mFragments.add(new UnfinishedOrderFragment());
        if (mFragments != null && mFragments.size() > 0) {
            // 设置ViewPager的适配器
            viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, topTitles));
            slidingTabLayout.setViewPager(viewPager); // 将slidingTabLayout和ViewPager绑定！
        }
        viewPager.setCurrentItem(0); // 默认在第一页
    }

    private void initData() {
        topTitles[0] = getResources().getString(R.string.wonders_finished_order);
        topTitles[1] = getResources().getString(R.string.wonders_unfinished_order);
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, FeeRecordActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
