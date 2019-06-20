package com.wondersgroup.android.sdk.ui.openafterpay.model;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.SmsEntity;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.openafterpay.contract.OpenAfterPayContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:开通医后付页面的 Model 类
 */
public class OpenAfterPayModel implements OpenAfterPayContract.IModel {

    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;
    private String mHomeAddress;
    private BusinessService mService;

    public OpenAfterPayModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mHomeAddress = SpUtil.getInstance().getString(SpKey.HOME_ADDRESS, "");
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void sendSmsCode(String phone, HttpRequestCallback<SmsEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.PHONE, phone);
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.IDEN_CLASS, OrgConfig.IDEN_CLASS1);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.sendSmsCode(RequestUrl.XY0006, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void openAfterPay(String phone, String idenCode, HttpRequestCallback<SmsEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0002);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.REG_ORG_NAME, OrgConfig.SIGN_ORG_NAME);
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.PHONE, phone);
        map.put(MapKey.HOME_ADDRESS, mHomeAddress);
        map.put(MapKey.IDEN_CODE, idenCode);
        map.put(MapKey.HEALTH_CARE_STATUS, OrgConfig.HEALTH_CARE_STATUS);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.sendSmsCode(RequestUrl.XY0002, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }
}
