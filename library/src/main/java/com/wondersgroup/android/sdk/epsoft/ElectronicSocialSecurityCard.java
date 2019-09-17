/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.epsoft;

import com.wondersgroup.android.sdk.WondersSdk;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

import java.util.HashMap;

import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.callback.ResultType;
import cn.com.epsoft.zjessc.callback.SdkCallBack;
import cn.com.epsoft.zjessc.tools.ZjBiap;
import cn.com.epsoft.zjessc.tools.ZjEsscException;

/**
 * Created by x-sir on 2019-05-23 :)
 * Function:
 */
public class ElectronicSocialSecurityCard {

    private static final String TAG = "ElectronicSocialSecurityCard";
    private CardStatusCallback cardStatusCallback;

    public void enter(MvpBaseActivity activity, CardStatusCallback cardStatusCallback) {
        if (activity == null) {
            return;
        }

        this.cardStatusCallback = cardStatusCallback;

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");

        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(3);
        map.put(MapKey.CHANNEL_NO, WondersSdk.getChannelNo());
        map.put(MapKey.AAC002, idNum);
        map.put(MapKey.AAC003, name);

        SignatureTool.getSign(activity, map, s -> startSdk(activity, idNum, name, s));
    }

    /**
     * 启动SDK
     *
     * @param activity
     * @param idCard   身份证
     * @param name     姓名
     * @param s        签名
     */
    private void startSdk(MvpBaseActivity activity, final String idCard, final String name, String s) {
        LogUtil.i(TAG, "idCard===" + idCard + ",name===" + name + ",s===" + s);
        String url = ZjBiap.getInstance().getIndexUrl();
        LogUtil.i(TAG, "url===" + url);

        ZjEsscSDK.startSdk(activity, idCard, name, url, s, new SdkCallBack() {
            @Override
            public void onLoading(boolean show) {
                if (activity != null) {
                    activity.showLoadingView(show);
                }
            }

            @Override
            public void onResult(@ResultType int type, String data) {
                if (cardStatusCallback != null) {
                    cardStatusCallback.onResult(type, data);
                }
            }

            @Override
            public void onError(String code, ZjEsscException e) {
                LogUtil.i(TAG, "onError():code===" + code + ",errorMsg===" + e.getMessage());
                WToastUtil.show(e.getMessage());
            }
        });
    }

    public interface CardStatusCallback {
        /**
         * 结果的回调
         *
         * @param type
         * @param data
         */
        void onResult(@ResultType int type, String data);
    }
}
