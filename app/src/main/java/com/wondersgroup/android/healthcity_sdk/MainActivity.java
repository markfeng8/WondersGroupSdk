package com.wondersgroup.android.healthcity_sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.utils.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etIcNum)
    EditText etIcNum;
    @BindView(R.id.etSocialNum)
    EditText etSocialNum;
    @BindView(R.id.etHomeAddress)
    EditText etHomeAddress;
    @BindView(R.id.btnAfterPayHome)
    Button btnAfterPayHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnAfterPayHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAfterPayHome:
                startAfterPayHomePage();
                break;
        }
    }

    private void startAfterPayHomePage() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String icNum = etIcNum.getText().toString().trim();
        String socialNum = etSocialNum.getText().toString().trim();
        String homeAddress = etHomeAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(MainActivity.this, "请输入姓名！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(MainActivity.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(icNum)) {
            Toast.makeText(MainActivity.this, "请输入身份证号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(socialNum)) {
            Toast.makeText(MainActivity.this, "请输入社保卡号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(homeAddress)) {
            Toast.makeText(MainActivity.this, "请输入家庭地址！", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * 《 跳转到医后付模块首页 》
         * 按顺序依次将所有需要传递的参数传入方法中(所有参数为必须！！！)
         *
         * @param context     上下文
         * @param name        姓名
         * @param phone       手机号
         * @param icNum       身份证号
         * @param socialNum   社保卡号
         * @param homeAddress 家庭地址
         */
        ActivityUtil.startAfterPayHome(MainActivity.this,
                name, phone, icNum, socialNum, homeAddress);
    }

}
