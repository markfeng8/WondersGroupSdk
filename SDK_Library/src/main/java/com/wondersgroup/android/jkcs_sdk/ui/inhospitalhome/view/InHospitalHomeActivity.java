/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.CityBean;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalBean;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.view.DayDetailedListActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.view.InHospitalHistory;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.presenter.InHospitalHomePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.view.LeaveHospitalActivity;
import com.wondersgroup.android.jkcs_sdk.utils.DateUtils;
import com.wondersgroup.android.jkcs_sdk.utils.EpSoftUtils;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.selecthospital.CityConfig;
import com.wondersgroup.android.jkcs_sdk.widget.selecthospital.HospitalPickerView;
import com.wondersgroup.android.jkcs_sdk.widget.selecthospital.OnCityItemClickListener;

import java.util.List;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院服务首页面
 */
public class InHospitalHomeActivity extends MvpBaseActivity<InHospitalHomeContract.IView,
        InHospitalHomePresenter<InHospitalHomeContract.IView>> implements InHospitalHomeContract.IView {

    private static final String TAG = "InHospitalHomeActivity";
    private TextView tvName;
    private TextView tvIdNum;
    private TextView tvMobPayState;
    private TextView tvHospitalName;
    private TextView tvPrepayFee;
    private TextView tvRechargeRecord;
    private TextView tvDayDetail;
    private TextView tvLeaveHos;
    private TextView tvInHosRecord;
    private TextView tvNoDetail;
    private TextView tvInHosId;
    private TextView tvInHosArea;
    private TextView tvInHosDate;
    private TextView tvInHosPrepayFee;
    private TextView tvInHosFeeTotal;
    private TextView tvPaymentType;
    private Group viewGroup;
    /**
     * 选择器默认的医院
     */
    private String mOrgName = "湖州市中心医院";
    private String mOrgCode;
    /**
     * 选择器默认的地区
     */
    private String mAreaName = "湖州市";
    private String mInHosId;
    private String mInHosArea;
    private String mInHosDate;
    private LoadingView mLoading;
    private Cy0001Entity mCy0001Entity;

    private HospitalPickerView mCityPickerView = new HospitalPickerView();

    /**
     * 住院状态：00 在院 01 预出院 10 已出院
     */
    private String mInState = "";

    private void requestCY0001() {
        if (!TextUtils.isEmpty(mOrgCode)) {
            mPresenter.requestCy0001(mOrgCode, OrgConfig.IN_STATE0);
        }
    }

    @Override
    protected InHospitalHomePresenter<InHospitalHomeContract.IView> createPresenter() {
        return new InHospitalHomePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital_home);
        findViews();
        initListener();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestCY0001();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        tvName.setText(name);
        String start = idNum.substring(0, 10);
        String end = idNum.substring(idNum.length() - 4, idNum.length());
        tvIdNum.setText(start + "****" + end);
    }

    private void findViews() {
        tvName = findViewById(R.id.tvName);
        tvIdNum = findViewById(R.id.tvIdNum);
        tvMobPayState = findViewById(R.id.tvMobPayState);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvPrepayFee = findViewById(R.id.tvPrepayFee);
        tvRechargeRecord = findViewById(R.id.tvRechargeRecord);
        tvDayDetail = findViewById(R.id.tvDayDetail);
        tvLeaveHos = findViewById(R.id.tvLeaveHos);
        tvInHosRecord = findViewById(R.id.tvInHosRecord);
        viewGroup = findViewById(R.id.viewGroup);
        tvNoDetail = findViewById(R.id.tvNoDetail);
        tvInHosId = findViewById(R.id.tvInHosId);
        tvInHosArea = findViewById(R.id.tvInHosArea);
        tvInHosDate = findViewById(R.id.tvInHosDate);
        tvInHosPrepayFee = findViewById(R.id.tvInHosPrepayFee);
        tvInHosFeeTotal = findViewById(R.id.tvInHosFeeTotal);
        tvPaymentType = findViewById(R.id.tvPaymentType);
    }

    private void initListener() {
        // 去开通医保移动支付
        tvMobPayState.setOnClickListener(view -> openYiBaoMobPay());
        // 选择医院
        tvHospitalName.setOnClickListener(view -> mPresenter.getHospitalList("V1.1", "02"));
        // 预交金充值
        tvPrepayFee.setOnClickListener(view -> {
            comingSoon();
        });
        // 充值记录
        tvRechargeRecord.setOnClickListener(view -> {
            //RechargeRecordActivity.actionStart(InHospitalHomeActivity.this);
            comingSoon();
        });
        // 日清单查询
        tvDayDetail.setOnClickListener(view -> findDayDetails());
        // 出院结算
        tvLeaveHos.setOnClickListener(view -> leaveHospitalSettle());
        // 历史住院记录
        tvInHosRecord.setOnClickListener(view -> {
            InHospitalHistory.actionStart(InHospitalHomeActivity.this, mOrgCode, mOrgName);
        });
    }

    private void comingSoon() {
        WToastUtil.show("敬请期待！");

        // 在院状态才能进行预交金充值
//        if ("00".equals(mInState)) {
//            PrepayFeeRechargeActivity.actionStart(InHospitalHomeActivity.this);
//        } else {
//            WToastUtil.show("您当前不是住院状态，不能充值！");
//        }
    }

    /**
     * 弹出选择器
     */
    private void showWheelDialog(String json) {
        // 预先加载仿iOS滚轮实现的全部数据
        mCityPickerView.init(this, json);

        CityConfig cityConfig = new CityConfig.Builder()
                .defaultCity(mAreaName)
                .defaultHospital(mOrgName)
                .build();

        mCityPickerView.setConfig(cityConfig);

        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(CityBean cityBean, HospitalBean hospitalBean) {
                mAreaName = cityBean.getArea_name();
                mOrgCode = hospitalBean.getOrg_code();
                mOrgName = hospitalBean.getOrg_name();
                tvHospitalName.setText(mOrgName);
                requestCY0001();
            }

            @Override
            public void onCancel() {
                LogUtil.i(TAG, "onCancel()");
            }
        });

        mCityPickerView.showCityPicker();
    }

    private void findDayDetails() {
        if (TextUtils.isEmpty(mOrgName)) {
            WToastUtil.show("请先选择医院！");
            return;
        }

        if (mCy0001Entity == null) {
            WToastUtil.show("当前无住院信息，请通过住院记录页面查询！");
            return;
        }
        // 在院或预出院状态才可以查询日清单
        long minMillis = DateUtils.getMinMillis(mInHosDate);
        long currentMillis = System.currentTimeMillis();
        LogUtil.i(TAG, "minMillis===" + minMillis + ",currentMillis===" + currentMillis);
        // minMillis===1541952000000,currentMillis===1547537526483
        if ("00".equals(mInState) || "01".equals(mInState)) {
            DayDetailedListActivity.actionStart(InHospitalHomeActivity.this, mOrgCode, mInHosId,
                    TAG, minMillis, currentMillis);
        }
    }

    private void leaveHospitalSettle() {
        if (mCy0001Entity == null) {
            WToastUtil.show("暂无住院信息！");
            return;
        }

        // 如果是医保卡，需要判断医保移动支付状态是否开通
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
            if (!"01".equals(mobPayStatus)) {
                WToastUtil.show("您未开通医保移动支付，请先开通！");
                return;
            }
        }

        LogUtil.i(TAG, "mInState===" + mInState);
        if ("01".equals(mInState) && viewGroup.getVisibility() == View.VISIBLE) {
            LeaveHospitalActivity.actionStart(InHospitalHomeActivity.this, mOrgCode, mOrgName, mInHosId, mInHosDate, mInHosArea);
        } else {
            WToastUtil.show("您当前不是预出院状态！");
        }
    }

    /**
     * 开通医保移动支付
     */
    private void openYiBaoMobPay() {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
//            AuthCall.businessProcess(InHospitalHomeActivity.this,
//                    MakeArgsFactory.getOpenArgs(), WToastUtil::show);
        } else {
            WToastUtil.show("网络连接错误，请检查您的网络连接！");
        }
    }

    @Override
    public void onHospitalListResult(HospitalEntity body) {
        if (body != null) {
            List<HospitalEntity.DetailsBeanX> details = body.getDetails();
            if (details != null && details.size() > 0) {
                String json = new Gson().toJson(details);
                LogUtil.i(TAG, "json===" + json);
                showWheelDialog(json);
            }
        } else {
            LogUtil.w(TAG, "onHospitalListResult() -> body is null!");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCy0001Result(Cy0001Entity entity) {
        this.mCy0001Entity = entity;
        if (entity != null) {
            List<Cy0001Entity.DetailsBean> details = entity.getDetails();
            if (details != null && details.size() > 0) {
                setViewVisibility(true);
                Cy0001Entity.DetailsBean detailsBean = details.get(0);
                if (detailsBean != null) {
                    mInHosId = detailsBean.getJzlsh();
                    tvInHosId.setText(mInHosId);
                    mInHosArea = detailsBean.getKsmc();
                    tvInHosArea.setText(mInHosArea);
                    mInHosDate = detailsBean.getRysj().substring(0, 10);
                    tvInHosDate.setText(mInHosDate);
                    tvInHosPrepayFee.setText(detailsBean.getYjkze() + "元");
                    tvInHosFeeTotal.setText(detailsBean.getFee_total() + "元");
                    mInState = detailsBean.getIn_state();
                    String cardType = detailsBean.getCard_type();
                    String cardNo = detailsBean.getCard_no();
                    SpUtil.getInstance().save(SpKey.CARD_TYPE, cardType);
                    SpUtil.getInstance().save(SpKey.CARD_NUM, cardNo);

                    tvPaymentType.setVisibility(View.VISIBLE);
                    if ("0".equals(cardType)) {
                        tvPaymentType.setText("医保");
                        // 如果是医保才去查询，自费不需要查询
                        queryYiBaoOpenStatus();
                    } else if ("2".equals(cardType)) {
                        tvPaymentType.setText("自费");
                    }

                    SpUtil.getInstance().save(SpKey.JZLSH, mInHosId);
                }
            }
        } else {
            setViewVisibility(false);
            tvPaymentType.setVisibility(View.GONE);
            tvMobPayState.setVisibility(View.GONE);
        }
    }

    private void queryYiBaoOpenStatus() {
        EpSoftUtils.queryYiBaoOpenStatus(this, status -> {
            if ("01".equals(status)) { // 已开通
                // 上传开通状态
                mPresenter.uploadMobilePayState();
            }

            if ("00".equals(status)) { // 00 未签约
                setMobilePayState(true);
            } else if ("01".equals(status)) { // 01 已签约
                setMobilePayState(false);
            }
        });
    }

    private void setViewVisibility(boolean hasDetail) {
        viewGroup.setVisibility(hasDetail ? View.VISIBLE : View.GONE);
        tvNoDetail.setVisibility(hasDetail ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置医保移动付状态
     */
    private void setMobilePayState(boolean enable) {
        tvMobPayState.setVisibility(View.VISIBLE);
        if (enable) {
            tvMobPayState.setText(getString(R.string.wonders_to_open_mobile_pay));
            tvMobPayState.setEnabled(true);
            tvMobPayState.setCompoundDrawables(null, null, null, null);
        } else {
            tvMobPayState.setText(getString(R.string.wonders_open_mobile_pay));
            tvMobPayState.setEnabled(false);
        }
    }

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.showLoadingDialog();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismissLoadingDialog();
        }
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, InHospitalHomeActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoading != null) {
            mLoading.dispose();
        }
    }
}
