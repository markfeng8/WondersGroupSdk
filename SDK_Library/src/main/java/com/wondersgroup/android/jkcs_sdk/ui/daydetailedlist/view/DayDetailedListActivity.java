/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.DayDetailedListAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0005Entity;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.contract.DayDetailedListContract;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.presenter.DayDetailedListPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.DateScrollerDialog;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.data.Type;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.listener.OnDateSetListener;

import java.util.List;

/**
 * Created by x-sir on 2018/11/01 :)
 * Function:日清单列表页面
 */
public class DayDetailedListActivity extends MvpBaseActivity<DayDetailedListContract.IView,
        DayDetailedListPresenter<DayDetailedListContract.IView>> implements DayDetailedListContract.IView {

    private static final String TAG = "DayDetailedListActivity";
    private RecyclerView recyclerView;
    private TextView tvStartDate;
    private TextView tvTotalFee;
    private TextView tvBeforeDay;
    private TextView tvToday;
    private LinearLayout llTotalFee;
    private LoadingView mLoading;
    private DayDetailedListAdapter mDayDetailedListAdapter;
    private List<Cy0005Entity.DetailsBean> mDetails;
    private long mLastTime = System.currentTimeMillis();
    private String mOrgCode;
    private String mInHosId;
    private String mActivityTag;
    private String mMinMillis;
    private String mMaxMillis;

    @Override
    protected DayDetailedListPresenter<DayDetailedListContract.IView> createPresenter() {
        return new DayDetailedListPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_day_detailed_list);
        findViews();
        initData();
        initListener();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvTotalFee = findViewById(R.id.tvTotalFee);
        tvBeforeDay = findViewById(R.id.tvBeforeDay);
        tvToday = findViewById(R.id.tvToday);
        llTotalFee = findViewById(R.id.llTotalFee);
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mInHosId = intent.getStringExtra(IntentExtra.IN_HOS_ID);
            mActivityTag = intent.getStringExtra(IntentExtra.ACTIVITY_TAG);
            mMinMillis = intent.getStringExtra(IntentExtra.MIN_MILLIS);
            mMaxMillis = intent.getStringExtra(IntentExtra.MAX_MILLIS);
        }

        if ("InHospitalHomeActivity".equals(mActivityTag)) {
            tvToday.setText("今天");
        } else if ("InHospitalRecordActivity".equals(mActivityTag)) {
            tvToday.setText("后一天");
        }

        mLastTime = Long.parseLong(mMaxMillis);
        String currentDate = TimeUtil.getDate(Long.parseLong(mMaxMillis));
        tvStartDate.setText(currentDate);
        requestCy0005(currentDate);
    }

    private void initListener() {
        tvStartDate.setOnClickListener(v -> showDate());
        tvBeforeDay.setOnClickListener(v -> {
            String beforeDate = TimeUtil.getBeforeDate(91);
            String lastDate = TimeUtil.getLastDay(mLastTime);
            if (lastDate.equals(beforeDate)) {
                WToastUtil.show("仅支持3个月内日清单记录查询！");
            } else {
                mLastTime -= 1000L * 60L * 60L * 24L;
                tvStartDate.setText(lastDate);
                requestCy0005(lastDate);
            }
        });
        tvToday.setOnClickListener(v -> {
            dealWithTime();
        });
    }

    private void dealWithTime() {
        if (!TextUtils.isEmpty(mActivityTag)) {
            switch (mActivityTag) {
                case "InHospitalHomeActivity":
                    mLastTime = System.currentTimeMillis();
                    break;
                case "InHospitalRecordActivity":
                    mLastTime += 1000L * 60L * 60L * 24L;
                    break;
                default:
                    LogUtil.e("Invalid activity tag!");
                    break;
            }

            if (mLastTime > System.currentTimeMillis()) {
                WToastUtil.show("已显示出院当天日清单，无法查看未住院日清单！");
                mLastTime = System.currentTimeMillis();
            } else {
                String date = TimeUtil.getDate(mLastTime);
                tvStartDate.setText(date);
                requestCy0005(date);
            }
        }
    }

    private void setAdapter() {
        mDayDetailedListAdapter = new DayDetailedListAdapter(mDetails);
        recyclerView.setAdapter(mDayDetailedListAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void refreshAdapter() {
        if (mDayDetailedListAdapter != null) {
            mDayDetailedListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 显示日期(配置最大、最小时间伐值)
     */
    public void showDate() {
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId(getString(R.string.wonders_select_date_please))
                .setMinMilliseconds(Long.parseLong(mMinMillis))
                .setMaxMilliseconds(Long.parseLong(mMaxMillis))
                .setCurMilliseconds(mLastTime)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getSupportFragmentManager(), "year_month_day");
            }
        }
    }

    /**
     * 选择时间数据的回调
     */
    private OnDateSetListener mOnDateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DateScrollerDialog timePickerView, long milliseconds) {
            mLastTime = milliseconds;
            String date = TimeUtil.getDate(milliseconds);
            tvStartDate.setText(date);
            requestCy0005(date);
        }
    };

    private void requestCy0005(String date) {
        mPresenter.requestCy0005(mOrgCode, mInHosId, date);
    }

    /**
     * Activity 跳转的逻辑处理
     *
     * @param context   上下文
     * @param orgCode   组织机构编号
     * @param inHosId   就诊流水号
     * @param flag      页面跳转标志(从哪个页面跳转过来的)
     * @param minMillis 时间控件的最小时间
     * @param maxMillis 时间控件的最大时间
     */
    public static void actionStart(Context context, String orgCode, String inHosId, String flag, String minMillis, String maxMillis) {
        if (context != null) {
            Intent intent = new Intent(context, DayDetailedListActivity.class);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.IN_HOS_ID, inHosId);
            intent.putExtra(IntentExtra.ACTIVITY_TAG, flag);
            intent.putExtra(IntentExtra.MIN_MILLIS, minMillis);
            intent.putExtra(IntentExtra.MAX_MILLIS, maxMillis);
            context.startActivity(intent);
        } else {
            throw new NullPointerException("context is null!");
        }
    }

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.showLoadingDialog();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismissLoadingDialog();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCy0005Result(Cy0005Entity entity) {
        llTotalFee.setVisibility(entity != null ? View.VISIBLE : View.GONE);
        // 清除旧数据
        if (mDetails != null && mDetails.size() > 0) {
            mDetails.clear();
        }
        if (entity != null) {
            tvTotalFee.setText("￥" + entity.getRqdzje());
            mDetails = entity.getDetails();
            if (mDetails != null && mDetails.size() > 0) {
                setAdapter();
            }
        } else {
            LogUtil.e(TAG, "entity is null!");
            setAdapter();
        }
    }
}
