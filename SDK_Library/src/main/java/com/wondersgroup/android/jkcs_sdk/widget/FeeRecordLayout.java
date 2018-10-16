package com.wondersgroup.android.jkcs_sdk.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/9/19 :)
 * Function:自定义缴费记录 Item 的展开布局
 */
public class FeeRecordLayout extends LinearLayout {

    private TextView tvFeeName;
    private TextView tvTimestamp;
    private TextView tvFeeNum;
    private static final String TAG = "FeeRecordLayout";

    public FeeRecordLayout(Context context) {
        this(context, null);
    }

    public FeeRecordLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeeRecordLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wonders_group_pay_record_hide_item, this);
        tvFeeName = findViewById(R.id.tvFeeName);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        tvFeeNum = findViewById(R.id.tvFeeNum);
    }

    public void setFeeName(String feeName) {
        if (!TextUtils.isEmpty(feeName)) {
            tvFeeName.setText(feeName);
        } else {
            LogUtil.e(TAG, "feeName is null set failed!");
        }
    }

    public void setTimestamp(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)) {
            tvTimestamp.setText(timestamp);
        } else {
            LogUtil.e(TAG, "timestamp is null set failed!");
        }
    }

    public void setFeeNum(String feeNum) {
        if (!TextUtils.isEmpty(feeNum)) {
            tvFeeNum.setText(feeNum);
        } else {
            LogUtil.e(TAG, "feeNum is null set failed!");
        }
    }
}
