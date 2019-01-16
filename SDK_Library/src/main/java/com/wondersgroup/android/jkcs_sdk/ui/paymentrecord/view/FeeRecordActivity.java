package com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.PaymentFeeRecordAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.contract.FeeRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.presenter.FeeRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.view.RecordDetailActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtils;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.DateScrollerDialog;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.data.Type;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.listener.OnDateSetListener;

import java.util.List;

/**
 * Created by x-sir on 2018/8/9 :)
 * Function:支付记录页面
 */
public class FeeRecordActivity extends MvpBaseActivity<FeeRecordContract.IView,
        FeeRecordPresenter<FeeRecordContract.IView>> implements FeeRecordContract.IView {

    private static final String TAG = "FeeRecordActivity";
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvQuery;
    private RecyclerView recyclerView;
    private long mLastTime = System.currentTimeMillis(); // 上次设置的时间
    private boolean isStartTime = true;
    private String mStartDate;
    private String mEndDate;
    /**
     * 页数
     */
    private String mPageNumber = "1";
    /**
     * 每页的条数
     */
    private String mPageSize = "100";
    private LoadingView mLoading;
    private List<FeeRecordEntity.DetailsBean> mDetails;
    private PaymentFeeRecordAdapter mPaymentFeeRecordAdapter;

    @Override
    protected FeeRecordPresenter<FeeRecordContract.IView> createPresenter() {
        return new FeeRecordPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_pay_record);
        findViews();
        initSomeData();
        getFeeState();
        initListener();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvQuery = findViewById(R.id.tvQuery);
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, FeeRecordActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

    private void initSomeData() {
        mLoading = new LoadingView.Builder(this)
                .build();

        mStartDate = TimeUtils.getBefore30Date();
        mEndDate = TimeUtils.getCurrentDate();
        tvStartDate.setText(mStartDate);
        tvEndDate.setText(mEndDate);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getFeeState() {
        mPresenter.getFeeRecord(OrgConfig.FEE_STATE01, mStartDate,
                mEndDate, mPageNumber, mPageSize); // 01 已完成订单
    }

    private void initListener() {
        tvStartDate.setOnClickListener(v -> {
            isStartTime = true;
            showDate();
        });
        tvEndDate.setOnClickListener(v -> {
            isStartTime = false;
            showDate();
        });
        tvQuery.setOnClickListener(v -> getFeeState());
    }

    /**
     * 显示日期(配置最大、最小时间伐值)
     */
    public void showDate() {
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId(getString(R.string.wonders_select_date_please))
                .setMinMilliseconds(TimeUtils.getScrollMinTime())
                .setMaxMilliseconds(System.currentTimeMillis())
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
            String date = TimeUtils.getDate(milliseconds);
            if (isStartTime) {
                mStartDate = date;
                tvStartDate.setText(date);
            } else {
                mEndDate = date;
                tvEndDate.setText(date);
            }
        }
    };

    @Override
    public void onFeeRecordResult(FeeRecordEntity entity) {
        if (entity != null) {
            mDetails = entity.getDetails();
            if (mDetails != null && mDetails.size() > 0) {
                LogUtil.e(TAG, "查询到" + mDetails.size() + "条【已完成订单】记录！");
            } else {
                LogUtil.e(TAG, "没有查询到【已完成订单】记录！");
                WToastUtil.show("没有查询到【已完成订单】记录！");
            }

            setAdapter();
        } else {
            refreshAdapter();
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

    private void setAdapter() {
        mPaymentFeeRecordAdapter = new PaymentFeeRecordAdapter(R.layout.wonders_group_fee_record_item, mDetails);
        View notDataView = getLayoutInflater().inflate(R.layout.wonders_group_empty_view, (ViewGroup) recyclerView.getParent(), false);
        mPaymentFeeRecordAdapter.setEmptyView(notDataView);
        recyclerView.setAdapter(mPaymentFeeRecordAdapter);
        mPaymentFeeRecordAdapter.setOnItemClickListener((adapter, view, position) -> {
            FeeRecordEntity.DetailsBean detailsBean = mDetails.get(position);
            RecordDetailActivity.actionStart(FeeRecordActivity.this, detailsBean.getOrg_code(), detailsBean.getOrg_name(), detailsBean.getShop_order_time(),
                    detailsBean.getPayplat_tradno(), detailsBean.getFee_total(), detailsBean.getFee_cash_total(), detailsBean.getFee_yb_total());
        });
    }

    private void refreshAdapter() {
        // 1.清除旧数据
        if (mDetails.size() > 0) {
            mDetails.clear();
        }
        // 2.刷新适配器
        if (mPaymentFeeRecordAdapter != null) {
            mPaymentFeeRecordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoading != null) {
            mLoading.dispose();
        }
    }
}
