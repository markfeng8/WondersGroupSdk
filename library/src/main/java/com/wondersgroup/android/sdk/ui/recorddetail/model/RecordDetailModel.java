/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.recorddetail.model;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.recorddetail.contract.RecordDetailContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:
 */
public class RecordDetailModel implements RecordDetailContract.IModel {

    private BusinessService mService;

    public RecordDetailModel() {
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void requestYd0009(String tradeNo, HttpRequestCallback<FeeBillEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0009);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.PAY_PLAT_TRADE_NO, tradeNo);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getBillInfo(RequestUrl.YD0009, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }
}
