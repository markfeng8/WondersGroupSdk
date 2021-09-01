/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.inhospitalhome.model;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.Cy0001Entity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RSAUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面的 Model
 */
public class InHospitalHomeModel implements InHospitalHomeContract.IModel {

    private BusinessService mService;

    public InHospitalHomeModel() {
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void requestCy0001(String orgCode, String inState, HttpRequestCallback<Cy0001Entity> callback) {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String phone = SpUtil.getInstance().getString(SpKey.PASS_PHONE, "");

        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, RandomUtils.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_CY0001);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        param.put(MapKey.ORG_CODE, orgCode);
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.ID_NO, StringUtils.getMosaicIdNum(idNum));
        param.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(idNum));
        param.put(MapKey.PHONE, phone);
        param.put(MapKey.IN_STATE, inState);
        param.put(MapKey.START_DATE, DateUtils.getCurMonthBeforeM(3));
        param.put(MapKey.END_DATE, DateUtils.getCurrentDate());
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        mService.cy0001(RequestUrl.CY0001, param)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }
}
