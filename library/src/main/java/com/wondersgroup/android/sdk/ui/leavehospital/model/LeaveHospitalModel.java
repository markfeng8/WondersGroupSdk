/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.leavehospital.model;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.Cy0006Entity;
import com.wondersgroup.android.sdk.entity.Cy0007Entity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.entity.PayParamEntity;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RSAUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class LeaveHospitalModel implements LeaveHospitalContract.IModel {

    private BusinessService mService;

    public LeaveHospitalModel() {
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void requestCy0006(String orgCode, String token, HttpRequestCallback<Cy0006Entity> callback) {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String jzlsh = SpUtil.getInstance().getString(SpKey.JZLSH, "");

        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, RandomUtils.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_CY0006);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        param.put(MapKey.ORG_CODE, orgCode);
        param.put(MapKey.JZLSH, jzlsh);
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.CARD_TYPE, cardType);
        param.put(MapKey.ID_NO, StringUtils.getMosaicIdNum(idNum));
        param.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(idNum));
        param.put(MapKey.TOKEN, token);
        param.put(MapKey.ADVICE_DATE_TIME, DateUtils.getCurrentDateTime());
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        mService.cy0006(RequestUrl.CY0006, param)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void requestCy0007(String orgCode, String toState, String token, String xxjje,
                              String payChl, HttpRequestCallback<Cy0007Entity> callback) {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String jzlsh = SpUtil.getInstance().getString(SpKey.JZLSH, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        String payStartTime = SpUtil.getInstance().getString(SpKey.PAY_START_TIME, "");

        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, RandomUtils.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_CY0007);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        param.put(MapKey.ORG_CODE, orgCode);
        param.put(MapKey.TO_STATE, toState);
        param.put(MapKey.JZLSH, jzlsh);
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.ID_NO, StringUtils.getMosaicIdNum(idNum));
        param.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(idNum));
        param.put(MapKey.TOKEN, token);
        param.put(MapKey.ADVICE_DATE_TIME, DateUtils.getCurrentDateTime());
        param.put(MapKey.XXJJE, xxjje);
        param.put(MapKey.PAY_PLAT_TRADE_NO, payPlatTradeNo);
        param.put(MapKey.PAY_TRAN_DATE_TIME, payStartTime);
        param.put(MapKey.PAY_CHL, payChl);
        // 01 代表 Android APP
        param.put(MapKey.PAY_CLIENT, "01");
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        mService.cy0007(RequestUrl.CY0007, param)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void getPayParam(String orgCode, HttpRequestCallback<PayParamEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0010);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getPayParams(RequestUrl.YD0010, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

}
