package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

// 医后付首页
public class AfterPayHomeActivity extends AppCompatActivity {

    private TextView tvUpdateInfo;
    private TextView tvPayRecord;
    private TextView tvPayToast;
    private TextView tvHospitalName;
    private TextView tvSelectHospital;
    private ListView listView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private ImageView ivBackBtn;
    private TextView tvTitleName;
    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvAfterPayState;
    private TextView tvOpenMobilePay;
    private TextView tvToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_pay_home);
        findViews();
        initData();
    }

    private void initData() {
        tvPayToast.setText(Html.fromHtml(getString(R.string.wonders_mark_text)));
    }

    private void findViews() {
        tvUpdateInfo = (TextView) findViewById(R.id.tvUpdateInfo);
        tvPayRecord = (TextView) findViewById(R.id.tvPayRecord);
        tvPayToast = (TextView) findViewById(R.id.tvPayToast);
        tvHospitalName = (TextView) findViewById(R.id.tvHospitalName);
        tvSelectHospital = (TextView) findViewById(R.id.tvSelectHospital);
        listView = (ListView) findViewById(R.id.listView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
        ivBackBtn = (ImageView) findViewById(R.id.ivBackBtn);
        tvTitleName = (TextView) findViewById(R.id.tvTitleName);
        tvTreatName = (TextView) findViewById(R.id.tvTreatName);
        tvSocialNum = (TextView) findViewById(R.id.tvSocialNum);
        tvAfterPayState = (TextView) findViewById(R.id.tvAfterPayState);
        tvOpenMobilePay = (TextView) findViewById(R.id.tvOpenMobilePay);
        tvToPay = (TextView) findViewById(R.id.tvToPay);
    }

}
