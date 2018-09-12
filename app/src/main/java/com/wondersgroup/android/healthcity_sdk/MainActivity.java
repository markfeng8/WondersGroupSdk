package com.wondersgroup.android.healthcity_sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.healthcity_sdk.bean.PersonBean;
import com.wondersgroup.android.healthcity_sdk.utils.AppInfoUtil;
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
    @BindView(R.id.tvMrLu)
    TextView tvMrLu;
    @BindView(R.id.tvMrZhu)
    TextView tvMrZhu;
    @BindView(R.id.tvMrWu)
    LinearLayout tvMrWu;
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    private PersonBean mPersonWu;
    private PersonBean mPersonLu;
    private PersonBean mPersonZhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String version = AppInfoUtil.getVersionName(this);
        String versionName = "demo 版本：V" + version;
        tvVersion.setText(versionName);

        /*吴振强的就是专门测试登录注册这些的*/
        mPersonWu = new PersonBean();
        mPersonWu.setName("吴振强");
        mPersonWu.setPhone("13588259873");
        mPersonWu.setIcNum("330501199008213715");
        mPersonWu.setSocialNum("A05111650");
        mPersonWu.setAddress("ShangHai");

        /*第一医院陆晓明主要测试账单欠费这些*/
        mPersonLu = new PersonBean();
        mPersonLu.setName("陆晓明");
        mPersonLu.setPhone("13588259873");
        mPersonLu.setIcNum("330502197207121415");
        mPersonLu.setSocialNum("A0109403X");
        mPersonLu.setAddress("ShangHai");

        /*中心医院朱凯主要测试账单等信息*/
        mPersonZhu = new PersonBean();
        mPersonZhu.setName("朱凯");
        mPersonZhu.setPhone("13588259873");
        mPersonZhu.setIcNum("330501199005222018");
        mPersonZhu.setSocialNum("A0486807X");
        mPersonZhu.setAddress("ShangHai");

        // 中心医院 "沈桂珠" "330502196702211421" "A04811583"
    }

    @OnClick({R.id.btnAfterPayHome, R.id.tvMrWu, R.id.tvMrLu, R.id.tvMrZhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAfterPayHome:
                startAfterPayHomePage();
                break;
            case R.id.tvMrWu:
                setPersonInfo(mPersonWu);
                break;
            case R.id.tvMrLu:
                setPersonInfo(mPersonLu);
                break;
            case R.id.tvMrZhu:
                setPersonInfo(mPersonZhu);
                break;
        }
    }

    private void setPersonInfo(PersonBean personBean) {
        String name = personBean.getName();
        String phone = personBean.getPhone();
        String icNum = personBean.getIcNum();
        String socialNum = personBean.getSocialNum();
        String address = personBean.getAddress();

        etName.setText(name);
        etPhone.setText(phone);
        etIcNum.setText(icNum);
        etSocialNum.setText(socialNum);
        etHomeAddress.setText(address);
    }

    private void startAfterPayHomePage() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String icNum = etIcNum.getText().toString().trim();
        String socialNum = etSocialNum.getText().toString().trim();
        String homeAddress = etHomeAddress.getText().toString().trim();

        /*
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
