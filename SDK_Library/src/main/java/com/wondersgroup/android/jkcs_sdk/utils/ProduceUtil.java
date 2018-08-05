package com.wondersgroup.android.jkcs_sdk.utils;

import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:
 */
public class ProduceUtil {

    private static final String TAG = ProduceUtil.class.getSimpleName();

    /**
     * 获取流水号
     * 交易机构编码 + yyyyMMddHH24mmss + 6位随机数
     *
     * @return
     */
    public static String getSid() {
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        String serialNum = OrgConfig.ORG_CODE + TimeUtil.getSecondsTime() + num;
        LogUtil.i(TAG, "sid===" + serialNum);
        return serialNum;
    }
}
