package com.wondersgroup.android.sdk.utils;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.entity.WondersExternParams;

public class WondersImp {

    private static final String TAG = "WondersImp";

    private static WondersParamsImp mWondersParamsImp;
    private static final String CHANNEL_NO = "03";
    private static final String QDCODE = "03";

    private static WondersExternParams wondersExternParams = new WondersExternParams();

    public interface WondersParamsImp {
        WondersExternParams getExternParams();
    }

    public static void setWondersExternParamsImp(WondersParamsImp wondersParamsImp) {
        mWondersParamsImp = wondersParamsImp;
    }

    /**
     * 获取接口实现后返回的参数，未实现接口时，返回默认参数
     *
     * @return
     */
    public static WondersExternParams getExternParams() {
        if (mWondersParamsImp != null) {
            WondersExternParams params = mWondersParamsImp.getExternParams();
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
