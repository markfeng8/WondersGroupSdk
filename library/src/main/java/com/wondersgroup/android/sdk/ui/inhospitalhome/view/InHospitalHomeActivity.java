/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.inhospitalhome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.CityBean;
import com.wondersgroup.android.sdk.entity.Cy0001Entity;
import com.wondersgroup.android.sdk.entity.EleCardEntity;
import com.wondersgroup.android.sdk.entity.HospitalBean;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.entity.Yd0001Entity;
import com.wondersgroup.android.sdk.epsoft.ElectronicSocialSecurityCard;
import com.wondersgroup.android.sdk.ui.daydetailedlist.view.DayDetailedListActivity;
import com.wondersgroup.android.sdk.ui.inhospitalhistory.view.InHospitalHistory;
import com.wondersgroup.android.sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
import com.wondersgroup.android.sdk.ui.inhospitalhome.presenter.InHospitalHomePresenter;
import com.wondersgroup.android.sdk.ui.leavehospital.view.LeaveHospitalActivity;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.DensityUtils;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.StringUtils;
import com.wondersgroup.android.sdk.utils.WToastUtil;
import com.wondersgroup.android.sdk.widget.selecthospital.CityConfig;
import com.wondersgroup.android.sdk.widget.selecthospital.HospitalPickerView;
import com.wondersgroup.android.sdk.widget.selecthospital.OnCityItemClickListener;

import java.util.List;

import cn.com.epsoft.zjessc.callback.ResultType;
import io.reactivex.Observable;

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
    private Cy0001Entity mCy0001Entity;
    private HospitalPickerView mCityPickerView;

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

    private void initData() {
        mCityPickerView = new HospitalPickerView(this);
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        tvName.setText(name);
        tvIdNum.setText(StringUtils.getMosaicIdNum(idNum));
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
        tvMobPayState.setOnClickListener(view -> applyElectronicSocialSecurityCard());
        // 选择医院
        tvHospitalName.setOnClickListener(view -> mPresenter.getHospitalList(OrgConfig.GLOBAL_API_VERSION, "02"));
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
            String mobPayStatus = SpUtil.getInstance().getString(SpKey.ELE_CARD_STATUS, "");
            if (!"01".equals(mobPayStatus)) {
                WToastUtil.show("您未开通医保移动支付，请先开通！");
                return;
            }
        }

        LogUtil.i(TAG, "mInState===" + mInState);
        if ("01".equals(mInState) && viewGroup.getVisibility() == View.VISIBLE) {
            LeaveHospitalActivity.actionStart(InHospitalHomeActivity.this,
                    mOrgCode, mOrgName, mInHosId, mInHosDate, mInHosArea
            );
        } else {
            WToastUtil.show("您当前不是预出院状态！");
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
        EleCardEntity eleCardEntity = new Gson().fromJson(data, EleCardEntity.class);
        String actionType = eleCardEntity.getActionType();
        switch (actionType) {
            // 电子社保卡申领完成（一级签发）
            case "001":
                parseResult(eleCardEntity);
                break;
            // 其他申领成功的情况，和 001 一样需要上传 signNo
            case "002":
                parseResult(eleCardEntity);
                break;
            // 解除绑定完成
            case "003":
                requestYd0002(OrgConfig.STATE_CLOSE);
                break;
            // 之前从未申领过社保卡，第一次申领电子社保卡的情况
            case "005":
                parseResult(eleCardEntity);
                break;
            default:
                break;
        }
    }

    private void parseResult(EleCardEntity eleCardEntity) {
        String signNo = eleCardEntity.getSignNo();
        String aab301 = eleCardEntity.getAab301();
        LogUtil.i(TAG, "signNo===" + signNo + ",aab301===" + aab301);
        SpUtil.getInstance().save(SpKey.SIGN_NO, signNo);
        requestYd0002(OrgConfig.STATE_OPEN);
    }

    /**
     * 上传电子社保卡开通状态
     */
    private void requestYd0002(String state) {
        mPresenter.requestYd0002(state);
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
                        requestYd0001();
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

    /**
     * 查询电子社保卡申领状态
     */
    private void requestYd0001() {
        mPresenter.requestYd0001();
    }

    @Override
    public void onYd0001Result(final Yd0001Entity entity) {
        // 电子社保卡状态：00 未开通 01 已开通
        String eleCardStatus = entity.getEleCardStatus();
        LogUtil.i(TAG, "eleCardStatus===" + eleCardStatus);
        SpUtil.getInstance().save(SpKey.ELE_CARD_STATUS, eleCardStatus);
        // 如果已开通，保存签发号
        if ("01".equals(eleCardStatus)) {
            SpUtil.getInstance().save(SpKey.SIGN_NO, entity.getSignNo());
        }

        // 00 未签约
        if ("00".equals(eleCardStatus)) {
            setEleCardState(true);
            // 01 已签约
        } else if ("01".equals(eleCardStatus)) {
            setEleCardState(false);
        }
    }

    private void setViewVisibility(boolean hasDetail) {
        viewGroup.setVisibility(hasDetail ? View.VISIBLE : View.GONE);
        tvNoDetail.setVisibility(hasDetail ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置电子社保卡开通状态
     */
    private void setEleCardState(boolean enable) {
        tvMobPayState.setVisibility(View.VISIBLE);
        if (enable) {
            // 分别为开始颜色，结束颜色
            int[] colors = new int[]{Color.parseColor("#f2c700"), Color.parseColor("#fea127")};
            GradientDrawable linearDrawable = new GradientDrawable();
            linearDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            linearDrawable.setColors(colors);
            linearDrawable.setCornerRadius(DensityUtils.dp2px(this, 20));
            linearDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

            tvMobPayState.setBackground(linearDrawable);
            tvMobPayState.setText(getString(R.string.wonders_to_open_mobile_pay));
            tvMobPayState.setTextColor(getResources().getColor(R.color.wonders_rgb_color_ffffff));
            tvMobPayState.setCompoundDrawables(null, null, null, null);

        } else {
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(DensityUtils.dp2px(this, 20));
            gd.setGradientType(GradientDrawable.RECTANGLE);
            gd.setColor(Color.parseColor("#6B45BFDB"));

            tvMobPayState.setBackground(gd);
            tvMobPayState.setText(getString(R.string.wonders_open_mobile_pay));
            tvMobPayState.setTextColor(getResources().getColor(R.color.wonders_rgb_color_386fb9));
        }
    }

    @Override
    public void showLoading(boolean show) {
        showLoadingView(show);
    }

    public static void actionStart(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, InHospitalHomeActivity.class);
        context.startActivity(intent);
    }

}
