package com.wondersgroup.android.sdk.ui.settingspage.model;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.BaseEntity;
import com.wondersgroup.android.sdk.entity.SmsEntity;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:设置页面的 Model
 */
public class SettingsModel implements SettingsContract.IModel {

    private String mCardType;
    private BusinessService mService;

    public SettingsModel() {
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void sendOpenRequest(HashMap<String, String> map, HttpRequestCallback<BaseEntity> callback) {
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0003);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.updatePhone(RequestUrl.XY0003, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void sendVerifyCode(String phone, String idenClass, HttpRequestCallback<SmsEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.PHONE, phone);
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.IDEN_CLASS, idenClass);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.sendSmsCode(RequestUrl.XY0006, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void termination(HashMap<String, String> map, HttpRequestCallback<BaseEntity> callback) {
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0004);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.updatePhone(RequestUrl.XY0004, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }
}
