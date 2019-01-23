package com.wondersgroup.android.jkcs_sdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by x-sir on 2018/08/14 :)
 * Function: sp存储的工具类
 */
public class SpUtil {

    private static final String WONDERS_GROUP = "WondersGroup";
    private static SpUtil instance = new SpUtil();
    private static SharedPreferences mSp = null;

    private SpUtil() {
    }

    public static SpUtil getInstance() {
        if (mSp == null) {
            mSp = WondersApplication.getsContext().getSharedPreferences(WONDERS_GROUP, Context.MODE_PRIVATE);
        }
        return instance;
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @SuppressLint("ApplySharedPref")
    public void save(String key, Object value) {
        if (value == null) return;
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }

    /**
     * 保存一个 HashMap 集合
     */
    public void save(HashMap<String, Object> map) {
        SharedPreferences.Editor editor = mSp.edit();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            }
        }

        editor.apply();
    }

    /**
     * 读取String类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    /**
     * 读取boolean类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    /**
     * 读取boolean类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    /**
     * 清空sp存储的数据(xxx.xml仍然存在，但是内部没有数据)
     */
    @SuppressLint("ApplySharedPref")
    public void clearAll() {
        mSp.edit().clear().commit();
    }

}
