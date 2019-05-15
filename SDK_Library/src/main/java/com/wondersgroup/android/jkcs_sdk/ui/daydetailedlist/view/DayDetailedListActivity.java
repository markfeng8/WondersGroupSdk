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
import com.wondersgroup.android.jkcs_sdk.constants.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0005Entity;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.contract.DayDetailedListContract;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.presenter.DayDetailedListPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.DateUtils;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
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
    private DayDetailedListAdapter mDayDetailedListAdapter;
    private List<Cy0005Entity.DetailsBean> mDetails;
    private long mLastTime = System.currentTimeMillis();
    private String mOrgCode;
    private String mInHosId;
    private String mActivityTag;
    private long mMinMillis;
    private long mMaxMillis;

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
        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mInHosId = intent.getStringExtra(IntentExtra.IN_HOS_ID);
            mActivityTag = intent.getStringExtra(IntentExtra.ACTIVITY_TAG);
            mMinMillis = intent.getLongExtra(IntentExtra.MIN_MILLIS, 0L);
            mMaxMillis = intent.getLongExtra(IntentExtra.MAX_MILLIS, 0L);
            LogUtil.i(TAG, "mMinMillis===" + mMinMillis + ",mMaxMillis===" + mMaxMillis);
        }

        if ("InHospitalHomeActivity".equals(mActivityTag)) {
            tvToday.setText("今天");
        } else if ("InHospitalRecordActivity".equals(mActivityTag)) {
            tvToday.setText("后一天");
        }

        mLastTime = mMaxMillis;
        String currentDate = DateUtils.getDate(mMaxMillis);
        tvStartDate.setText(currentDate);
        requestCy0005(currentDate);
    }

    private void initListener() {
        /*
         * 设置点击时间选择器的点击事件
         */
        tvStartDate.setOnClickListener(v -> showDate());
        /*
         * 设置点击上一天的点击事件
         */
        tvBeforeDay.setOnClickListener(v -> getLastDayData());
        /*
         * 设置点击今天或者下一天的点击事件
         */
        tvToday.setOnClickListener(v -> dealWithTime());
    }

    private void getLastDayData() {
        // 1.先获取上一天的时间戳
        long lastTimestamp = mLastTime - 1000L * 60L * 60L * 24L;
        // 2.格式化 lastTimestamp 为字符串
        String lastDateStr = DateUtils.getDate(lastTimestamp);
        // 3.格式化最小时间戳为字符串
        String minDateStr = DateUtils.getDate(mMinMillis);

        // 4.用上一天的时间戳和 90 天之前的时间戳进行比较
        boolean compareResult1 = DateUtils.compareBefore(DateUtils.getDateFormat3(), lastDateStr, DateUtils.getBeforeDate(90));

        if (compareResult1) {
            WToastUtil.show("仅支持3个月内日清单记录查询！");
            return;
        }

        // 5.用上一天的时间戳和最小的时间戳进行比较
        boolean compareResult2 = DateUtils.compareBefore(DateUtils.getDateFormat3(), lastDateStr, minDateStr);

        if (compareResult2) {
            WToastUtil.show("已显示入院当天日清单!");
            return;
        }

        String lastDate = DateUtils.getLastDay(mLastTime);
        mLastTime -= 1000L * 60L * 60L * 24L;
        tvStartDate.setText(lastDate);
        requestCy0005(lastDate);
    }

    private void dealWithTime() {
        // 如果是从历史住院记录点击进去的需要判断不能超过出院时间
        if ("InHospitalRecordActivity".equals(mActivityTag)) {
            // 1.先获取下一天的时间戳
            long nextTimestamp = mLastTime + 1000L * 60L * 60L * 24L;
            // 2.格式化 nextTimestamp 为字符串
            String nextDateStr = DateUtils.getDate(nextTimestamp);
            // 3.格式化最大时间戳为字符串
            String maxDateStr = DateUtils.getDate(mMaxMillis);

            // 4.用上一天的时间戳和最大的时间戳进行比较
            boolean compareResult = DateUtils.compareAfter(DateUtils.getDateFormat3(), nextDateStr, maxDateStr);

            // 需要考虑时间相等的情况，相等也是返回 false 的
            if (compareResult) {
                WToastUtil.show("已显示入院当天日清单！");
                return;
            }
        }

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

            String date = DateUtils.getDate(mLastTime);
            tvStartDate.setText(date);
            requestCy0005(date);
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
        LogUtil.i(TAG, "showDate():mMinMillis===" + mMinMillis + ",mMaxMillis===" + mMaxMillis);
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId(getString(R.string.wonders_select_date_please))
                .setMinMilliseconds(mMinMillis)
                .setMaxMilliseconds(mMaxMillis)
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
            String date = DateUtils.getDate(milliseconds);
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
    public static void actionStart(Context context, String orgCode, String inHosId, String flag, long minMillis, long maxMillis) {
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
    public void showLoading(boolean show) {
        showLoadingView(show);
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
