package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.presenter.AfterPayPresenter;

import java.util.HashMap;

// 开通医后付页面
public class OpenAfterPayActivity extends MvpBaseActivity<AfterPayContract.IView,
        AfterPayPresenter<AfterPayContract.IView>> implements AfterPayContract.IView {

    private ImageView ivBackBtn;
    private TextView tvTitleName;
    private EditText etPhone;
    private EditText etSmsCode;
    private Button btnOpen;
    private TextView btnGetSmsCode;
    private ToggleButton toggleButton;
    private SerializableHashMap sMap;

    @Override
    protected AfterPayPresenter<AfterPayContract.IView> createPresenter() {
        return new AfterPayPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_open_afterpay);
        initViews();
        initData();
        initListener();
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_text_open_after_paying));
        getIntentData();
    }

    private void initViews() {
        ivBackBtn = findViewById(R.id.ivBackBtn);
        tvTitleName = findViewById(R.id.tvTitleName);
        etPhone = findViewById(R.id.etPhone);
        etSmsCode = findViewById(R.id.etSmsCode);
        btnGetSmsCode = findViewById(R.id.btnGetSmsCode);
        toggleButton = findViewById(R.id.toggleButton);
        btnOpen = findViewById(R.id.btnOpen);
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(v -> OpenAfterPayActivity.this.finish());
        btnGetSmsCode.setOnClickListener(v -> mPresenter.sendSmsCode());
        btnOpen.setOnClickListener(v -> sendOpenRequest());
    }

    private void sendOpenRequest() {
        if (sMap != null) {
            HashMap<String, String> map = sMap.getMap();
            String name = map.get(MapKey.NAME);
            String idNo = map.get(MapKey.ID_NO);
            String cardNo = map.get(MapKey.CARD_NO);
            String phone = etPhone.getText().toString();
            String smsCode = etSmsCode.getText().toString();

            HashMap<String, String> param = new HashMap<>();
            param.put(MapKey.NAME, name);
            param.put(MapKey.ID_NO, idNo);
            param.put(MapKey.CARD_NO, cardNo);
            param.put(MapKey.PHONE, phone);
            param.put(MapKey.IDEN_CODE, smsCode);
            mPresenter.openAfterPay(param);
        }
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
            }
        }
    }
}
