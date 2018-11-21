/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.recorddetail.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.RecordDetailsAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.CombineDetailsBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.ui.qrcodepage.QrCodeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.contract.RecordDetailContract;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.presenter.RecordDetailPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.PayItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:订单记录详情页面
 */
public class RecordDetailActivity extends MvpBaseActivity<RecordDetailContract.IView,
        RecordDetailPresenter<RecordDetailContract.IView>> implements RecordDetailContract.IView {

    private static final String TAG = "RecordDetailActivity";
    private TextView tvHospitalName;
    private TextView tvFeeDate;
    private TextView tvTradeNo;
    private LinearLayout llQrCode;
    private PayItemLayout plTotalMoney;
    private PayItemLayout plPersonalPay;
    private PayItemLayout plYiBaoPay;
    private RecyclerView recyclerView;
    private LoadingView mLoading;
    private View activityView;
    private int mClickItemPos = -1; // 记录点击的 Item 的位置
    private RecordDetailsAdapter mAdapter;
    private List<FeeBillEntity.DetailsBean> details;
    private List<CombineDetailsBean> mItemList = new ArrayList<>(); // 组合 Item 数据的集合
    private String mOrgCode;
    private String payPlatTradeNo;
    private String mOrgName;

    @Override
    protected RecordDetailPresenter<RecordDetailContract.IView> createPresenter() {
        return new RecordDetailPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_record_detail);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        llQrCode.setOnClickListener(view -> QrCodeActivity.actionStart(RecordDetailActivity.this,
                payPlatTradeNo, mOrgName));
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            String shopOrderTime = intent.getStringExtra(IntentExtra.SHOP_ORDER_TIME);
            payPlatTradeNo = intent.getStringExtra(IntentExtra.PAY_PLAT_TRADE_NO);
            String feeTotal = intent.getStringExtra(IntentExtra.FEE_TOTAL);
            String feeCashTotal = intent.getStringExtra(IntentExtra.FEE_CASH_TOTAL);
            String feeYbTotal = intent.getStringExtra(IntentExtra.FEE_YB_TOTAL);

            tvHospitalName.setText(mOrgName);
            tvFeeDate.setText("订单日期：" + shopOrderTime);
            tvTradeNo.setText("订单号：" + payPlatTradeNo);

            plTotalMoney.setFeeName("总计金额：");
            plTotalMoney.setFeeNum(feeTotal);
            plPersonalPay.setFeeName("现金部分：");
            plPersonalPay.setFeeNum(feeCashTotal);
            plYiBaoPay.setFeeName("医保部分：");
            plYiBaoPay.setFeeNum(feeYbTotal);

            // 获取账单记录详情
            mPresenter.requestYd0009(payPlatTradeNo);
        }
    }

    private void findViews() {
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvFeeDate = findViewById(R.id.tvFeeDate);
        tvTradeNo = findViewById(R.id.tvTradeNo);
        llQrCode = findViewById(R.id.llQrCode);
        plTotalMoney = findViewById(R.id.plTotalMoney);
        plPersonalPay = findViewById(R.id.plPersonalPay);
        plYiBaoPay = findViewById(R.id.plYiBaoPay);
        recyclerView = findViewById(R.id.recyclerView);
        activityView = findViewById(R.id.activityView);
    }

    @Override
    public void onYd0009Result(FeeBillEntity entity) {
        if (entity != null) {
            details = entity.getDetails();
            // 转换为组合数据
            getCombineListData(details);
            setAdapter();
        }
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new RecordDetailsAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    /**
     * 获取 List 的组合数据
     */
    private void getCombineListData(List<FeeBillEntity.DetailsBean> details) {
        for (int i = 0; i < details.size(); i++) {
            CombineDetailsBean bean = new CombineDetailsBean();
            bean.setDefaultDetails(details.get(i));
            mItemList.add(bean);
        }
    }

    /**
     * 订单明细列表结果回调
     */
    @Override
    public void onOrderDetailsResult(OrderDetailsEntity entity) {
        if (entity != null) {
            List<OrderDetailsEntity.DetailsBean> details = entity.getDetails();
            if (details.size() > 0) {
                // List 数据从 1 开始，需要减去头部的位置 1
                mItemList.get(mClickItemPos).setOpenDetails(details);
                refreshAdapter();
            }
        }
    }

    private void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.setItemList(mItemList);
        }
    }

    /**
     * 获取账单明细
     */
    public void getOrderDetails(String hisOrderNo, int position) {
        mClickItemPos = position;
        mPresenter.getOrderDetails(hisOrderNo, mOrgCode);
    }

    public static void actionStart(Context context, String orgCode, String orgName, String shopOrderTime,
                                   String payPlatTradeNo, String feeTotal, String feeCashTotal, String feeYbTotal) {
        if (context != null) {
            Intent intent = new Intent(context, RecordDetailActivity.class);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            intent.putExtra(IntentExtra.SHOP_ORDER_TIME, shopOrderTime);
            intent.putExtra(IntentExtra.PAY_PLAT_TRADE_NO, payPlatTradeNo);
            intent.putExtra(IntentExtra.FEE_TOTAL, feeTotal);
            intent.putExtra(IntentExtra.FEE_CASH_TOTAL, feeCashTotal);
            intent.putExtra(IntentExtra.FEE_YB_TOTAL, feeYbTotal);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoading != null) {
            mLoading.dispose();
        }
    }
}
