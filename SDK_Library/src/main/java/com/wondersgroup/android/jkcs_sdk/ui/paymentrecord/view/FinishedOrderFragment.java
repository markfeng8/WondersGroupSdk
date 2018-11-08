package com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseFragment;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.entity.CombineFeeRecord;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.adapter.FeeRecordAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.contract.FeeRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.presenter.FeeRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.DateScrollerDialog;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.data.Type;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x-sir on 2018/8/9 :)
 * Function:已完成订单页面
 */
public class FinishedOrderFragment extends MvpBaseFragment<FeeRecordContract.IView,
        FeeRecordPresenter<FeeRecordContract.IView>> implements FeeRecordContract.IView {

    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvQuery;
    private View fragmentView;
    private RecyclerView recyclerView;
    private long mLastTime = System.currentTimeMillis(); // 上次设置的时间
    private boolean isStartTime = true;
    private String mStartDate;
    private String mEndDate;
    private String mPageNumber = "1"; // 页数
    private String mPageSize = "100"; // 每页的条数
    private FeeRecordAdapter mAdapter;
    private LoadingView mLoading;
    private List<FeeRecordEntity.DetailsBean> mDetails;
    private int mPosition = -1;
    private List<CombineFeeRecord> mItemList = new ArrayList<>();
    private static final String TAG = "FinishedOrderFragment";

    @Override
    protected FeeRecordPresenter<FeeRecordContract.IView> createPresenter() {
        return new FeeRecordPresenter<>();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.wonders_group_fragment_finish_order, null);
        fragmentView = view.findViewById(R.id.fragmentView);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        tvQuery = view.findViewById(R.id.tvQuery);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initSomeData();
        getFeeState();
        initListener();
    }

    private void initSomeData() {
        mLoading = new LoadingView.Builder(mContext)
                .setDropView(fragmentView)
                .build();

        mStartDate = TimeUtil.getBefore30Date();
        mEndDate = TimeUtil.getCurrentDate();
        tvStartDate.setText(mStartDate);
        tvEndDate.setText(mEndDate);
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
                .setMinMilliseconds(TimeUtil.getScrollMinTime())
                .setMaxMilliseconds(System.currentTimeMillis())
                .setCurMilliseconds(mLastTime)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getFragmentManager(), "year_month_day");
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
            }

            // 获取到数据和没有获取到数据都需要刷新适配器（刷新掉适配器中的旧数据）
            combineListData();
            setAdapter();
        }
    }

    /**
     * 组装数据
     */
    private void combineListData() {
        // 清除旧集合中的数据
        if (mItemList != null && mItemList.size() > 0) {
            mItemList.clear();
        }
        for (int i = 0; i < mDetails.size(); i++) {
            CombineFeeRecord record = new CombineFeeRecord();
            record.setRecordDetail(mDetails.get(i));
            mItemList.add(record);
        }
    }

    @Override
    public void onFeeDetailResult(FeeBillEntity entity) {
        if (entity != null) {
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            mItemList.get(mPosition).setFeeDetail(details);
            setAdapter();
        }
    }

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new FeeRecordAdapter(mContext, FinishedOrderFragment.this, mItemList, false);
            recyclerView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            mAdapter.setDetails(mItemList);
        }
    }

    @Override
    public void getFeeDetails(String payPlatTradeNo, int position, boolean isOfficialPay) {
        super.getFeeDetails(payPlatTradeNo, position, isOfficialPay);
        this.mPosition = position;
        if (!TextUtils.isEmpty(payPlatTradeNo)) {
            mPresenter.getFeeDetail(payPlatTradeNo);
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
