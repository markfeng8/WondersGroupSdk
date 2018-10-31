/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.view;

import android.content.Intent;
import android.os.Bundle;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.contract.SelfPayFeeContract;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.presenter.SelfPayFeePresenter;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/10/31 :)
 * Function:自费卡类型页面
 */
public class SelfPayFeeActivity extends MvpBaseActivity<SelfPayFeeContract.IView,
        SelfPayFeePresenter<SelfPayFeeContract.IView>> implements SelfPayFeeContract.IView {

    private static final String TAG = "SelfPayFeeActivity";
    private HashMap<String, String> mPassParamMap;

    @Override
    protected SelfPayFeePresenter<SelfPayFeeContract.IView> createPresenter() {
        return new SelfPayFeePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_self_pay_fee);
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    mPassParamMap = sMap.getMap();

                    // TODO: 2018/10/31
                }
            }
        }
    }
}
