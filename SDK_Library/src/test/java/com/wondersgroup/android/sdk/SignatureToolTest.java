/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk;

import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.epsoft.SignatureTool;

import org.junit.Test;

import java.util.HashMap;

import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.callback.ResultType;
import cn.com.epsoft.zjessc.callback.SdkCallBack;
import cn.com.epsoft.zjessc.tools.ZjEsscException;

/**
 * Created by x-sir on 2019/3/29 :)
 * Function:
 */
public class SignatureToolTest {

    private static final String ID_CARD = "123";
    private static final String NAME = "张三";
    private static final String URL = "";
    MvpBaseActivity activity = null;

    @Test
    public void sign() {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put("channelNo", "123");
        SignatureTool.getSign(activity, map, s -> {
            startSdk(ID_CARD, NAME, s);
        });
    }

    /**
     * 启动SDK
     *
     * @param idCard 身份证
     * @param name   姓名
     * @param s      签名
     */
    private void startSdk(final String idCard, final String name, String s) {
        ZjEsscSDK.startSdk(activity, idCard, name, URL, s, new SdkCallBack() {
            @Override
            public void onLoading(boolean show) {
                //showProgress(show);
            }

            @Override
            public void onResult(@ResultType int type, String data) {
                if (type == ResultType.ACTION) {
                    //handleAction(data);
                } else if (type == ResultType.SCENE) {
                    //handleScene(data);
                }
            }

            @Override
            public void onError(String code, ZjEsscException e) {

            }
        });
    }
}
