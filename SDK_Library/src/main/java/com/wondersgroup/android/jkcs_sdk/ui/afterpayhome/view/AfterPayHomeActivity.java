package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.AfterPayAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.widget.DividerItemDecoration;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 医后付首页
public class AfterPayHomeActivity extends MvpBaseActivity<AfterPayHomeContract.IView,
        AfterPayHomePresenter<AfterPayHomeContract.IView>> implements AfterPayHomeContract.IView {

    private static final String TAG = AfterPayHomeActivity.class.getSimpleName();
    private ImageView ivBackBtn;
    private TextView tvTitleName;
    private RecyclerView recyclerView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private View activityView;
    private LinearLayout llNeedPay;

    private LoadingView mLoading;
    private AfterHeaderBean mHeaderBean;
    private List<Object> mItemList = new ArrayList<>();
    private AfterPayAdapter mAdapter;

    @Override
    protected AfterPayHomePresenter<AfterPayHomeContract.IView> createPresenter() {
        return new AfterPayHomePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_after_pay_home);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(v -> AfterPayHomeActivity.this.finish());
        tvPayMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_after_pay_home));
        mLoading = new LoadingView.Builder(this)
                .setDropView(activityView)
                .build();

        initHeaderData();

        //@Test
        //setMobilePayState(true);

        getIntentAndFindAfterPayState();
    }

    private void initHeaderData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String socialNum = SpUtil.getInstance().getString(SpKey.SOCIAL_NUM, "");
        mHeaderBean = new AfterHeaderBean();
        mHeaderBean.setName(name);
        mHeaderBean.setSocialNum(socialNum);

        mItemList.add(mHeaderBean); // 第一次添加数据

        setAdapter();
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new AfterPayAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            // 设置布局管理器
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.setmItemList(mItemList);
        }
    }

    private void getIntentAndFindAfterPayState() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    HashMap<String, String> mPassParamMap = sMap.getMap();
                    // 查询当前医后付签约状态
                    mPresenter.getAfterPayState(mPassParamMap);
                    // 查询当前移动支付状态
                    mPresenter.getMobilePayState(mPassParamMap);
                }
            }
        }
    }

    private void findViews() {
        ivBackBtn = (ImageView) findViewById(R.id.ivBackBtn);
        tvTitleName = (TextView) findViewById(R.id.tvTitleName);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
        llNeedPay = findViewById(R.id.llNeedPay);
        activityView = findViewById(R.id.activity_after_pay_home);
    }

    @Override
    public void afterPayResult(AfterPayStateEntity entity) {
        if (entity != null) {
            String signingStatus = entity.getSigning_status();
            String paymentStatus = entity.getOne_payment_status();
            String noticePhone = entity.getPhone();
            String signDate = entity.getCt_date();
            String feeTotal = entity.getFee_total();
            if (!TextUtils.isEmpty(feeTotal)) {
                tvMoneyNum.setText(feeTotal);
            }

            SpUtil.getInstance().save(SpKey.SIGNING_STATUS, signingStatus);
            SpUtil.getInstance().save(SpKey.PAYMENT_STATUS, paymentStatus);
            SpUtil.getInstance().save(SpKey.NOTICE_PHONE, noticePhone);
            SpUtil.getInstance().save(SpKey.SIGN_DATE, signDate);
            SpUtil.getInstance().save(SpKey.FEE_TOTAL, feeTotal);

            mHeaderBean.setSigningStatus(signingStatus);
            mHeaderBean.setPaymentStatus(paymentStatus);
            mHeaderBean.setFeeTotal(feeTotal);

            mItemList.set(0, mHeaderBean); // 第二次添加数据(放到下标为0处)
            refreshAdapter();
        }
    }

    @Override
    public void mobilePayResult(MobilePayEntity entity) {
        if (entity != null) {
            String mobPayStatus = entity.getMobile_pay_status();
            SpUtil.getInstance().save(SpKey.MOB_PAY_STATUS, mobPayStatus);
            mHeaderBean.setMobPayStatus(mobPayStatus);

            mItemList.set(0, mHeaderBean); // 第三次添加数据(放到下标为0处)
            refreshAdapter();
        }
    }

    @Override
    public void feeBillResult(FeeBillEntity entity) {
        if (entity != null) {
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            if (mItemList.size() > 1) {
                // 先移除旧的
                for (int i = 1; i < mItemList.size(); i++) {
                    mItemList.remove(i);
                }
            }
            mItemList.addAll(details); // 添加医院欠费信息数据(放到下标为0处)
            refreshAdapter();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentExtra.REQUEST_CODE && resultCode == IntentExtra.RESULT_CODE) {
            String orgName = data.getStringExtra(IntentExtra.ORG_NAME);
            String orgCode = data.getStringExtra(IntentExtra.ORG_CODE);
            mHeaderBean.setHospitalName(orgName);
            mItemList.set(0, mHeaderBean); // 选择医院后添加数据(放到下标为0处)
            refreshAdapter();

            HashMap<String, String> map = new HashMap<>();
            map.put(MapKey.ORG_CODE, orgCode);
            map.put(MapKey.PAGE_NUMBER, "1");
            map.put(MapKey.PAGE_SIZE, "10");
            mPresenter.getUnclearedBill(map);
        }
    }
}
