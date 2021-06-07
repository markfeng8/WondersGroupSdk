package com.wondersgroup.android.sdk;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.entity.WondersExternParams;
import com.wondersgroup.android.sdk.entity.WondersOutParams;

/**
 * 提供对载体输出参数，和载体对sdk输入参数的回调
 * WondersExternParams 外部入参实体类
 * WondersOutParams    SDK对外出参实体类
 */
public class WondersImp {

    private static final String TAG = "WondersImp";

    private static WondersParamsImp mWondersParamsImp;
    private static final String CHANNEL_NO = "3309000201";
    private static final String QDCODE = "01";

    private static WondersExternParams wondersExternParams = new WondersExternParams();

    public interface WondersParamsImp {
        WondersExternParams getExternParams(WondersOutParams outParams, WondersSignImp signImp);
    }

    public interface WondersSignImp {
        void getSignParams(WondersExternParams wondersExternParams);
    }

    public static void setWondersExternParamsImp(WondersParamsImp wondersParamsImp) {
        mWondersParamsImp = wondersParamsImp;
    }

    /**
     * 获取接口实现后返回的参数，未实现接口时，返回默认参数
     *
     * @return
     */
    public static WondersExternParams getExternParams(WondersOutParams outParams, WondersSignImp signImp) {
        if (mWondersParamsImp != null) {
            WondersExternParams params = mWondersParamsImp.getExternParams(outParams, signImp);
            if (params != null) {
                if (!TextUtils.isEmpty(params.getChannelNo())) {
                    wondersExternParams.setChannelNo(params.getChannelNo());
                }
                if (!TextUtils.isEmpty(params.getQDCODE())) {
                    wondersExternParams.setQDCODE(params.getQDCODE());
                }
                if (!TextUtils.isEmpty(params.getSign())) {
                    wondersExternParams.setSign(params.getSign());
                }
            }
        } else {
            wondersExternParams.setChannelNo(CHANNEL_NO);
            wondersExternParams.setQDCODE(QDCODE);
        }
        return wondersExternParams;
    }

}
