package com.wondersgroup.android.jkcs_sdk.widget;

import android.annotation.SuppressLint;
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
 * Function:自定义付款 Item 的布局
 */
public class PayItemLayout extends LinearLayout {

    private TextView tvFeeName;
    private TextView tvFeeNum;

    public PayItemLayout(Context context) {
        this(context, null);
    }

    public PayItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wonders_group_pay_item_layout, this);
        tvFeeName = findViewById(R.id.tvFeeName);
        tvFeeNum = findViewById(R.id.tvFeeNum);
    }

    public void setFeeName(String name) {
        if (!TextUtils.isEmpty(name)) {
            tvFeeName.setText(name);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setFeeNum(String num) {
        if (!TextUtils.isEmpty(num)) {
            tvFeeNum.setText("￥" + num);
        }
    }
}
