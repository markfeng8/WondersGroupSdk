/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.view.DayDetailedListActivity;
import com.wondersgroup.android.jkcs_sdk.ui.eleinvoice.EleInvoiceActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.contract.InHospitalRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.presenter.InHospitalRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtils;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:历史住院记录页面
 */
public class InHospitalRecordActivity extends MvpBaseActivity<InHospitalRecordContract.IView,
        InHospitalRecordPresenter<InHospitalRecordContract.IView>> implements InHospitalRecordContract.IView {

    private static final String TAG = "InHospitalRecordActivity";
    private TextView tvName;
    private TextView tvHospital;
    private TextView tvIdNum;
    private TextView tvInHosId;
    private TextView tvInHosArea;
    private TextView tvInHosDate;
    private TextView tvLeaveHosDate;
    private TextView tvInHosType;
    private TextView tvInHosFeeTotal;
    private TextView tvInHosDetail;
    private TextView tvEleInvoice;
    private String mPayPlatTradeNo;
    private String mOrgCode;
    private String mInHosId;
    private String mLeaveHosDate;
    private String mInHosDate;

    @Override
    protected InHospitalRecordPresenter<InHospitalRecordContract.IView> createPresenter() {
        return new InHospitalRecordPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital_record);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        tvInHosDetail.setOnClickListener(v -> {
            if (!TimeUtils.isOver90Days(mLeaveHosDate)) {
                DayDetailedListActivity.actionStart(InHospitalRecordActivity.this, mOrgCode,
                        mInHosId, TAG, TimeUtils.getMinMillis(mInHosDate), TimeUtils.convertToMillis(TimeUtils.SDF4, mLeaveHosDate));
            } else {
                WToastUtil.show("仅支持3个月内日清单记录查询！");
            }
        });
        tvEleInvoice.setOnClickListener(v -> EleInvoiceActivity.actionStart(InHospitalRecordActivity.this,
                TextUtils.isEmpty(mPayPlatTradeNo) ? "0" : mPayPlatTradeNo));
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Cy0001Entity.DetailsBean detailsBean = (Cy0001Entity.DetailsBean) bundle.get(IntentExtra.SERIALIZABLE_DETAILS_BEAN);
                if (detailsBean != null) {
                    setViewsData(detailsBean);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setViewsData(Cy0001Entity.DetailsBean detailsBean) {
        mOrgCode = detailsBean.getOrg_code();
        mInHosId = detailsBean.getJzlsh();
        mInHosDate = detailsBean.getRysj().substring(0, 10);
        mLeaveHosDate = detailsBean.getCysj().substring(0, 10);
        tvName.setText(detailsBean.getName());
        tvHospital.setText(detailsBean.getOrgName());
        tvIdNum.setText(detailsBean.getId_no());
        tvInHosId.setText(mInHosId);
        tvInHosArea.setText(detailsBean.getKsmc());
        tvInHosDate.setText(mInHosDate);
        tvLeaveHosDate.setText(mLeaveHosDate);
        tvInHosType.setText("0".equals(detailsBean.getCard_type()) ? "医保" : "自费");
        tvInHosFeeTotal.setText(detailsBean.getFee_total() + "元");
        mPayPlatTradeNo = detailsBean.getPayPlatTradeNo();
    }

    private void findViews() {
        tvName = findViewById(R.id.tvName);
        tvHospital = findViewById(R.id.tvHospital);
        tvIdNum = findViewById(R.id.tvIdNum);
        tvInHosId = findViewById(R.id.tvInHosId);
        tvInHosArea = findViewById(R.id.tvInHosArea);
        tvInHosDate = findViewById(R.id.tvInHosDate);
        tvLeaveHosDate = findViewById(R.id.tvLeaveHosDate);
        tvInHosType = findViewById(R.id.tvInHosType);
        tvInHosFeeTotal = findViewById(R.id.tvInHosFeeTotal);
        tvInHosDetail = findViewById(R.id.tvInHosDetail);
        tvEleInvoice = findViewById(R.id.tvEleInvoice);
    }

    public static void actionStart(Context context, Cy0001Entity.DetailsBean detailsBean) {
        if (context != null) {
            Intent intent = new Intent(context, InHospitalRecordActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentExtra.SERIALIZABLE_DETAILS_BEAN, detailsBean);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
