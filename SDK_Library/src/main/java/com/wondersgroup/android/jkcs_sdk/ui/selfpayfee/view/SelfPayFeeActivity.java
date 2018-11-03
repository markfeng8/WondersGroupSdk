/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.SelfPayFeeAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SelfPayHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.contract.SelfPayFeeContract;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.presenter.SelfPayFeePresenter;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectHospitalWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by x-sir on 2018/10/31 :)
 * Function:自费卡类型页面
 */
public class SelfPayFeeActivity extends MvpBaseActivity<SelfPayFeeContract.IView,
        SelfPayFeePresenter<SelfPayFeeContract.IView>> implements SelfPayFeeContract.IView {

    private static final String TAG = "SelfPayFeeActivity";
    private View activityView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private LinearLayout llNeedPay;
    private RecyclerView recyclerView;
    private LoadingView mLoading;
    private HashMap<String, String> mPassParamMap;
    private SelfPayFeeAdapter mSelfPayFeeAdapter;
    private List<Object> mItemList = new ArrayList<>();
    private String mOrgName;
    private String mOrgCode;
    private String mNotice = "温馨提示";
    private SelfPayHeaderBean mSelfPayHeaderBean;
    private SelectHospitalWindow mSelectHospitalWindow;
    private List<HospitalEntity.DetailsBean> mHospitalBeanList;
    private SelectHospitalWindow.OnLoadingListener mOnLoadingListener =
            () -> BrightnessManager.lighton(SelfPayFeeActivity.this);

    @Override
    protected SelfPayFeePresenter<SelfPayFeeContract.IView> createPresenter() {
        return new SelfPayFeePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_self_pay_fee);
        findViews();
        getIntentData();
        initData();
        initListener();
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .setDropView(activityView)
                .build();

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mSelfPayHeaderBean = new SelfPayHeaderBean();
        mSelfPayHeaderBean.setName(name);
        mSelfPayHeaderBean.setIcNum(idNum);
        mItemList.add(mSelfPayHeaderBean);
        mItemList.add(mNotice);
        setAdapter();
    }

    private void findViews() {
        tvPayMoney = findViewById(R.id.tvPayMoney);
        tvMoneyNum = findViewById(R.id.tvMoneyNum);
        llNeedPay = findViewById(R.id.llNeedPay);
        activityView = findViewById(R.id.activityView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        tvPayMoney.setOnClickListener(v -> PaymentDetailsActivity.actionStart(
                SelfPayFeeActivity.this, mOrgCode, mOrgName, false));
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    mPassParamMap = sMap.getMap();

                    // TODO: 2018/10/31
                }
            }
        }
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mSelfPayFeeAdapter = new SelfPayFeeAdapter(this, mItemList);
            recyclerView.setAdapter(mSelfPayFeeAdapter);
            // 设置布局管理器
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
    }

    public void getHospitalList() {
        mPresenter.getHospitalList();
    }

    private SelectHospitalWindow.OnItemClickListener mOnItemClickListener = new SelectHospitalWindow.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            if (mHospitalBeanList != null && position < mHospitalBeanList.size()) {
                HospitalEntity.DetailsBean bean = mHospitalBeanList.get(position);
                if (bean != null) {
                    mOrgCode = bean.getOrg_code();
                    mOrgName = bean.getOrg_name();
                }
            }

            mSelfPayHeaderBean.setHospitalName(mOrgName);

            // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
            if (mItemList.size() > 0) {
                mItemList.clear();
            }
            mItemList.add(mSelfPayHeaderBean); // 选择医院后添加数据
            refreshAdapter();
            requestYd0003();
        }
    };

    public void refreshAdapter() {
        if (mSelfPayFeeAdapter != null) {
            mSelfPayFeeAdapter.setItemList(mItemList);
        }
    }

    /**
     * 请求 yd0003 接口
     */
    public void requestYd0003() {
        mPresenter.requestYd0003(mOrgCode);
    }

    @Override
    public void onYd0003Result(FeeBillEntity entity) {
        if (entity != null) {
            llNeedPay.setVisibility(View.VISIBLE);
            String feeTotal = entity.getFee_total();
            tvMoneyNum.setText(feeTotal);
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            // 添加医院欠费信息数据(放到下标为 1 处)
            mItemList.addAll(1, details);
            // 第二次添加数据
            mItemList.add(mNotice);
            refreshAdapter();
        } else {
            llNeedPay.setVisibility(View.GONE);
            mItemList.add(mNotice); // 第二次添加数据
            refreshAdapter();
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

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
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
