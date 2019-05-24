package com.wondersgroup.android.sdk.ui.afterpayhome.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.adapter.AfterPayHomeAdapter;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.Exceptions;
import com.wondersgroup.android.sdk.constants.IntentExtra;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.sdk.entity.CityBean;
import com.wondersgroup.android.sdk.entity.EleCardEntity;
import com.wondersgroup.android.sdk.entity.FeeBillDetailsBean;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.HospitalBean;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.entity.SerializableHashMap;
import com.wondersgroup.android.sdk.entity.Yd0001Entity;
import com.wondersgroup.android.sdk.epsoft.ElectronicSocialSecurityCard;
import com.wondersgroup.android.sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.RxUtils;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;
import com.wondersgroup.android.sdk.widget.selecthospital.CityConfig;
import com.wondersgroup.android.sdk.widget.selecthospital.HospitalPickerView;
import com.wondersgroup.android.sdk.widget.selecthospital.OnCityItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.epsoft.zjessc.callback.ResultType;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页
 */
public class AfterPayHomeActivity extends MvpBaseActivity<AfterPayHomeContract.IView,
        AfterPayHomePresenter<AfterPayHomeContract.IView>> implements AfterPayHomeContract.IView {

    private static final String TAG = AfterPayHomeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private LinearLayout llNeedPay;
    private AfterPayHomeAdapter mAdapter;
    private HashMap<String, String> mPassParamMap;
    /**
     * 选择器默认的医院
     */
    private String mOrgName = "湖州市中心医院";
    private String mOrgCode;
    /**
     * 选择器默认的地区
     */
    private String mAreaName = "湖州市";
    private boolean mAfterPayOpenSuccess;
    /**
     * 头部数据类型
     */
    private AfterHeaderBean mHeaderBean = new AfterHeaderBean();
    /**
     * 中间的门诊账单的数据类型
     */
    private List<FeeBillDetailsBean> mFeeBillList = new ArrayList<>();
    /**
     * 尾部温馨提示的数据类型
     */
    private static final String NOTICE_MESSAGE = "温馨提示";
    /**
     * 装所有数据的 List 集合
     */
    private List<Object> mItemList = new ArrayList<>();

    private HospitalPickerView mCityPickerView = new HospitalPickerView();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected AfterPayHomePresenter<AfterPayHomeContract.IView> createPresenter() {
        return new AfterPayHomePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_after_pay_home);
        findViews();
        initData();
        initListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG, "onRestart()");
        mAfterPayOpenSuccess = SpUtil.getInstance().getBoolean(SpKey.AFTER_PAY_OPEN_SUCCESS, false);
        if (mAfterPayOpenSuccess) {
            requestXy0001();
        }
        backRefreshPager();
    }

    /**
     * 返回到主页面刷新所有数据
     */
    private void backRefreshPager() {
        // 回来就隐藏付款的布局
        llNeedPay.setVisibility(View.GONE);
        // 刷新医后付&医保移动支付状态
        requestXy0001();
        requestYd0001();
        // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
        mHeaderBean.setHospitalName("请选择医院");
        mItemList.removeAll(mFeeBillList);
        refreshAdapter();
    }

    private void initListener() {
        Disposable disposable =
                RxUtils.clickView(tvPayMoney)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(s -> PaymentDetailsActivity.actionStart(
                                AfterPayHomeActivity.this, mOrgCode, mOrgName, false));

        mCompositeDisposable.add(disposable);
    }

    private void initData() {
        initHeaderData();
        getIntentAndFindAfterPayState();
    }

    private void initHeaderData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String socialNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mHeaderBean.setName(name);
        mHeaderBean.setSocialNum(socialNum);
        // 第一次添加头部数据
        mItemList.add(mHeaderBean);
        // 第二次添加门诊账单数据
        mItemList.addAll(mFeeBillList);
        // 第三次添加尾部数据
        mItemList.add(NOTICE_MESSAGE);
        setAdapter();
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new AfterPayHomeAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.refreshAdapter();
        }
    }

    private void getIntentAndFindAfterPayState() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    mPassParamMap = sMap.getMap();
                    requestXy0001();
                    requestYd0001();
                }
            }
        }
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tvMoneyNum = findViewById(R.id.tvMoneyNum);
        tvPayMoney = findViewById(R.id.tvPayMoney);
        llNeedPay = findViewById(R.id.llNeedPay);
    }

    @Override
    public void onXy0001Result(AfterPayStateEntity entity) {
        String signingStatus = entity.getSigning_status();
        String paymentStatus = entity.getOne_payment_status();
        String phone = entity.getPhone();
        String signDate = entity.getCt_date();
        String feeTotal = entity.getFee_total();
        mOrgCode = entity.getOrg_code();
        mOrgName = entity.getOrg_name();

        if (!TextUtils.isEmpty(feeTotal)) {
            tvMoneyNum.setText(feeTotal);
        }

        SpUtil.getInstance().save(SpKey.SIGNING_STATUS, signingStatus);
        SpUtil.getInstance().save(SpKey.PAYMENT_STATUS, paymentStatus);
        SpUtil.getInstance().save(SpKey.PHONE, phone);
        SpUtil.getInstance().save(SpKey.SIGN_DATE, signDate);
        SpUtil.getInstance().save(SpKey.FEE_TOTAL, feeTotal);
        // 重置医后付开通标志
        if (mAfterPayOpenSuccess) {
            SpUtil.getInstance().save(SpKey.AFTER_PAY_OPEN_SUCCESS, false);
        }

        mHeaderBean.setOrgCode(mOrgCode);
        mHeaderBean.setOrgName(mOrgName);
        mHeaderBean.setSigningStatus(signingStatus);
        mHeaderBean.setPaymentStatus(paymentStatus);
        mHeaderBean.setFeeTotal(feeTotal);
        refreshAdapter();
    }

    @Override
    public void onYd0001Result(final Yd0001Entity entity) {
        Disposable disposable =
                Observable
                        .just(entity)
                        .doOnNext(this::saveEleCardData)
                        .subscribe(s -> refreshAdapter());

        mCompositeDisposable.add(disposable);
    }

    private void saveEleCardData(Yd0001Entity entity) {
        // 电子社保卡状态：00 未开通 01 已开通
        String eleCardStatus = entity.getEleCardStatus();
        LogUtil.i(TAG, "eleCardStatus===" + eleCardStatus);
        mHeaderBean.setEleCardStatus(eleCardStatus);
        SpUtil.getInstance().save(SpKey.ELE_CARD_STATUS, eleCardStatus);
        // 如果已开通，保存签发号
        if ("01".equals(eleCardStatus)) {
            SpUtil.getInstance().save(SpKey.SIGN_NO, entity.getSignNo());
        }
    }

    public void applyElectronicSocialSecurityCard() {
        new ElectronicSocialSecurityCard().enter(this, (type, data) -> {
            if (type == ResultType.ACTION) {
                handleAction(data);
            }
        });
    }

    /**
     * 签发回调处理
     */
    private void handleAction(String data) {
        WToastUtil.show(data);
        EleCardEntity eleCardEntity = new Gson().fromJson(data, EleCardEntity.class);
        String actionType = eleCardEntity.getActionType();
        // 表示一级签发
        if ("001".equals(actionType)) {
            String signNo = eleCardEntity.getSignNo();
            String aab301 = eleCardEntity.getAab301();
            LogUtil.i(TAG, "signNo===" + signNo + ",aab301===" + aab301);
            SpUtil.getInstance().save(SpKey.SIGN_NO, signNo);
            requestYd0002();
        }
    }

    @Override
    public void onYd0003Result(FeeBillEntity entity) {
        // 先移除旧的门诊账单数据
        mItemList.removeAll(mFeeBillList);
        if (entity != null) {
            llNeedPay.setVisibility(View.VISIBLE);
            String feeTotal = entity.getFeeTotal();
            // 00 未结算 01 正在结算
            String payState = entity.getPayState();
            if ("01".equals(payState)) {
                tvPayMoney.setText("支付中");
                tvPayMoney.setEnabled(false);
            }
            tvMoneyNum.setText(feeTotal);
            mFeeBillList = entity.getDetails();
            mItemList.addAll(1, mFeeBillList);
        } else {
            llNeedPay.setVisibility(View.GONE);
        }
        refreshAdapter();
    }

    @Override
    public void showLoading(boolean show) {
        showLoadingView(show);
    }

    @Override
    public void onHospitalListResult(HospitalEntity body) {
        Disposable disposable =
                Observable
                        .just(body)
                        .map(HospitalEntity::getDetails)
                        .filter(detailsBeanXES -> detailsBeanXES != null && detailsBeanXES.size() > 0)
                        .map(detailsBeanXES -> new Gson().toJson(detailsBeanXES))
                        .subscribe(this::showWheelDialog);

        mCompositeDisposable.add(disposable);
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
                mHeaderBean.setHospitalName(mOrgName);
                requestYd0003();
            }

            @Override
            public void onCancel() {
                LogUtil.i(TAG, "onCancel()");
            }
        });

        mCityPickerView.showCityPicker();
    }

    /**
     * 查询医后付签约状态
     */
    private void requestXy0001() {
        mPresenter.requestXy0001(mPassParamMap);
    }

    /**
     * 查询电子社保卡申领状态
     */
    private void requestYd0001() {
        mPresenter.requestYd0001();
    }

    /**
     * 上传电子社保卡开通状态
     */
    private void requestYd0002() {
        mPresenter.requestYd0002();
    }

    /**
     * 请求 yd0003 接口
     */
    private void requestYd0003() {
        mPresenter.requestYd0003(mOrgCode);
    }

    /**
     * 获取医院列表
     */
    public void getHospitalList() {
        mPresenter.getHospitalList("V1.1", "01");
    }

    public static void actionStart(Context context, HashMap<String, String> param) {
        if (context == null) {
            return;
        }
        if (param == null || param.isEmpty()) {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
        // 传递数据
        SerializableHashMap sMap = new SerializableHashMap();
        // 将 map 数据添加到封装的 sMap 中
        sMap.setMap(param);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentExtra.SERIALIZABLE_MAP, sMap);
        Intent intent = new Intent(context, AfterPayHomeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
