package com.wondersgroup.android.healthcity_sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.utils.ActivityUtil;

import java.util.HashMap;

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

        /*
         * 将所有需要传递的参数（所有参数必须传）放入 HashMap 中，
         * 不要修改 Key，只修改 value 即可。
         */
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.NAME, name); // 姓名
        map.put(MapKey.PHONE, phone); // 手机号
        map.put(MapKey.ID_NO, icNum); // 身份证号码
        map.put(MapKey.CARD_NO, socialNum); // 社保卡号
        map.put(MapKey.HOME_ADDRESS, homeAddress); // 家庭地址
        ActivityUtil.startAfterPayHome(MainActivity.this, map);
    }

}
