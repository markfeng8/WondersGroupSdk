/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.healthcity_sdk;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wondersgroup.android.healthcity_sdk.adapter.PersonAdapter;
import com.wondersgroup.android.healthcity_sdk.bean.PersonBean;
import com.wondersgroup.android.healthcity_sdk.utils.AppInfoUtil;
import com.wondersgroup.android.sdk.WondersImp;
import com.wondersgroup.android.sdk.api.WondersGroup;
import com.wondersgroup.android.sdk.entity.UserBuilder;
import com.wondersgroup.android.sdk.entity.WondersExternParams;
import com.wondersgroup.android.sdk.ui.familydoctor.FamilyDoctorActivity;
import com.xsir.pgyerappupdate.library.PgyerApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String[] NAME = {"王焱",
            "吴振强", "陆晓明", "朱凯", "潘人伟", "杨旭成", "沈佳威", "赵岳寅", "钟瑜", "唐其儿", "邱晨飞",
            "钟央毛", "程华凤", "测试2", "严超", "闵丽明", "测试新", "居国芳", "潘品凡", "王虹", "张利峰",
            "秦丽娅", "钱天宝", "胡欣慧", "李强", "庞万春", "宋华倩", "姚玉麟", "吴育英", "陈龙", "米婷",
            "闵子鑫", "张鹤立"
    };
    private static final String[] ID_NUM = {"330902198102080019",
            "330501199008213715", "330502197207121415", "330501199005222018", "330502196310210630",
            "330102197703011512", "330501198709151313", "330681198610031570", "330501198112238539",
            "330501198908158673", "330501199001262215", "330511193603094218", "330523196701243028",
            "330501198804146213", "330501198804146213", "330511194908061024", "111111111111111111",
            "330502196211040023", "330502195910071828", "330502193503190425", "330501198801042216",
            "330502196303220427", "33051119470513641X", "330501198505220620", "330501197801294419",
            "330501199207010814", "330501199002222629", "330501198306273017", "330501197704033022",
            "330501198708050836", "510821199207201368", "330511193902087835", "330902198810271035"
    };
    private static final String[] CARD_TYPE = {"0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "2", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"
    };
    private static final String[] CARD_NUM = {"A03094471",
            "A05111650", "A0109403X", "A0486807X", "A00305253", "A00997691", "A02418702", "A05401164",
            "A03111112", "A05300523", "006953059", "000000000", "000000000", "000000000", "000000000",
            "000000000", "000000000", "000000000", "000000000", "000000000", "A03747583", "A01411728",
            "C00249132", "A04036791", "A0420429X", "A05111650", "A03905051", "A0366004X", "A04329949",
            "A50668677", "009081572", "A04289342", "A31262321"
    };
    /**
     * 证件类型 01 身份证
     */
    private static final String ID_TYPE = "01";
    /**
     * 手机号码
     * 13588259873
     */
    private static final String PHONE = "13587092069";
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
    @BindView(R.id.btnHealthCard)
    Button btnHealthCard;
    @BindView(R.id.btnFamilyDoctor)
    Button btnFamilyDoctor;
    @BindView(R.id.btnElectronicCard)
    Button btnElectronicCard;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        initData();
        setAdapter();
    }

    private void initData() {
        String version = AppInfoUtil.getVersionName(this);
        String versionName = "demo 版本：V" + version;
        tvVersion.setText(versionName);
        initPerson();
        PgyerApi.checkUpdate(this);
    }

    private void setAdapter() {
        PersonAdapter personAdapter = new PersonAdapter(mPersonList);
        recyclerView.setAdapter(personAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        personAdapter.setOnItemClickListener((adapter, view, position) -> setPersonInfo(mPersonList.get(position)));
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

    @OnClick({R.id.btnAfterPayHome, R.id.btnSelfPayHome, R.id.btnInHospitalHome, R.id.btnHealthCard,
            R.id.btnFamilyDoctor, R.id.btnElectronicCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAfterPayHome:
                startBusiness(0);
                break;
            case R.id.btnSelfPayHome:
                startBusiness(1);
                break;
            case R.id.btnInHospitalHome:
                startBusiness(2);
                break;
            case R.id.btnHealthCard:
                startBusiness(4);
                break;
            case R.id.btnFamilyDoctor:
                String idNum = etIdNum.getText().toString().trim();
                if (!TextUtils.isEmpty(idNum)) {
                    showAlertDialog(idNum);
                } else {
                    Toast.makeText(MainActivity.this, "身份证号码不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnElectronicCard:
                startBusiness(3);
                break;
            default:
                break;
        }
    }

    private void showAlertDialog(final String idNum) {
        // 家庭医生新的请求地址
        // http://115.238.228.2:8000/jtys/?idCard=330511196307211620
        final EditText editText = new EditText(this);
        editText.setText("http://115.238.228.2:8000");
        new AlertDialog.Builder(this)
                .setTitle("设置服务器地址")
                .setCancelable(false)
                .setView(editText)
                .setPositiveButton("确定", (dialog, which) -> {
                    String host = editText.getText().toString();
                    String url = host + "/jtys/?idCard=" + idNum;
                    FamilyDoctorActivity.actionStart(MainActivity.this, url);
                })
                .setNegativeButton("取消", null)
                .show();
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
     *             <p>
     *             cardType = 0（社保卡），就诊卡号如果没有，默认传000000000
     *             cardType = 2（自费卡），就诊卡号可不传
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
                // 姓名
                .setName(name)
                // 手机号
                .setPhone(phone)
                // 证件类型(01：身份证)
                .setIdType(idType)
                // 证件号码
                .setIdNum(idNum)
                // 就诊卡类型(0：社保卡 2：自费卡)
                .setCardType(cardType)
                // 就诊卡号
                .setCardNum(cardNum)
                // 家庭地址
                .setAddress(homeAddress);

        WondersGroup.startBusiness(MainActivity.this, userBuilder, flag);
    }

    void setParams() {
        /**
         *
         * 渠道编号
         * 渠道信息
         */
        WondersImp.setWondersExternParamsImp(new WondersImp.WondersParamsImp() {
            @Override
            public WondersExternParams getExternParams() {
                WondersExternParams params = new WondersExternParams();
                params.setChannelNo("");
                params.setQDCODE("");
                return params;
            }
        });


        /**
         *
         * sign
         */
        WondersImp.setWondersExternParamsImp(new WondersImp.WondersParamsImp() {
            @Override
            public WondersExternParams getExternParams() {
                WondersExternParams params = new WondersExternParams();
                params.setSign("");
                return params;
            }
        });
    }
}

