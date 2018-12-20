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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.HosHistoryAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.contract.InHosHisContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.presenter.InHosHisPresenter;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.view.InHospitalRecordActivity;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectHospitalWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x-sir on 2018/12/18 :)
 * Function:
 */
public class InHospitalHistory extends MvpBaseActivity<InHosHisContract.IView,
        InHosHisPresenter<InHosHisContract.IView>> implements InHosHisContract.IView {

    private static final String TAG = "InHospitalHistory";
    private RecyclerView recyclerView;
    private TextView tvHospitalName;
    private View activityView;
    private String mOrgName;
    private String mOrgCode;
    private LoadingView mLoading;
    private HosHistoryAdapter mHosHistoryAdapter;
    private List<Cy0001Entity.DetailsBean> mDetails = new ArrayList<>();
    private SelectHospitalWindow mSelectHospitalWindow;
    private List<HospitalEntity.DetailsBean> mHospitalBeanList;
    private SelectHospitalWindow.OnLoadingListener mOnLoadingListener =
            () -> BrightnessManager.lighton(InHospitalHistory.this);

    private static final String HUZHOU_CENTER_HOS_ORG_CODE = "47117170333050211A1001";
    private static final String HUZHOU_CENTER_HOS_ORG_NAME = "湖州市中心医院";

    private SelectHospitalWindow.OnItemClickListener mOnItemClickListener = new SelectHospitalWindow.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            if (mHospitalBeanList != null && position < mHospitalBeanList.size()) {
                HospitalEntity.DetailsBean bean = mHospitalBeanList.get(position);
                if (bean != null) {
                    mOrgCode = bean.getOrg_code();
                    mOrgName = bean.getOrg_name();
                    LogUtil.i(TAG, "mOrgCode===" + mOrgCode + ",mOrgName===" + mOrgName);
                }
            }

            tvHospitalName.setText(mOrgName);
            requestCY0001();
        }
    };

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
        mOrgName = HUZHOU_CENTER_HOS_ORG_NAME;
        mOrgCode = HUZHOU_CENTER_HOS_ORG_CODE;
        tvHospitalName.setText(mOrgName);
        requestCY0001();
    }

    private void initListener() {
        tvHospitalName.setOnClickListener(v -> mPresenter.getHospitalList());
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        activityView = findViewById(R.id.activityView);
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
            mHospitalBeanList = body.getDetails();
            if (mHospitalBeanList != null && mHospitalBeanList.size() > 0) {
                if (mSelectHospitalWindow == null) {
                    mSelectHospitalWindow = new SelectHospitalWindow.Builder(this)
                            .setDropView(activityView)
                            .setListener(mOnLoadingListener)
                            .setOnItemClickListener(mOnItemClickListener)
                            .build();
                }

                BrightnessManager.lightoff(this);
                mSelectHospitalWindow.setBeanList(mHospitalBeanList);
                mSelectHospitalWindow.show();
            } else {
                WToastUtil.show("未查询到门诊账单！");
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

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, InHospitalHistory.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

}
