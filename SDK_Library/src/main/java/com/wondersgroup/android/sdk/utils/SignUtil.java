/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.constants.OrgConfig;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final String SIGN = "sign";

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCm/lee01uUd5kChouKJC9eg4wQ\n" +
            "xshspcD5GKUYuhkEK68zztqtGYYbFWeAxPpVbOf29+FgxUv5DIzRTU7AQFVF775z\n" +
            "laGqVXBp1CZ6exV4yk/5v3CpHc4jNetH8YM+7tVXNkSoYajXQ2gdd6FG2rm7IF2z\n" +
            "fLOEmxq17n5yG8m8pwIDAQAB";

    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKb+V57TW5R3mQKG\n" +
            "i4okL16DjBDGyGylwPkYpRi6GQQrrzPO2q0ZhhsVZ4DE+lVs5/b34WDFS/kMjNFN\n" +
            "TsBAVUXvvnOVoapVcGnUJnp7FXjKT/m/cKkdziM160fxgz7u1Vc2RKhhqNdDaB13\n" +
            "oUbaubsgXbN8s4SbGrXufnIbybynAgMBAAECgYAQKj7BGSScUpd1LyEC1k3fkExW\n" +
            "s2djXQg5FLGmmx0g0jm7giKY7weUR5YlWOwCqPArcANZIsAf858OA7XD1nZqjqqU\n" +
            "HSauvtIC6+skhTSLd55xHd3vUb8VNkxPvJh7Geg3FFBsSdBh/GMB29yIGHe7TY2O\n" +
            "p8Dyu4WPjVIL5/8wQQJBANCYNdLdpgMxbguDQ8+yZKlcc5WNWKmQL8wf5Kx9ngFG\n" +
            "53lP7Ui0Dfqm9OsYmZFKx4jZTa8PBZWWlukrefef6QsCQQDM8dTairg8Q27K+bZ+\n" +
            "eK5FWnm48ASaZliQJi5KLavOwRSEzfeywU3FzxxTyB0a9CBAUd4AqjTqEG0HMlCD\n" +
            "RZRVAkEAx3qYFlRS20Dc/POF2My/yNqZyk4GmPlDTFC/rVjfzlbRK8kMoPdXWvlo\n" +
            "xJ6c1T4O/UbaLGwQKhJ9tPQXyn/JKwJAce29tf+Hi3ixDoTivku4FTXGlNhYtrxO\n" +
            "X1PaR8I70CPllC4rlGOBKIWJ6clE5MbxZtAb6aK056lZ8rY1q8PyQQJBAMfYLfrr\n" +
            "8j6Fs+srS2+U7pFU53RHCjh9/m66toReaD8J1bRwAtnHBxjOTZGyfYsBjEvGu5kV\n" +
            "H2+8MYkh52Mv1TI=";

    /**
     * 获取一般 Json 数据的签名
     *
     * @param map
     * @return
     */
    public static String getSign(HashMap<String, String> map) {
        return createSign(map, OrgConfig.KEY);
    }

    /**
     * 获取带数组对象 Json 数据的签名
     *
     * @param param
     * @return
     */
    public static String getSignWithObject(HashMap<String, Object> param) {
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(param.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> item : infoIds) {
            String k = item.getKey();
            Object v = item.getValue();

            // 处理 Value 是 List 的情况
            if (v instanceof List) {
                List list = (List) v;
                String listSortValue = "";
                StringBuilder strBuilder = new StringBuilder();

                List<String> sortList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Object object = list.get(i);
                    if (object instanceof Map) {
                        Map map = (Map) object;
                        String suffixKey = k + "[" + i + "].";
                        String objectSort = getMapObjectSort(suffixKey, map);
                        sortList.add(objectSort);
                    }
                }

                // 1.对 sortList 按字典序进行排序
                Collections.sort(sortList, String::compareTo);

                // 循环拼接排序好的 List
                for (String str : sortList) {
                    strBuilder.append(str);
                }

                listSortValue = strBuilder.toString();

                // 空值不传递，不参与签名组串
                if (!TextUtils.isEmpty(listSortValue)) {
                    sb.append(listSortValue);
                }

            } else { // 处理 Value 是其他类型的情况
                // 空值不传递，不参与签名组串
                if (!TextUtils.isEmpty(k) && !TextUtils.isEmpty((String) v)) {
                    sb.append(k).append("=").append(v).append("&");
                }
            }
        }

        sb = sb.append("key=").append(OrgConfig.KEY);
        LogUtil.i(TAG, "字符串:" + sb.toString());
        // HMAC-SHA256 加密,结果转换为大写字符?
        String sign = sha256_HMAC(sb.toString(), OrgConfig.KEY);
        LogUtil.i(TAG, "HMAC-SHA256 加密值:" + sign);
        return sign;
    }

    /**
     * 获取 json 数组中对象是 Map 的排序字符串
     *
     * @param suffixKey
     * @param param
     * @return
     */
    private static String getMapObjectSort(String suffixKey, Map param) {
        @SuppressWarnings("unchecked")
        List<Map.Entry> infoIds = new ArrayList<>(param.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, (o1, o2) -> ((String) o1.getKey()).compareTo((String) o2.getKey()));

        StringBuilder sb = new StringBuilder();

        for (Map.Entry item : infoIds) {
            Object k = item.getKey();
            Object v = item.getValue();

            // 空值不传递，不参与签名组串
            if (!TextUtils.isEmpty((String) k) && !TextUtils.isEmpty((String) v)) {
                sb.append(suffixKey).append(k).append("=").append(v).append("&");
            }
        }

        return sb.toString();
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
        Collections.sort(infoIds, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));

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
     * 生成 RSA 签名
     *
     * @param sortedParam
     * @return
     */
    public static String createSignWithRsa(Map sortedParam) {
        StringBuilder content = new StringBuilder();
        @SuppressWarnings("unchecked")
        List<String> keys = new ArrayList<String>(sortedParam.keySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(keys);

        int index = 0;

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (SIGN.endsWith(key)) {
                continue;
            }
            String value = String.valueOf(sortedParam.get(key));
            // 空值不传递，不参与签名组串
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }

        String sbString = content.toString();
        LogUtil.i(TAG, "拼接后的字符串:" + sbString);

        // RSA 加密
        String encryption = RSAUtils.sign(sbString, PRIVATE_KEY);
        LogUtil.i(TAG, "encryption===" + encryption);

        return encryption;
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
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            hmacSHA256.init(secretKey);
            byte[] bytes = hmacSHA256.doFinal(message.getBytes());
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
        String tmpStr;
        for (int n = 0; b != null && n < b.length; n++) {
            tmpStr = Integer.toHexString(b[n] & 0XFF);
            if (tmpStr.length() == 1) {
                hs.append('0');
            }
            hs.append(tmpStr);
        }
        return hs.toString().toLowerCase();
    }
}
