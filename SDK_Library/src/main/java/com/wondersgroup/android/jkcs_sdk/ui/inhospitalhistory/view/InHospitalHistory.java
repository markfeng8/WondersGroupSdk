/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.HosHistoryAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.entity.CityBean;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalBean;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.contract.InHosHisContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.presenter.InHosHisPresenter;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.view.InHospitalRecordActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.selecthospital.CityConfig;
import com.wondersgroup.android.jkcs_sdk.widget.selecthospital.HospitalPickerView;
import com.wondersgroup.android.jkcs_sdk.widget.selecthospital.OnCityItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x-sir on 2018/12/18 :)
 * Function:住院历史列表页面
 */
public class InHospitalHistory extends MvpBaseActivity<InHosHisContract.IView,
        InHosHisPresenter<InHosHisContract.IView>> implements InHosHisContract.IView {

    private static final String TAG = "InHospitalHistory";
    private RecyclerView recyclerView;
    private TextView tvHospitalName;
    private View activityView;
    /**
     * 选择器默认的医院
     */
    private String mOrgName = "湖州市中心医院";
    private String mOrgCode;
    /**
     * 选择器默认的地区
     */
    private String mAreaName = "湖州市";
    private LoadingView mLoading;
    private HosHistoryAdapter mHosHistoryAdapter;
    private List<Cy0001Entity.DetailsBean> mDetails = new ArrayList<>();
    private HospitalPickerView mCityPickerView = new HospitalPickerView();

    private void requestCY0001() {
        mPresenter.requestCy0001(mOrgCode, OrgConfig.IN_STATE1);
    }

    @Override
    protected InHosHisPresenter<InHosHisContract.IView> createPresenter() {
        return new InHosHisPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital_history);
        findViews();
        initViews();
        initData();
        initListener();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();
        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            if (!TextUtils.isEmpty(mOrgCode) && !TextUtils.isEmpty(mOrgName)) {
                tvHospitalName.setText(mOrgName);
                requestCY0001();
            }
        }
    }

    private void initListener() {
        tvHospitalName.setOnClickListener(v -> mPresenter.getHospitalList());
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        activityView = findViewById(R.id.activityView);
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
        if (entity != null) {
            mDetails = entity.getDetails();
            setAdapter();
        } else {
            refreshAdapter();
        }
    }

    private void refreshAdapter() {
        // 1.清除旧数据
        if (mDetails.size() > 0) {
            mDetails.clear();
        }
        // 2.刷新适配器
        if (mHosHistoryAdapter != null) {
            mHosHistoryAdapter.notifyDataSetChanged();
        }
    }

    private void setAdapter() {
        mHosHistoryAdapter = new HosHistoryAdapter(R.layout.wonders_group_in_hos_record_item, mDetails);
        View notDataView = getLayoutInflater().inflate(R.layout.wonders_group_empty_view, (ViewGroup) recyclerView.getParent(), false);
        mHosHistoryAdapter.setEmptyView(notDataView);
        mHosHistoryAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mHosHistoryAdapter.isFirstOnly(false);
        recyclerView.setAdapter(mHosHistoryAdapter);
        mHosHistoryAdapter.setOnItemClickListener((adapter, view, position) -> InHospitalRecordActivity.actionStart(InHospitalHistory.this, mDetails.get(position)));
    }

    public static void actionStart(Context context, String orgCode, String orgName) {
        if (context != null) {
            Intent intent = new Intent(context, InHospitalHistory.class);
            if (!TextUtils.isEmpty(orgCode)) {
                intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            }
            if (!TextUtils.isEmpty(orgCode)) {
                intent.putExtra(IntentExtra.ORG_NAME, orgName);
            }
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

}
