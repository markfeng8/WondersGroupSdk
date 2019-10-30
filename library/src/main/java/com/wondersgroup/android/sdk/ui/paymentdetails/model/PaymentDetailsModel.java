package com.wondersgroup.android.sdk.ui.paymentdetails.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.WondersSdk;
import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.EleCardTokenEntity;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.LockOrderEntity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.sdk.entity.PayParamEntity;
import com.wondersgroup.android.sdk.entity.SettleEntity;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.api.Converter;
import com.wondersgroup.android.sdk.net.callback.AbstractSubscriber;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;

import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面的 Model 类
 */
public class PaymentDetailsModel implements PaymentDetailsContract.IModel {

    private static final String TAG = "PaymentDetailsModel";
    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;
    private BusinessService mService;

    public PaymentDetailsModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mService = RetrofitHelper.getInstance().createService(BusinessService.class);
    }

    @Override
    public void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback) {
        String pageNumber = "1";
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
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.FEE_STATE, OrgConfig.FEE_STATE00);
        map.put(MapKey.START_DATE, OrgConfig.ORDER_START_DATE);
        map.put(MapKey.END_DATE, DateUtils.getCurrentDate());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getBillInfo(RequestUrl.YD0003, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void lockOrder(HashMap<String, Object> map, int totalCount, HttpRequestCallback<LockOrderEntity> callback) {
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0005);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.TOTAL_COUNT, String.valueOf(totalCount));
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        mService.lockOrder(RequestUrl.YD0005, Converter.toBody(map))
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    /**
     * 获取账单明细
     *
     * @param hisOrderNo
     * @param callback
     */
    @Override
    public void getOrderDetails(String hisOrderNo, String orgCode, HttpRequestCallback<OrderDetailsEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0004);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.HIS_ORDER_NO, hisOrderNo);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        mService.getOrderDetails(RequestUrl.YD0004, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void tryToSettle(String token, String orgCode, HashMap<String, Object> map, HttpRequestCallback<SettleEntity> callback) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.TOKEN, token);
        map.put(MapKey.ADVICE_DATE_TIME, adviceDateTime);
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        mService.toSettle(RequestUrl.YD0006, Converter.toBody(map))
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

    @Override
    public void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map, HttpRequestCallback<SettleEntity> callback) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        LogUtil.i(TAG, "adviceDateTime===" + adviceDateTime + ",payPlatTradeNo===" + payPlatTradeNo);

        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0007);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.TO_STATE, toState);
        map.put(MapKey.TOKEN, token);
        map.put(MapKey.ADVICE_DATE_TIME, adviceDateTime);
        // 如果现金支付为 0时，锁单号固定传 0，如果不为0，就传真实锁单号
        map.put(MapKey.PAY_PLAT_TRADE_NO, isPureYiBao ? "0" : payPlatTradeNo);
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        mService.toSettle(RequestUrl.YD0007, Converter.toBody(map))
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>(callback));
    }

    @Override
    public void applyElectronicSocialSecurityCardToken(String businessType, HttpRequestCallback<EleCardTokenEntity> callback) {
        String signNo = SpUtil.getInstance().getString(SpKey.SIGN_NO, "");
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.CHANNEL_NO, WondersSdk.getChannelNo());
        map.put(MapKey.FUNCTION, OrgConfig.CREATE_TOKEN);
        map.put(MapKey.VERSION, "1.0.0");
        map.put(MapKey.SIGN_TYPE, "RSA");
        map.put(MapKey.RANDOM_STR, RandomUtils.getRandomStr(30));
        map.put(MapKey.SER_TYPE, businessType);
        map.put(MapKey.SYS_ID, OrgConfig.YIBAO_SYS_ID);
        map.put(MapKey.CERT_NO, mIdNum);
        map.put(MapKey.NAME, mName);

        if (OrgConfig.SRY.equals(businessType)) {
            map.put(MapKey.SCENE_TYPE, OrgConfig.SCENE_TYPE_SMS);
        } else if (OrgConfig.SRJ.equals(businessType)) {
            map.put(MapKey.SCENE_TYPE, OrgConfig.SCENE_TYPE_PWD);
        }

        map.put(MapKey.SIGN_NO, signNo);
        if (OrgConfig.SRJ.equals(businessType)) {
            String busiSeq = SpUtil.getInstance().getString(SpKey.BUSI_SEQ, "");
            map.put(MapKey.BUSI_SEQ, busiSeq);
        }
        map.put(MapKey.SIGN, SignUtil.createSignWithRsa(map));

        String json = new Gson().toJson(map);
        LogUtil.i(TAG, "json===" + json);

        mService.getToken(RequestUrl.CHECK_SIGN_API, map)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new AbstractSubscriber<Response<EleCardTokenEntity>>() {
                    @Override
                    public void onNext(Response<EleCardTokenEntity> response) {
                        int code = response.code();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && successful) {
                            EleCardTokenEntity body = response.body();
                            if (body != null) {
                                String returnCode = body.getCode();
                                if ("0".equals(returnCode)) {
                                    if (callback != null) {
                                        callback.onSuccess(body);
                                    }
                                } else {
                                    String errCodeDes = body.getErrCode();
                                    String msg = body.getMsg();
                                    String errorInfo = errCodeDes + ":" + msg;
                                    if (!TextUtils.isEmpty(errorInfo)) {
                                        if (callback != null) {
                                            callback.onFailed(errorInfo);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailed("服务器异常！");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (callback != null) {
                                callback.onFailed(error);
                            }
                        }
                    }
                });
    }

}
