package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.epsoft.hzauthsdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.PayRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.view.SelectHospitalActivity;
import com.wondersgroup.android.jkcs_sdk.utils.ActivityUtil;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WonderToastUtil;

import java.util.HashMap;

// 医后付首页
public class AfterPayHomeActivity extends MvpBaseActivity<AfterPayHomeContract.IView,
        AfterPayHomePresenter<AfterPayHomeContract.IView>> implements AfterPayHomeContract.IView {

    private static final String TAG = AfterPayHomeActivity.class.getSimpleName();
    private TextView tvSettings;
    private TextView tvPayRecord;
    private TextView tvPayToast;
    private TextView tvHospitalName;
    private TextView tvSelectHospital;
    private ListView listView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private ImageView ivBackBtn;
    private TextView tvTitleName;
    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvAfterPayState;
    private TextView tvMobilePayState;
    private TextView tvToPay;
    private TextView tvToPayFee;
    private LinearLayout llToPayFee;
    private HashMap<String, String> mPassParamMap;
    private AfterPayStateEntity mAfterPayEntity;
    private MobilePayEntity mMobilePayEntity;

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
        // 选择医院
        tvSelectHospital.setOnClickListener(v -> startActivity(new Intent(AfterPayHomeActivity.this, SelectHospitalActivity.class)));
        // 设置
        tvSettings.setOnClickListener(v -> ActivityUtil.startSettingsPage(AfterPayHomeActivity.this, mPassParamMap, mAfterPayEntity, mMobilePayEntity));
        // 缴费记录
        tvPayRecord.setOnClickListener(v -> startActivity(new Intent(AfterPayHomeActivity.this, PayRecordActivity.class)));
        // 去缴费
        tvToPayFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // 去开通医后付
        tvAfterPayState.setOnClickListener(v -> ActivityUtil.startOpenAfterPay(AfterPayHomeActivity.this, mPassParamMap));
        // 去开通医保移动支付
        tvMobilePayState.setOnClickListener(v -> {
            openMobilePay();
        });
    }

    private void openMobilePay() {
        AuthCall.initSDK(AfterPayHomeActivity.this, "6151490102",
                result -> LogUtil.e(TAG, "result===" + result));

        String phone = SpUtil.getInstance().getString(SpKey.PHONE, "");
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            AuthCall.businessProcess(AfterPayHomeActivity.this,
                    MakeArgsFactory.getBussArgs(phone), WonderToastUtil::show);
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_PHONE_INVALID);
        }
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_after_pay_home));
        tvPayToast.setText(Html.fromHtml(getString(R.string.wonders_mark_text)));

        //@Test
        setMobilePayState(true);

        getIntentAndFindAfterPayState();
    }

    private void getIntentAndFindAfterPayState() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    mPassParamMap = sMap.getMap();
                    showPersonalBaseInfo();
                    // 查询当前医后付签约状态
                    mPresenter.getAfterPayState(mPassParamMap);
                    // 查询当前移动支付状态
                    mPresenter.getMobilePayState(mPassParamMap);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showPersonalBaseInfo() {
        String name = mPassParamMap.get(MapKey.NAME);
        String cardNo = mPassParamMap.get(MapKey.CARD_NO);
        tvTreatName.setText(name);
        tvSocialNum.setText(getString(R.string.wonders_text_social_number) + cardNo);
    }

    private void findViews() {
        tvSettings = (TextView) findViewById(R.id.tvUpdateInfo);
        tvPayRecord = (TextView) findViewById(R.id.tvPayRecord);
        tvPayToast = (TextView) findViewById(R.id.tvPayToast);
        tvHospitalName = (TextView) findViewById(R.id.tvHospitalName);
        tvSelectHospital = (TextView) findViewById(R.id.tvSelectHospital);
        listView = (ListView) findViewById(R.id.listView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
        ivBackBtn = (ImageView) findViewById(R.id.ivBackBtn);
        tvTitleName = (TextView) findViewById(R.id.tvTitleName);
        tvTreatName = (TextView) findViewById(R.id.tvTreatName);
        tvSocialNum = (TextView) findViewById(R.id.tvSocialNum);
        tvAfterPayState = (TextView) findViewById(R.id.tvAfterPayState);
        tvMobilePayState = (TextView) findViewById(R.id.tvMobilePayState);
        tvToPay = (TextView) findViewById(R.id.tvToPay);
        tvToPayFee = (TextView) findViewById(R.id.tvToPayFee);
        llToPayFee = (LinearLayout) findViewById(R.id.llToPayFee);
    }

    @Override
    public void afterPayResult(AfterPayStateEntity entity) {
        mAfterPayEntity = entity;
        if (entity != null) {
            String signingStatus = entity.getSigning_status();
            if ("00".equals(signingStatus)) { // 00未签约（医后付状态给NULL）
                setAfterPayState(true);
            } else if ("01".equals(signingStatus)) { // 01已签约
                setAfterPayState(false);
                /*
                 * 1：正常（缴清或未使用医后付服务）2：欠费(医后付后有欠费的概要信
                 */
                String paymentStatus = entity.getOne_payment_status();
                if ("1".equals(paymentStatus)) {
                    llToPayFee.setVisibility(View.INVISIBLE);
                } else if ("2".equals(paymentStatus)) {
                    llToPayFee.setVisibility(View.VISIBLE);
                    String feeTotal = entity.getFee_total();
                    String content = getString(R.string.wonders_to_pay_fee1) + feeTotal + getString(R.string.wonders_to_pay_fee2);
                    tvToPay.setText(content);
                }
            } else if ("02".equals(signingStatus)) { // 02 其他
                setAfterPayState(false);
            }
        }
    }

    @Override
    public void mobilePayResult(MobilePayEntity entity) {
        mMobilePayEntity = entity;
        if (entity != null) {
            String mobPayStatus = entity.getMobile_pay_status();
            if ("00".equals(mobPayStatus)) { // 00 未签约
                setMobilePayState(true);
            } else if ("01".equals(mobPayStatus)) { // 01已签约
                setMobilePayState(false);
            } else if ("02".equals(mobPayStatus)) { // 02 其他
                setMobilePayState(true);
            }
        }
    }

    /**
     * 设置医后付状态
     */
    private void setAfterPayState(boolean enable) {
        if (enable) {
            tvAfterPayState.setText(getString(R.string.wonders_to_open_after_pay));
            tvAfterPayState.setEnabled(true);
            tvAfterPayState.setCompoundDrawables(null, null, null, null);
        } else {
            tvAfterPayState.setText(getString(R.string.wonders_open_after_pay));
            tvAfterPayState.setEnabled(false);
        }
    }

    /**
     * 设置医保移动付状态
     */
    private void setMobilePayState(boolean enable) {
        if (enable) {
            tvMobilePayState.setText(getString(R.string.wonders_to_open_mobile_pay));
            tvMobilePayState.setEnabled(true);
            tvMobilePayState.setCompoundDrawables(null, null, null, null);
        } else {
            tvMobilePayState.setText(getString(R.string.wonders_open_mobile_pay));
            tvMobilePayState.setEnabled(false);
        }
    }

}
