package com.wondersgroup.android.jkcs_sdk.ui.payrecord.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseFragment;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.entity.CombineFeeRecord;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.FeeRecordAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.contract.FeeRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.presenter.FeeRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.view.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SettleUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x-sir on 2018/8/9 :)
 * Function:未完成订单页面
 */
public class UnfinishedOrderFragment extends MvpBaseFragment<FeeRecordContract.IView,
        FeeRecordPresenter<FeeRecordContract.IView>> implements FeeRecordContract.IView {

    private View fragmentView;
    private LoadingView mLoading;
    private RecyclerView recyclerView;
    private String mEndDate;
    private String mStartDate;
    private int mPosition = -1;
    private String mPageNumber = "1"; // 页数
    private String mPageSize = "100"; // 每页的条数（后续可能需要做下拉刷新&加载更多功能）
    private FeeRecordAdapter mAdapter;
    private List<FeeRecordEntity.DetailsBean> mDetails;
    private List<CombineFeeRecord> mItemList = new ArrayList<>();
    private boolean mIsOfficialPay;
    private static final String TAG = "UnfinishedOrderFragment";

    @Override
    protected FeeRecordPresenter<FeeRecordContract.IView> createPresenter() {
        return new FeeRecordPresenter<>();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.wonders_group_fragment_unfinish_order, null);
        fragmentView = view.findViewById(R.id.fragmentView);
        recyclerView = view.findViewById(R.id.recyclerView);
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

        mStartDate = "2018-01-01";
        mEndDate = TimeUtil.getCurrentDate();
    }

    private void initListener() {

    }

    private void getFeeState() {
        mPresenter.getFeeRecord(OrgConfig.FEE_STATE00, mStartDate,
                mEndDate, mPageNumber, mPageSize); // 00 未完成订单
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

    @Override
    public void getFeeDetails(String payPlatTradeNo, int position, boolean isOfficialPay) {
        super.getFeeDetails(payPlatTradeNo, position, isOfficialPay);
        this.mPosition = position;
        this.mIsOfficialPay = isOfficialPay;
        if (!TextUtils.isEmpty(payPlatTradeNo)) {
            mPresenter.getFeeDetail(payPlatTradeNo);
        }
    }

    @Override
    public void onFeeRecordResult(FeeRecordEntity entity) {
        if (entity != null) {
            mDetails = entity.getDetails();
            if (mDetails != null && mDetails.size() > 0) {
                LogUtil.e(TAG, "查询到" + mDetails.size() + "条【未完成订单】记录！");
            } else {
                LogUtil.e(TAG, "没有查询到【未完成订单】记录！");
            }

            // 获取到数据和没有获取到数据都需要刷新适配器（刷新掉适配器中的旧数据）
            combineListData();
            setAdapter();
        }
    }

    private void combineListData() {
        for (int i = 0; i < mDetails.size(); i++) {
            CombineFeeRecord record = new CombineFeeRecord();
            record.setRecordDetail(mDetails.get(i));
            mItemList.add(record);
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new FeeRecordAdapter(mContext, UnfinishedOrderFragment.this, mItemList, true);
            recyclerView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            mAdapter.setDetails(mItemList);
        }
    }

    @Override
    public void onFeeDetailResult(FeeBillEntity entity) {
        if (entity != null) {
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            // 如果是发起正式结算时请求的 yd0009，则进行组装数据
            if (mIsOfficialPay) {
                FeeRecordEntity.DetailsBean detailsBean = mDetails.get(mPosition);
                String orgCode = detailsBean.getOrg_code();
                String orgName = detailsBean.getOrg_name();
                String feeTotal = detailsBean.getFee_total();
                String feeCashTotal = detailsBean.getFee_cash_total();
                String feeYbTotal = detailsBean.getFee_yb_total();
                /*
                 * 传递参数过去
                 * 此时还是未完成订单的状态，所以 isComplete 传 false
                 */
                PersonalPayActivity.actionStart(mContext, false, true, orgName, orgCode, feeTotal, feeCashTotal,
                        feeYbTotal, SettleUtil.getOfficialSettleParam(details));
            } else {
                // 如果是展开详情，直接刷新适配器即可
                mItemList.get(mPosition).setFeeDetail(details);
                setAdapter();
            }
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
