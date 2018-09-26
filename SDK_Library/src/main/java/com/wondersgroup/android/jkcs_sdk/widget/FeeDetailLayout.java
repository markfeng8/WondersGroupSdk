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
 * Created by x-sir on 2018/9/19 :)
 * Function:自定义缴费详情 Item 的展开布局
 */
public class FeeDetailLayout extends LinearLayout {

    private TextView tvFeeName;
    private TextView tvFeeNum;

    public FeeDetailLayout(Context context) {
        this(context, null);
    }

    public FeeDetailLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeeDetailLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wonders_group_pay_detail_hide_item, this);
        tvFeeName = findViewById(R.id.tvFeeName);
        tvFeeNum = findViewById(R.id.tvFeeNum);
    }

    public void setFeeName(String feeName) {
        if (!TextUtils.isEmpty(feeName)) {
            tvFeeName.setText(feeName);
        }
    }

    public void setFeeNum(String feeNum) {
        if (!TextUtils.isEmpty(feeNum)) {
            tvFeeNum.setText(feeNum);
        }
    }
}
