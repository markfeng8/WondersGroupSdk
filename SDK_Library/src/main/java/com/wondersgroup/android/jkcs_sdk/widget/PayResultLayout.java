package com.wondersgroup.android.jkcs_sdk.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

/**
 * Created by x-sir on 2018/9/27 :)
 * Function:自定义正式结算完成时的公共布局
 */
public class PayResultLayout extends LinearLayout {

    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvHospitalName;
    private TextView tvBillDate;

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
        tvTreatName = findViewById(R.id.tvTreatName);
        tvSocialNum = findViewById(R.id.tvSocialNum);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvBillDate = findViewById(R.id.tvBillDate);
    }

    public void setTreatName(String name) {
        if (!TextUtils.isEmpty(name)) {
            tvTreatName.setText(name);
        }
    }

    public void setSocialNum(String socialNum) {
        if (!TextUtils.isEmpty(socialNum)) {
            tvSocialNum.setText(socialNum);
        }
    }

    public void setHospitalName(String hospitalName) {
        if (!TextUtils.isEmpty(hospitalName)) {
            tvHospitalName.setText(hospitalName);
        }
    }

    public void setBillDate(String billDate) {
        if (!TextUtils.isEmpty(billDate)) {
            tvBillDate.setText(billDate);
        }
    }
}
