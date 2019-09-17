/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.healthcity_sdk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wondersgroup.android.healthcity_sdk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class HealthCardActivity extends AppCompatActivity {

    private static final String TAG = "HealthCardActivity";
    //private static final String URL = "http://115.238.228.2:8000/hcbmp/management/h5/index?appId=5a2d3137f400494dacd73ceaf6e332a3&cipherText=";
    private static final String URL = "http://115.238.228.2:8000/hcbmp/management/h5/index?appId=050b1e9c8dd34d478518a9eafffa99ba&cipherText=";
    /**
     * 公钥截取前面16位作为报文加密密钥 e815d21106a59ac5e1fb305ededaf9cb
     */
    //private static final String PUBLIC_KEY = "e815d21106a59ac5";
    /**
     * 公钥截取前面16位作为报文加密密钥 dd80ec7b658a4ea660ae9bc7315fb227
     */
    private static final String PUBLIC_KEY = "dd80ec7b658a4ea6";

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etIdNum)
    EditText etIdNum;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.btnCommit)
    Button btnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_card);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCommit)
    public void onViewClicked() {
        String name = etName.getText().toString();
        String idNum = etIdNum.getText().toString();
        String phone = etPhone.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(HealthCardActivity.this, "请输入姓名！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(idNum)) {
            Toast.makeText(HealthCardActivity.this, "请输入身份证号！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(HealthCardActivity.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
            return;
        }

        String requestUrl = getRequestUrl(name, idNum, phone);
        Log.i(TAG, "requestUrl===" + requestUrl);
        WebViewActivity.actionStart(this, requestUrl);
    }

    private String getRequestUrl(String name, String idNum, String phone) {
        return URL + getEncryptionResult(name, idNum, phone);
    }

    private String getEncryptionResult(String name, String idNum, String phone) {
        String content = getParameters(name, idNum, phone);
        Log.i(TAG, "content===" + content);
        SymmetricCrypto sm4 = SmUtil.sm4(PUBLIC_KEY.getBytes());
        return sm4.encryptHex(content);
    }

    private String getParameters(String name, String idNum, String phone) {
        return "openId=" + idNum + "&xm=" + name + "&sfzh=" + idNum + "&sjhm=" + phone + "&timestamp=" + getTimestamp();
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }
}