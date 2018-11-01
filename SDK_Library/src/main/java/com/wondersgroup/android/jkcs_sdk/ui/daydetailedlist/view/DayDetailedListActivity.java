/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.DayDetailedListAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.contract.DayDetailedListContract;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.presenter.DayDetailedListPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/11/01 :)
 * Function:日清单列表页面
 */
public class DayDetailedListActivity extends MvpBaseActivity<DayDetailedListContract.IView,
        DayDetailedListPresenter<DayDetailedListContract.IView>> implements DayDetailedListContract.IView {

    private static final String TAG = "DayDetailedListActivity";
    private RecyclerView recyclerView;
    private DayDetailedListAdapter mDayDetailedListAdapter;

    @Override
    protected DayDetailedListPresenter<DayDetailedListContract.IView> createPresenter() {
        return new DayDetailedListPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_day_detailed_list);
        findViews();
        setAdapter();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setAdapter() {
        if (mDayDetailedListAdapter == null) {
            mDayDetailedListAdapter = new DayDetailedListAdapter(this);
            recyclerView.setAdapter(mDayDetailedListAdapter);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            //mDayDetailedListAdapter.setDetails();
        }
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, DayDetailedListActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
