package com.wondersgroup.android.jkcs_sdk.utils;

import com.wondersgroup.android.jkcs_sdk.constants.OrgConfig;

import java.util.Random;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:随机字符串工具类
 */
public class RandomUtils {

    private static final String TAG = RandomUtils.class.getSimpleName();

    /**
     * 获取流水号
     * 交易机构编码 + yyyyMMddHH24mmss + 6位随机数
     *
     * @return
     */
    public static String getSid() {
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        String serialNum = OrgConfig.ORG_CODE + DateUtils.getTheNearestSecondTime() + num;
        LogUtil.i(TAG, "sid===" + serialNum);
        return serialNum;
    }

    public static String getRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return DateUtils.getTheNearestSecondTime2() + sb.toString();
    }
}
