/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.selfpayfee.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.adapter.SelfPayFeeAdapter;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.CityBean;
import com.wondersgroup.android.sdk.entity.FeeBillDetailsBean;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.HospitalBean;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.entity.SelfPayHeaderBean;
import com.wondersgroup.android.sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.sdk.ui.selfpayfee.contract.SelfPayFeeContract;
import com.wondersgroup.android.sdk.ui.selfpayfee.presenter.SelfPayFeePresenter;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.widget.selecthospital.CityConfig;
import com.wondersgroup.android.sdk.widget.selecthospital.HospitalPickerView;
import com.wondersgroup.android.sdk.widget.selecthospital.OnCityItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by x-sir on 2018/10/31 :)
 * Function:?????????????????????
 */
public class SelfPayFeeActivity extends MvpBaseActivity<SelfPayFeeContract.IView,
        SelfPayFeePresenter<SelfPayFeeContract.IView>> implements SelfPayFeeContract.IView {

    private static final String TAG = "SelfPayFeeActivity";
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private LinearLayout llNeedPay;
    private RecyclerView recyclerView;
    private SelfPayFeeAdapter mSelfPayFeeAdapter;
    private List<Object> mItemList = new ArrayList<>();
    /**
     * ????????????????????????
     */
    private String mOrgName = "?????????????????????";
    private String mOrgCode;
    /**
     * ????????????????????????
     */
    private String mAreaName = "?????????";
    private SelfPayHeaderBean mSelfPayHeaderBean;
    private HospitalPickerView mCityPickerView;

    @Override
    protected SelfPayFeePresenter<SelfPayFeeContract.IView> createPresenter() {
        return new SelfPayFeePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_self_pay_fee);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        mCityPickerView = new HospitalPickerView(this);
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mSelfPayHeaderBean = new SelfPayHeaderBean();
        mSelfPayHeaderBean.setName(name);
        mSelfPayHeaderBean.setIcNum(idNum);
        mSelfPayHeaderBean.setHospitalName("?????????");
        mItemList.add(mSelfPayHeaderBean);
        setAdapter();
    }

    private void findViews() {
        tvPayMoney = findViewById(R.id.tvPayMoney);
        tvMoneyNum = findViewById(R.id.tvMoneyNum);
        llNeedPay = findViewById(R.id.llNeedPay);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        tvPayMoney.setOnClickListener(v -> PaymentDetailsActivity.actionStart(
                SelfPayFeeActivity.this, mOrgCode, mOrgName, false));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG, "onRestart()");
        backRefreshPager();
    }

    private void backRefreshPager() {
        // ??????????????????????????????
        llNeedPay.setVisibility(View.GONE);
        // ???????????????????????????????????????????????????????????????????????????
        if (mItemList.size() > 0) {
            mItemList.clear();
        }
        mSelfPayHeaderBean.setHospitalName("?????????");
        // ???????????????????????????
        mItemList.add(mSelfPayHeaderBean);
        refreshAdapter();
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mSelfPayFeeAdapter = new SelfPayFeeAdapter(this, mItemList);
            recyclerView.setAdapter(mSelfPayFeeAdapter);
            // ?????????????????????
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public void getHospitalList() {
        mPresenter.getHospitalList(OrgConfig.GLOBAL_API_VERSION, "01");
    }

    /**
     * ???????????????
     */
    private void showWheelDialog(String json) {
        // ???????????????iOS???????????????????????????
        mCityPickerView.init(json);

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

                mSelfPayHeaderBean.setHospitalName(mOrgName);

                // ???????????????????????????????????????????????????????????????????????????
                if (mItemList.size() > 0) {
                    mItemList.clear();
                }
                // ???????????????????????????
                mItemList.add(mSelfPayHeaderBean);
                refreshAdapter();
                requestYd0003();
            }

            @Override
            public void onCancel() {
                LogUtil.i(TAG, "onCancel()");
            }
        });

        mCityPickerView.showCityPicker();
    }

    public void refreshAdapter() {
        if (mSelfPayFeeAdapter != null) {
            mSelfPayFeeAdapter.setItemList(mItemList);
        }
    }

    /**
     * ?????? yd0003 ??????
     */
    public void requestYd0003() {
        mPresenter.requestYd0003(mOrgCode);
    }

    @Override
    public void onYd0003Result(FeeBillEntity entity) {
        if (entity != null) {
            llNeedPay.setVisibility(View.VISIBLE);
            String feeTotal = entity.getFeeTotal();
            tvMoneyNum.setText(feeTotal);
            List<FeeBillDetailsBean> details = entity.getDetails();
            // ??????????????????????????????(??????????????? 1 ???)
            mItemList.addAll(1, details);
            refreshAdapter();
        } else {
            llNeedPay.setVisibility(View.GONE);
            refreshAdapter();
        }
    }

    @Override
    public void onHospitalListResult(HospitalEntity body) {
        mCompositeDisposable.add(
                Observable
                        .just(body)
                        .map(HospitalEntity::getDetails)
                        .filter(detailsBean -> detailsBean != null && detailsBean.size() > 0)
                        .map(detailsBean -> new Gson().toJson(detailsBean))
                        .subscribe(this::showWheelDialog, throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()))
        );
    }

    @Override
    public void showLoading(boolean show) {
        showLoadingView(show);
    }

    public static void actionStart(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, SelfPayFeeActivity.class);
        context.startActivity(intent);
    }

}
