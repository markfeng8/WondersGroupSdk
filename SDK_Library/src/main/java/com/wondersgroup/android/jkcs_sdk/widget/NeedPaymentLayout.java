package com.wondersgroup.android.jkcs_sdk.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

/**
 * Created by x-sir on 2018/9/2 :)
 * Function:自定义医后付首页中间欠费部分的布局
 */
public class NeedPaymentLayout extends LinearLayout {

    private TextView tvPayment;

    public NeedPaymentLayout(Context context) {
        this(context, null);
    }

    public NeedPaymentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NeedPaymentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wonders_group_need_payment, this);
        tvPayment = findViewById(R.id.tvPayment);
    }

    private void initData() {

    }
}
