package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

// 缴费详情页面
public class PaymentDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // 使用分类型的 RecyclerView 来实现
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private TextView tvTitleName;
    private ImageView ivBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_pay_detail));
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentDetailsActivity.this.finish();
            }
        });
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
        ivBackBtn = findViewById(R.id.ivBackBtn);
        tvTitleName = findViewById(R.id.tvTitleName);
    }
}
