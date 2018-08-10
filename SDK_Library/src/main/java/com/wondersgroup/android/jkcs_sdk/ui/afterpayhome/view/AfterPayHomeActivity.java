package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.PayRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.SelectHospitalActivity;
import com.wondersgroup.android.jkcs_sdk.utils.ActivityUtil;

import java.util.HashMap;

// 医后付首页
public class AfterPayHomeActivity extends MvpBaseActivity<AfterPayHomeContract.IView,
        AfterPayHomePresenter<AfterPayHomeContract.IView>> implements AfterPayHomeContract.IView {

    private TextView tvUpdateInfo;
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
    private TextView tvOpenMobilePay;
    private TextView tvToPay;
    private HashMap<String, String> mPassParamMap;

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
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterPayHomeActivity.this.finish();
            }
        });
        tvSelectHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterPayHomeActivity.this, SelectHospitalActivity.class));
            }
        });
        tvUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 还需要添加一些其他数据
                ActivityUtil.startSettingsPage(AfterPayHomeActivity.this, mPassParamMap);
            }
        });
        tvPayRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterPayHomeActivity.this, PayRecordActivity.class));
            }
        });
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_after_pay_home));
        tvPayToast.setText(Html.fromHtml(getString(R.string.wonders_mark_text)));
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
                    // 查询当前签约状态和医后付状态
                    mPresenter.getAfterPayState(mPassParamMap);
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
        tvUpdateInfo = (TextView) findViewById(R.id.tvUpdateInfo);
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
        tvOpenMobilePay = (TextView) findViewById(R.id.tvOpenMobilePay);
        tvToPay = (TextView) findViewById(R.id.tvToPay);
    }

    @Override
    public void returnResult(AfterPayStateEntity entity) {
        if (entity != null) {
            String signingStatus = entity.getSigning_status();
            if ("00".equals(signingStatus)) { // 00未签约（医后付状态给NULL）
                setAfterPayState(true);
            } else if ("01".equals(signingStatus)) { // 01已签约
                setAfterPayState(false);
            } else if ("02".equals(signingStatus)) { // 02 其他
                setAfterPayState(false);
            }
        }
    }

    /**
     * 设置医后付状态
     */
    private void setAfterPayState(boolean enable) {
        if (enable) {
            tvAfterPayState.setText("去开通医后付");
            tvAfterPayState.setEnabled(true);
            tvAfterPayState.setCompoundDrawables(null, null, null, null);
        } else {
            tvAfterPayState.setText("已开通医后付");
            tvAfterPayState.setEnabled(false);
        }
    }

    /**
     * 设置医保移动付状态
     */
    private void setMobilePayState(boolean enable) {
        if (enable) {
            tvAfterPayState.setText("去开通移动支付");
            tvAfterPayState.setEnabled(true);
            tvAfterPayState.setCompoundDrawables(null, null, null, null);
        } else {
            tvAfterPayState.setText("已开通移动支付");
            tvAfterPayState.setEnabled(false);
        }
    }

}
