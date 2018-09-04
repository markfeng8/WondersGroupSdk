package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

// 缴费详情页面
public class PaymentDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // 使用分类型的 RecyclerView 来实现
    private TextView tvMoneyNum;
    private TextView tvPayMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_payment_details);
        findViews();
        initData();
        initListener();
    }

    private void initData() {

    }

    private void initListener() {

    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
    }
}
