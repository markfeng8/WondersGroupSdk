package com.wondersgroup.android.jkcs_sdk.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

/**
 * Created by x-sir on 2018/9/27 :)
 * Function:自定义正式结算完成时的公共布局
 */
public class PayResultLayout extends LinearLayout {

    private LinearLayout llSocialNum;
    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvHospitalName;
    private TextView tvBillDate;
    private TextView tvBillNo;

    public PayResultLayout(Context context) {
        this(context, null);
    }

    public PayResultLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayResultLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wonders_group_payment_result_common, this);
        llSocialNum = findViewById(R.id.llSocialNum);
        tvTreatName = findViewById(R.id.tvTreatName);
        tvSocialNum = findViewById(R.id.tvSocialNum);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvBillDate = findViewById(R.id.tvBillDate);
        tvBillNo = findViewById(R.id.tvBillNo);
    }

    public void setTreatName(String name) {
        if (!TextUtils.isEmpty(name)) {
            tvTreatName.setText(name);
        } else {
            tvTreatName.setVisibility(View.GONE);
        }
    }

    public void setSocialNum(String socialNum) {
        if (!TextUtils.isEmpty(socialNum)) {
            tvSocialNum.setText(socialNum);
        } else {
            // 如果是 null 则不显示社保卡号项
            llSocialNum.setVisibility(View.GONE);
        }
    }

    public void setHospitalName(String hospitalName) {
        if (!TextUtils.isEmpty(hospitalName)) {
            tvHospitalName.setText(hospitalName);
        } else {
            tvHospitalName.setVisibility(View.GONE);
        }
    }

    public void setBillDate(String billDate) {
        if (!TextUtils.isEmpty(billDate)) {
            tvBillDate.setText(billDate);
        } else {
            tvBillDate.setVisibility(View.GONE);
        }
    }

    public void setBillNo(String billNo) {
        if (!TextUtils.isEmpty(billNo)) {
            tvBillNo.setText(billNo);
        } else {
            tvBillNo.setVisibility(View.GONE);
        }
    }
}
