package com.wondersgroup.android.jkcs_sdk.ui.personalpay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

// 个人账户支付页面
public class PersonalPayActivity extends AppCompatActivity {

    private TextView tvTongChouPay;
    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvBillDate;
    private TextView tvTotalPay;
    private TextView tvTitleName;
    private Button btnConfirmPay;
    private ImageView ivBackBtn;
    private RadioGroup rgPayType;
    private RadioButton rbAlipay;
    private RadioButton rbWeChatPay;
    private RadioButton rbUnionPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_personal_pay);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_personal_pay));
        rbAlipay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_union_pay)));
    }

    private void findViews() {
        tvTongChouPay = (TextView) findViewById(R.id.tvTongChouPay);
        tvTreatName = (TextView) findViewById(R.id.tvTreatName);
        tvSocialNum = (TextView) findViewById(R.id.tvSocialNum);
        tvBillDate = (TextView) findViewById(R.id.tvBillDate);
        tvTotalPay = (TextView) findViewById(R.id.tvTotalPay);
        btnConfirmPay = (Button) findViewById(R.id.btnConfirmPay);
        ivBackBtn = (ImageView) findViewById(R.id.ivBackBtn);
        tvTitleName = (TextView) findViewById(R.id.tvTitleName);
        rgPayType = (RadioGroup) findViewById(R.id.rgPayType);
        rbAlipay = (RadioButton) findViewById(R.id.rbAlipay);
        rbWeChatPay = (RadioButton) findViewById(R.id.rbWeChatPay);
        rbUnionPay = (RadioButton) findViewById(R.id.rbUnionPay);
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalPayActivity.this.finish();
            }
        });
        btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
