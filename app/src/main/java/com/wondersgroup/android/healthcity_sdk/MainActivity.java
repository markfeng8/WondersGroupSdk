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
import com.wondersgroup.android.jkcs_sdk.entity.UserBuilder;
import com.wondersgroup.android.jkcs_sdk.utils.WondersGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String[] NAME = {"吴振强", "陆晓明", "朱凯", "潘人伟", "杨旭成", "沈佳威", "赵岳寅", "钟瑜", "唐其儿", "邱晨飞", "钟央毛", "程华凤", "测试2", "严超"};
    private static final String[] ID_NUM = {"330501199008213715", "330502197207121415", "330501199005222018", "330502196310210630", "330102197703011512", "330501198709151313", "330681198610031570", "330501198112238539", "330501198908158673", "330501199001262215", "330511193603094218", "330523196701243028", "330501198804146213", "330501198804146213"};
    private static final String[] CARD_TYPE = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "2", "0", "0", "0", "0"};
    private static final String[] CARD_NUM = {"A05111650", "A0109403X", "A0486807X", "A00305253", "A00997691", "A02418702", "A05401164", "A03111112", "A05300523", "006953059", "000000000", "000000000", "000000000", "000000000"};
    /**
     * 证件类型 01 身份证
     */
    private static final String ID_TYPE = "01";
    /**
     * 手机号码
     */
    private static final String PHONE = "13588259873";
    /**
     * 家庭住址
     */
    private static final String ADDRESS = "ShangHai";

    private List<PersonBean> mPersonList = new ArrayList<>();

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
    @BindView(R.id.btnSelfPayHome)
    Button btnSelfPayHome;
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
    @BindView(R.id.tvMrQiu)
    TextView tvMrQiu;
    @BindView(R.id.tvMrZym)
    TextView tvMrZym;
    @BindView(R.id.tvMrCheng)
    TextView tvMrCheng;
    @BindView(R.id.tvMrTest2)
    TextView tvMrTest2;
    @BindView(R.id.tvMrYanChao)
    TextView tvMrYanChao;
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String version = AppInfoUtil.getVersionName(this);
        String versionName = "demo 版本：V" + version;
        tvVersion.setText(versionName);
        initPerson();
    }

    private void initPerson() {
        for (int i = 0; i < NAME.length; i++) {
            PersonBean personBean = new PersonBean();
            personBean.setName(NAME[i]);
            personBean.setPhone(PHONE);
            personBean.setIdType(ID_TYPE);
            personBean.setIdNum(ID_NUM[i]);
            personBean.setCardType(CARD_TYPE[i]);
            personBean.setCardNum(CARD_NUM[i]);
            personBean.setAddress(ADDRESS);
            mPersonList.add(personBean);
        }
    }

    @OnClick({R.id.tvMrWu, R.id.tvMrLu, R.id.tvMrZhu, R.id.tvMrPan, R.id.tvMrYang, R.id.tvMrShen,
            R.id.tvMrZhao, R.id.tvMrZhong, R.id.tvMrTang, R.id.tvMrQiu, R.id.tvMrZym, R.id.tvMrCheng,
            R.id.tvMrTest2, R.id.tvMrYanChao, R.id.btnAfterPayHome, R.id.btnSelfPayHome, R.id.btnInHospitalHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvMrWu:
                setPersonInfo(mPersonList.get(0));
                break;
            case R.id.tvMrLu:
                setPersonInfo(mPersonList.get(1));
                break;
            case R.id.tvMrZhu:
                setPersonInfo(mPersonList.get(2));
                break;
            case R.id.tvMrPan:
                setPersonInfo(mPersonList.get(3));
                break;
            case R.id.tvMrYang:
                setPersonInfo(mPersonList.get(4));
                break;
            case R.id.tvMrShen:
                setPersonInfo(mPersonList.get(5));
                break;
            case R.id.tvMrZhao:
                setPersonInfo(mPersonList.get(6));
                break;
            case R.id.tvMrZhong:
                setPersonInfo(mPersonList.get(7));
                break;
            case R.id.tvMrTang:
                setPersonInfo(mPersonList.get(8));
                break;
            case R.id.tvMrQiu:
                setPersonInfo(mPersonList.get(9));
                break;
            case R.id.tvMrZym:
                setPersonInfo(mPersonList.get(10));
                break;
            case R.id.tvMrCheng:
                setPersonInfo(mPersonList.get(11));
                break;
            case R.id.tvMrTest2:
                setPersonInfo(mPersonList.get(12));
                break;
            case R.id.tvMrYanChao:
                setPersonInfo(mPersonList.get(13));
                break;
            case R.id.btnAfterPayHome:
                startBusiness(0);
                break;
            case R.id.btnSelfPayHome:
                startBusiness(1);
                break;
            case R.id.btnInHospitalHome:
                startBusiness(2);
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
     * 《 跳转到医后付首页、自费卡、住院页面 》
     *
     * @param flag 业务标志 0 医后付 1 自费卡 2 住院
     *             注：flag 为 0 和 2 时 cardType 一定传 0(社保卡)，
     *             flag 为 1 时 cardType 传 2(自费卡)
     */
    private void startBusiness(int flag) {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String idType = etIdType.getText().toString().trim();
        String idNum = etIdNum.getText().toString().trim();
        String cardType = etCardType.getText().toString().trim();
        String cardNum = etCardNum.getText().toString().trim();
        String homeAddress = etHomeAddress.getText().toString().trim();

        // 设置需要传递的参数，所有参数都为必须！！！
        UserBuilder userBuilder = new UserBuilder()
                .setName(name) // 姓名
                .setPhone(phone) // 手机号
                .setIdType(idType) // 证件类型(01：身份证)
                .setIdNum(idNum) // 证件号码
                .setCardType(cardType) // 就诊卡类型(0：社保卡 2：自费卡)
                .setCardNum(cardNum) // 就诊卡号
                .setAddress(homeAddress); // 家庭地址

        WondersGroup.startBusiness(MainActivity.this, userBuilder, flag);
    }

}
