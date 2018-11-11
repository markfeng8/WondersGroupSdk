package com.wondersgroup.android.healthcity_sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wondersgroup.android.healthcity_sdk.bean.PersonBean;
import com.wondersgroup.android.healthcity_sdk.utils.AppInfoUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WondersGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etIdType)
    EditText etIdType;
    @BindView(R.id.etIdNum)
    EditText etIdNum;
    @BindView(R.id.etCardType)
    EditText etCardType;
    @BindView(R.id.etCardNum)
    EditText etCardNum;
    @BindView(R.id.etHomeAddress)
    EditText etHomeAddress;
    @BindView(R.id.btnAfterPayHome)
    Button btnAfterPayHome;
    @BindView(R.id.btnInHospitalHome)
    Button btnInHospitalHome;
    @BindView(R.id.tvMrLu)
    TextView tvMrLu;
    @BindView(R.id.tvMrZhu)
    TextView tvMrZhu;
    @BindView(R.id.tvMrWu)
    TextView tvMrWu;
    @BindView(R.id.tvMrPan)
    TextView tvMrPan;
    @BindView(R.id.tvMrYang)
    TextView tvMrYang;
    @BindView(R.id.tvMrShen)
    TextView tvMrShen;
    @BindView(R.id.tvMrZhao)
    TextView tvMrZhao;
    @BindView(R.id.tvMrZhong)
    TextView tvMrZhong;
    @BindView(R.id.tvMrTang)
    TextView tvMrTang;
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    private PersonBean mPersonWu;
    private PersonBean mPersonLu;
    private PersonBean mPersonZhu;
    private PersonBean mPersonPan;
    private PersonBean mPersonYang;
    private PersonBean mPersonShen;
    private PersonBean mPersonZhao;
    private PersonBean mPersonZhong;
    private PersonBean mPersonTang;
    private String mName;
    private String mPhone;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;
    private String mHomeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        // 隐藏软键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String version = AppInfoUtil.getVersionName(this);
        String versionName = "demo 版本：V" + version;
        tvVersion.setText(versionName);

        /*吴振强 的就是专门测试登录注册这些的*/
        mPersonWu = new PersonBean();
        mPersonWu.setName("吴振强");
        mPersonWu.setPhone("13588259873");
        mPersonWu.setIdType("01");
        mPersonWu.setIdNum("330501199008213715");
        mPersonWu.setCardType("0");
        mPersonWu.setCardNum("A05111650");
        mPersonWu.setAddress("ShangHai");

        /*第一医院 陆晓明 主要测试账单欠费这些*/
        mPersonLu = new PersonBean();
        mPersonLu.setName("陆晓明");
        mPersonLu.setPhone("13588259873");
        mPersonLu.setIdType("01");
        mPersonLu.setIdNum("330502197207121415");
        mPersonLu.setCardType("0");
        mPersonLu.setCardNum("A0109403X");
        mPersonLu.setAddress("ShangHai");

        /*中心医院 朱凯 主要测试账单等信息*/
        mPersonZhu = new PersonBean();
        mPersonZhu.setName("朱凯");
        mPersonZhu.setPhone("13588259873");
        mPersonZhu.setIdType("01");
        mPersonZhu.setIdNum("330501199005222018");
        mPersonZhu.setCardType("0");
        mPersonZhu.setCardNum("A0486807X");
        mPersonZhu.setAddress("ShangHai");

        /*中心医院 潘人伟 主要测试账单等信息*/
        mPersonPan = new PersonBean();
        mPersonPan.setName("潘人伟");
        mPersonPan.setPhone("13588259873");
        mPersonPan.setIdType("01");
        mPersonPan.setIdNum("330502196310210630");
        mPersonPan.setCardType("0");
        mPersonPan.setCardNum("A00305253");
        mPersonPan.setAddress("ShangHai");

        /*中心医院 杨旭成 主要测试账单等信息*/
        mPersonYang = new PersonBean();
        mPersonYang.setName("杨旭成");
        mPersonYang.setPhone("13588259873");
        mPersonYang.setIdType("01");
        mPersonYang.setIdNum("330102197703011512");
        mPersonYang.setCardType("0");
        mPersonYang.setCardNum("A00997691");
        mPersonYang.setAddress("ShangHai");

        /*中心医院 沈佳威 主要测试账单等信息*/
        mPersonShen = new PersonBean();
        mPersonShen.setName("沈佳威");
        mPersonShen.setPhone("13588259873");
        mPersonShen.setIdType("01");
        mPersonShen.setIdNum("330501198709151313");
        mPersonShen.setCardType("0");
        mPersonShen.setCardNum("A02418702");
        mPersonShen.setAddress("ShangHai");

        /*中心医院 赵岳寅 主要测试账单等信息*/
        mPersonZhao = new PersonBean();
        mPersonZhao.setName("赵岳寅");
        mPersonZhao.setPhone("13588259873");
        mPersonZhao.setIdType("01");
        mPersonZhao.setIdNum("330681198610031570");
        mPersonZhao.setCardType("0");
        mPersonZhao.setCardNum("A05401164");
        mPersonZhao.setAddress("ShangHai");

        /*中心医院 钟瑜 主要测试账单等信息*/
        mPersonZhong = new PersonBean();
        mPersonZhong.setName("钟瑜");
        mPersonZhong.setPhone("13588259873");
        mPersonZhong.setIdType("01");
        mPersonZhong.setIdNum("330501198112238539");
        mPersonZhong.setCardType("0");
        mPersonZhong.setCardNum("A03111112");
        mPersonZhong.setAddress("ShangHai");

        /*唐其儿*/
        mPersonTang = new PersonBean();
        mPersonTang.setName("唐其儿");
        mPersonTang.setPhone("13588259873");
        mPersonTang.setIdType("01");
        mPersonTang.setIdNum("330501198908158673");
        mPersonTang.setCardType("0");
        mPersonTang.setCardNum("A05300523");
        mPersonTang.setAddress("ShangHai");
    }

    @OnClick({R.id.btnAfterPayHome, R.id.tvMrWu, R.id.tvMrLu, R.id.tvMrZhu, R.id.tvMrPan,
            R.id.tvMrYang, R.id.tvMrShen, R.id.tvMrZhao, R.id.tvMrZhong, R.id.tvMrTang,
            R.id.btnInHospitalHome})
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
            case R.id.tvMrPan:
                setPersonInfo(mPersonPan);
                break;
            case R.id.tvMrYang:
                setPersonInfo(mPersonYang);
                break;
            case R.id.tvMrShen:
                setPersonInfo(mPersonShen);
                break;
            case R.id.tvMrZhao:
                setPersonInfo(mPersonZhao);
                break;
            case R.id.tvMrZhong:
                setPersonInfo(mPersonZhong);
                break;
            case R.id.tvMrTang:
                setPersonInfo(mPersonTang);
                break;
            case R.id.btnInHospitalHome:
                startInHospitalHomePage();
                break;
        }
    }

    private void setPersonInfo(PersonBean personBean) {
        etName.setText(personBean.getName());
        etPhone.setText(personBean.getPhone());
        etIdType.setText(personBean.getIdType());
        etIdNum.setText(personBean.getIdNum());
        etCardType.setText(personBean.getCardType());
        etCardNum.setText(personBean.getCardNum());
        etHomeAddress.setText(personBean.getAddress());
    }

    /**
     * 《 跳转到医后付首页 》
     */
    private void startAfterPayHomePage() {
        getEditTextValues();

        /*
         * 按顺序依次将所有需要传递的参数传入方法中(所有参数为必须！！！)
         *
         * @param context      上下文
         * @param mName        姓名
         * @param mPhone       手机号
         * @param mIdType      证件类型(01：身份证)
         * @param mIdNum       证件号码
         * @param mCardType    就诊卡类型(0：社保卡 2：自费卡)
         * @param mCardNum     就诊卡号
         * @param mHomeAddress 家庭地址
         */
        WondersGroup.startAfterPayHome(MainActivity.this,
                mName, mPhone, mIdType, mIdNum, mCardType, mCardNum, mHomeAddress);
    }

    /**
     * 《 跳转到住院服务首页 》
     */
    private void startInHospitalHomePage() {
        getEditTextValues();

        /*
         * 按顺序依次将所有需要传递的参数传入方法中(所有参数为必须！！！)
         *
         * @param context      上下文
         * @param mName        姓名
         * @param mPhone       手机号
         * @param mIdType      证件类型(01：身份证)
         * @param mIdNum       证件号码
         * @param mCardType    就诊卡类型(0：社保卡 2：自费卡)
         * @param mCardNum     就诊卡号
         * @param mHomeAddress 家庭地址
         */
        WondersGroup.startInHospitalHome(MainActivity.this,
                mName, mPhone, mIdType, mIdNum, mCardType, mCardNum, mHomeAddress);
    }

    private void getEditTextValues() {
        mName = etName.getText().toString().trim();
        mPhone = etPhone.getText().toString().trim();
        mIdType = etIdType.getText().toString().trim();
        mIdNum = etIdNum.getText().toString().trim();
        mCardType = etCardType.getText().toString().trim();
        mCardNum = etCardNum.getText().toString().trim();
        mHomeAddress = etHomeAddress.getText().toString().trim();
    }

}
