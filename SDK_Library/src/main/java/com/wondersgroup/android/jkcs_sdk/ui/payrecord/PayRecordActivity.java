package com.wondersgroup.android.jkcs_sdk.ui.payrecord;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.BaseFragment;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.MyFragmentPagerAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.frament.FinishedOrderFragment;
import com.wondersgroup.android.jkcs_sdk.ui.frament.UnfinishedOrderFragment;

import java.util.ArrayList;
import java.util.List;

// 支付记录页面
public class PayRecordActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tvTitleName;
    private SlidingTabLayout slidingTabLayout;
    private List<BaseFragment> fragments;
    private String[] topTitles = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        tvTitleName = findViewById(R.id.tvTitleName);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new FinishedOrderFragment());
        fragments.add(new UnfinishedOrderFragment());
        if (fragments != null && fragments.size() > 0) {
            // 设置ViewPager的适配器
            viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, topTitles));
            slidingTabLayout.setViewPager(viewPager); // 将slidingTabLayout和ViewPager绑定！
        }
        viewPager.setCurrentItem(0); // 默认在第一页
    }

    private void initData() {
        tvTitleName.setText(R.string.wonders_pay_fee_record);
        topTitles[0] = getResources().getString(R.string.wonders_finished_order);
        topTitles[1] = getResources().getString(R.string.wonders_unfinished_order);
    }
}
