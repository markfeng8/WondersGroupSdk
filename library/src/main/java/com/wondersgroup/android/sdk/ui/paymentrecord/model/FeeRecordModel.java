package com.wondersgroup.android.sdk.ui.paymentrecord.model;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.paymentrecord.contract.FeeRecordContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/18 :)
 * Function:缴费记录(门诊订单)页面数据的 Model
 */
public class FeeRecordModel implements FeeRecordContract.IModel {

    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;
    private BusinessService mService;

    public FeeRecordModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void getFeeRecord(String feeState, String startDate, String endDate, String pageNumber,
                             String pageSize, HttpRequestCallback<FeeRecordEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.FEE_STATE, feeState);
        map.put(MapKey.START_DATE, startDate);
        map.put(MapKey.END_DATE, endDate);
        map.put(MapKey.PAGE_NUMBER, pageNumber);
        map.put(MapKey.PAGE_SIZE, pageSize);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getFeeRecord(RequestUrl.YD0008, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }
}
