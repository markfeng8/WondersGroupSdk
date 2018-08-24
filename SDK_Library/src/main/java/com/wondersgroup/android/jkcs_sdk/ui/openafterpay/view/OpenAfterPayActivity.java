package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.presenter.AfterPayPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WonderToastUtil;

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
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO: 2018/8/24 set button enable
            }
        });
    }

    private void sendOpenRequest() {
        String phone = etPhone.getText().toString().trim();
        String smsCode = etSmsCode.getText().toString().trim();

        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(smsCode)) {
            String name = SpUtil.getInstance().getString(SpKey.NAME, "");
            String idNo = SpUtil.getInstance().getString(SpKey.IC_NUM, "");
            String cardNo = SpUtil.getInstance().getString(SpKey.SOCIAL_NUM, "");

            HashMap<String, String> param = new HashMap<>();
            param.put(MapKey.NAME, name);
            param.put(MapKey.ID_NO, idNo);
            param.put(MapKey.CARD_NO, cardNo);
            param.put(MapKey.PHONE, phone);
            param.put(MapKey.IDEN_CODE, smsCode);
            mPresenter.openAfterPay(param);
        } else {
            WonderToastUtil.show("手机号或验证为不能为空！");
        }
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }
}
