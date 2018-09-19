package com.wondersgroup.android.jkcs_sdk.ui.payrecord.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseFragment;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.contract.FeeRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.presenter.FeeRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

import widget.DateScrollerDialog;
import widget.data.Type;
import widget.listener.OnDateSetListener;

/**
 * Created by x-sir on 2018/8/9 :)
 * Function:未完成订单页面
 */
public class UnfinishedOrderFragment extends MvpBaseFragment<FeeRecordContract.IView,
        FeeRecordPresenter<FeeRecordContract.IView>> implements FeeRecordContract.IView {

    private TextView tvStartDate;
    private TextView tvEndDate;
    private View fragmentView;
    private LoadingView mLoading;
    private RecyclerView recyclerView;
    private long mLastTime = System.currentTimeMillis(); // 上次设置的时间
    private boolean isStartTime = true;

    @Override
    protected FeeRecordPresenter<FeeRecordContract.IView> createPresenter() {
        return new FeeRecordPresenter<>();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.wonders_group_fragment_order_record, null);
        fragmentView = view.findViewById(R.id.fragmentView);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initSomeData();
        initListener();
    }

    private void initSomeData() {
        mLoading = new LoadingView.Builder(mContext)
                .setDropView(fragmentView)
                .build();
    }

    private void initListener() {
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = true;
                showDate();
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = false;
                showDate();
            }
        });
    }

    /**
     * 显示日期(配置最大、最小时间伐值)
     */
    public void showDate() {
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId(getString(R.string.wonders_select_date_please))
                //.setMinMilliseconds(dateScrollMin)
                //.setMaxMilliseconds(dateScrollMax)
                //.setCurMilliseconds(mLastTime)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getFragmentManager(), "year_month_day");
            }
        }
    }

    // 数据的回调
    private OnDateSetListener mOnDateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DateScrollerDialog timePickerView, long milliseconds) {
            mLastTime = milliseconds;
            //String text = TimeUtil.getAuthTime(milliseconds);
            if (isStartTime) {

            } else {

            }
        }
    };

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

    @Override
    public void getFeeDetails(String payPlatTradeNo, int position) {
        super.getFeeDetails(payPlatTradeNo, position);
        mPresenter.getFeeDetail(payPlatTradeNo);
    }

    @Override
    public void onFeeRecordResult(FeeRecordEntity entity) {

    }

    @Override
    public void onFeeDetailResult(FeeBillEntity entity) {

    }
}
