/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.epsoft;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.RequestUrl;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.entity.Maps;
import com.wondersgroup.android.sdk.net.RetrofitHelper;
import com.wondersgroup.android.sdk.net.service.SignatureService;
import com.wondersgroup.android.sdk.utils.DateUtils;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.RandomUtils;
import com.wondersgroup.android.sdk.utils.SignUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by x-sir on 2019/3/29 :)
 * Function:
 */
public class SignatureTool {

    private static final String TAG = "SignatureTool";
    private static final String SUCCESS = "SUCCESS";

    public SignatureTool() {

    }

    public static void getSign(final MvpBaseActivity activity, HashMap<String, String> map,
                               final Consumer<String> consumer) {
        if (activity == null) {
            return;
        }

        activity.showLoadingView(true);

        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, RandomUtils.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_SIGN);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        param.put(MapKey.JSON_STR, new Gson().toJson(map));
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        LogUtil.i(TAG, "json===" + new Gson().toJson(param));

        Disposable disposable = RetrofitHelper
                .getInstance()
                .createService(SignatureService.class)
                .getSign(RequestUrl.SIGN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> activity.showLoadingView(false))
                .subscribe(body -> {
                    String returnCode = body.getReturn_code();
                    String resultCode = body.getResult_code();
                    if (SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {
                        consumer.accept(body.getSign());
                    } else {
                        WToastUtil.show(body.getErr_code_des());
                    }
                });
    }
}
