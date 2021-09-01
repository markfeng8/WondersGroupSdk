package com.wondersgroup.android.sdk.ui.afterpayhome.model;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.entity.Yd0001Entity;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.RSAUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页数据的 Model 类
 */
public class AfterPayHomeModel implements AfterPayHomeContract.IModel {

    private String mName;
    private String mIdType;
    private String mIdNum;
    private BusinessService mService;

    public AfterPayHomeModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Override
    public void requestXy0001(HashMap<String, String> map, HttpRequestCallback<AfterPayStateEntity> callback) {
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0001);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(mIdNum));
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        // 此处时为了判断是否已经请求过一次，是否将 sign 加入 map 中，第二次请求时需要剔除 sign，因为 sign 不参与签名！
        if (map.containsKey(MapKey.SIGN)) {
            map.remove(MapKey.SIGN);
        }
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.findAfterPayState(RequestUrl.XY0001, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void requestYd0001(HttpRequestCallback<Yd0001Entity> callback) {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0001);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, StringUtils.getMosaicIdNum(mIdNum));
        map.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(mIdNum));
        map.put(MapKey.CARD_TYPE, cardType);
        map.put(MapKey.CARD_NO, cardNum);
        map.put(MapKey.VERSION, OrgConfig.GLOBAL_API_VERSION);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.yd0001(RequestUrl.YD0001, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback) {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        // 页数
        String pageNumber = "1";
        // 每页的条数
        String pageSize = "100";
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.PAGE_NUMBER, pageNumber);
        map.put(MapKey.PAGE_SIZE, pageSize);
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0003);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, StringUtils.getMosaicIdNum(mIdNum));
        map.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(mIdNum));
        map.put(MapKey.CARD_TYPE, cardType);
        map.put(MapKey.CARD_NO, cardNum);
        map.put(MapKey.FEE_STATE, OrgConfig.FEE_STATE00);
        map.put(MapKey.START_DATE, DateUtils.getCurMonthBeforeM(3));
        map.put(MapKey.END_DATE, DateUtils.getCurrentDate());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getBillInfo(RequestUrl.YD0003, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    /**
     * 旧版本获取医院列表接口
     * recommend use {@link AfterPayHomeModel}
     */
    @Deprecated
    public void getHospitalListOld(HttpRequestCallback<HospitalEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getHosList(RequestUrl.XY0008, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void getHospitalList(String version, String type, HttpRequestCallback<HospitalEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(8);
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        // 兼容旧版本接口，如果传空就说明请求的是旧接口
        if (!TextUtils.isEmpty(version)) {
            map.put(MapKey.VERSION, version);
        }
        map.put(MapKey.TYPE, type);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getHosList(RequestUrl.XY0008, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

}
