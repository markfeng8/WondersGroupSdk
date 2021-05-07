/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.epsoft;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.EleCardEntity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.externparams.WondersExtern;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.callback.ApiSubscriber;
import com.wondersgroup.android.sdk.net.service.BusinessService;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.RSAUtils;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.RxThreadUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.StringUtils;
import com.wondersgroup.android.sdk.utils.WToastUtil;

import java.util.HashMap;

import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.callback.SdkCallBack;
import cn.com.epsoft.zjessc.tools.ZjBiap;
import cn.com.epsoft.zjessc.tools.ZjEsscException;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by x-sir on 2019-05-23 :)
 * Function:电子社保卡业务处理类
 */
public class ElectronicSocialSecurityCard {

    private static final String TAG = "ElectronicSocialSecurityCard";
    private static final String SUCCESS = "SUCCESS";

    /**
     * 进入电子社保卡首页
     *
     * @param activity Activity 对象
     */
    public void enter(Activity activity) {
        final String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        final String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
//
//        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(3);
////        map.put(MapKey.CHANNEL_NO, WondersSdk.getChannelNo());
//        map.put(MapKey.CHANNEL_NO, WondersImp.getExternParams().getChannelNo());
//        map.put(MapKey.AAC002, idNum);
//        map.put(MapKey.AAC003, name);
//
//        getSign(map, s -> startSdk(activity, idNum, name, s));
        // TODO: 2021/5/6  外部修改参数获取记录
        startSdk(activity, idNum, name, WondersExtern.getExternParams().getSign());
    }

    /**
     * 启动 SDK
     *
     * @param activity Activity
     * @param idCard   身份证
     * @param name     姓名
     * @param s        签名
     */
    private void startSdk(Activity activity, final String idCard, final String name, String s) {
        LogUtil.i(TAG, "idCard===" + idCard + ",name===" + name + ",s===" + s);
        String url = ZjBiap.getInstance().getMainUrl();
        LogUtil.i(TAG, "url===" + url);

        ZjEsscSDK.startSdk(activity, idCard, name, url, s, new SdkCallBack() {
            @Override
            public void onLoading(boolean show) {

            }

            @Override
            public void onResult(String data) {
                handleAction(data);
            }

            @Override
            public void onError(String code, ZjEsscException e) {
                LogUtil.i(TAG, "onError():code===" + code + ",errorMsg===" + e.getMessage());
                WToastUtil.show(e.getMessage());
            }
        });
    }

    /**
     * 获取校验电子社保卡密码的参数
     *
     * @return HashMap
     */
    public static HashMap<String, String> getVerifyElectronicSocialSecurityCardPasswordParams() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String signNo = SpUtil.getInstance().getString(SpKey.SIGN_NO, "");

        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(6);
//        map.put(MapKey.CHANNEL_NO, WondersSdk.getChannelNo());
        map.put(MapKey.CHANNEL_NO, WondersExtern.getExternParams().getChannelNo());
        map.put(MapKey.AAC002, idNum);
        map.put(MapKey.AAC003, name);
        //map.put(MapKey.AAB301, "330500");
        map.put(MapKey.AAB301, "330999");
        map.put(MapKey.SIGN_NO, signNo);
        map.put(MapKey.IS_INDEP, "1");

        return map;
    }

    /**
     * 签发回调处理
     */
    private void handleAction(String data) {
        LogUtil.i(TAG, "data===" + data);
        EleCardEntity eleCardEntity = new Gson().fromJson(data, EleCardEntity.class);
        String actionType = eleCardEntity.getActionType();
        switch (actionType) {
            case "006":/*到卡面，通过其他途径申领过社保卡导致后台状态不同步还是未开通状态，
            此时SDK返回006说明已经开通，此时状态同001，需要APP端通知后台更新状态*/
                // 电子社保卡申领完成（一级签发）
            case "001":
                parseResult(eleCardEntity);
                break;
            // 直接验密签发(指在其他渠道已领取，然后在当前渠道签发)，和 001 一样需要上传 signNo
            case "002":
                parseResult(eleCardEntity);
                break;
            // 解除绑定完成
            case "003":
                requestYd0002(OrgConfig.STATE_CLOSE);
                break;
            // 开通缴费结算功能(二级签发)
            case "005":
                parseResult(eleCardEntity);
                break;
            default:
                break;
        }
    }

    private void parseResult(EleCardEntity eleCardEntity) {
        String signNo = eleCardEntity.getSignNo();
        String aab301 = eleCardEntity.getAab301();
        LogUtil.i(TAG, "signNo===" + signNo + ",aab301===" + aab301);
        SpUtil.getInstance().save(SpKey.SIGN_NO, signNo);
        requestYd0002(OrgConfig.STATE_OPEN);
    }

    private void requestYd0002(String state) {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        String signNo = SpUtil.getInstance().getString(SpKey.SIGN_NO, "");
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");

        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, RandomUtils.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0002);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_NO, StringUtils.getMosaicIdNum(idNum));
        param.put(MapKey.SIGN_ID_NO, RSAUtils.encrypt(idNum));
        param.put(MapKey.CARD_NO, cardNum);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.CARD_TYPE, cardType);
        param.put(MapKey.MOBILE_PAY_TIME, DateUtils.getCurrentDate());
        // 01 代表开通
        param.put(MapKey.MOBILE_PAY_STATUS, state);
        // 01 已开通 00：未开通
        param.put(MapKey.ELE_CARD_STATUS, state);
        // 签发号
        if (!TextUtils.isEmpty(signNo)) {
            param.put(MapKey.SIGNATURE_NO, signNo);
        }
        param.put(MapKey.VERSION, OrgConfig.GLOBAL_API_VERSION);
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        String json = new Gson().toJson(param);
        LogUtil.json(TAG, json);

        RetrofitHelper
                .getInstance()
                .createService(BusinessService.class)
                .findMobilePayState(RequestUrl.YD0002, param)
                .compose(RxThreadUtils.flowableToMain())
                .subscribe(new ApiSubscriber<>());
    }

    /**
     * 获取签名串
     *
     * @param map      HashMap
     * @param consumer Consumer
     */
    public static void getSign(HashMap<String, String> map, final Consumer<String> consumer) {
        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, RandomUtils.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_SIGN);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        param.put(MapKey.JSON_STR, new Gson().toJson(map));
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        LogUtil.d(TAG, "json===" + new Gson().toJson(param));

        Disposable disposable =
                RetrofitHelper
                        .getInstance()
                        .createService(BusinessService.class)
                        .getSign(RequestUrl.SIGN, param)
                        .compose(RxThreadUtils.observableToMain())
                        .subscribe(body -> {
                            String returnCode = body.getReturnCode();
                            String resultCode = body.getResultCode();
                            if (SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {
                                consumer.accept(body.getSign());
                            } else {
                                WToastUtil.show(body.getErrCodeDes());
                            }
                        });
    }
}
