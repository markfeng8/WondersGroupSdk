package com.wondersgroup.android.jkcs_sdk.utils;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:
 */
public class SignUtil {

    private static final String TAG = SignUtil.class.getSimpleName();

    public static String getSign(HashMap<String, String> map) {
        return createSign(map, OrgConfig.KEY);
    }

    /**
     * 生成签名
     *
     * @param param
     * @param key
     * @return
     */
    private static String createSign(HashMap<String, String> param, String key) {
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(param.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : infoIds) {
            String k = item.getKey();
            String v = item.getValue();
            // 空值不传递，不参与签名组串
            if (!TextUtils.isEmpty(k) && !TextUtils.isEmpty(v)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }

        sb = sb.append("key=").append(key);
        LogUtil.i(TAG, "字符串:" + sb.toString());
        // HMAC-SHA256 加密,结果转换为大写字符?
        String sign = sha256_HMAC(sb.toString(), key);
        LogUtil.i(TAG, "HMAC-SHA256 加密值:" + sign);
        return sign;
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            LogUtil.e(TAG, "Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
