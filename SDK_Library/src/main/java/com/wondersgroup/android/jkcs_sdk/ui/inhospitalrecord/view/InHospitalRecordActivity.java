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
import android.view.View;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.contract.InHospitalRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.presenter.InHospitalRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
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
        tvInHosDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WToastUtil.show("暂未开通！");
            }
        });
        tvEleInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WToastUtil.show("暂未开通！");
                //EleInvoiceActivity.actionStart(InHospitalRecordActivity.this, "123");
            }
        });
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
        tvName.setText(detailsBean.getName());
        tvHospital.setText(detailsBean.getOrgName());
        tvIdNum.setText(detailsBean.getId_no());
        tvInHosId.setText(detailsBean.getJzlsh());
        tvInHosArea.setText(detailsBean.getKsmc());
        tvInHosDate.setText(detailsBean.getRysj().substring(0, 10));
        tvLeaveHosDate.setText(detailsBean.getCysj().substring(0, 10));
        tvInHosType.setText("0".equals(detailsBean.getCard_type()) ? "医保" : "自费");
        tvInHosFeeTotal.setText(detailsBean.getFee_total() + "元");
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
