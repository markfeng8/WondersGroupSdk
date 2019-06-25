package com.wondersgroup.android.sdk.utils;

import android.content.Context;

import com.wondersgroup.android.sdk.WondersApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by x-sir on 2018/08/14 :)
 * Function: sp存储的工具类
 */
public class SpUtil {

    private static final String WONDERS_GROUP = "WondersGroup";
    private static SpUtil instance = new SpUtil();
    private SecuritySharedPreference mSp;

    private SpUtil() {
    }

    public static SpUtil getInstance() {
        return instance;
    }

    private SecuritySharedPreference getSpObject() {
        if (mSp == null) {
            mSp = new SecuritySharedPreference(WondersApplication.getsContext(), WONDERS_GROUP, Context.MODE_PRIVATE);
        }
        return mSp;
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, Object value) {
        SecuritySharedPreference.SecurityEditor editor = getSpObject().edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value != null ? value.toString() : "");
        }

        editor.apply();
    }

    /**
     * 保存一个 HashMap 集合
     */
    public void save(HashMap<String, Object> map) {
        SecuritySharedPreference.SecurityEditor editor = getSpObject().edit();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else {
                editor.putString(key, value != null ? value.toString() : "");
            }
        }

        editor.apply();
    }

    /**
     * 读取 String 类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return getSpObject().getString(key, defValue);
    }

    /**
     * 读取 boolean 类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return getSpObject().getBoolean(key, defValue);
    }

    /**
     * 读取 int 类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return getSpObject().getInt(key, defValue);
    }

    /**
     * 读取 float 类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        return getSpObject().getFloat(key, defValue);
    }

    /**
     * 读取 long 类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue) {
        return getSpObject().getLong(key, defValue);
    }

    /**
     * 清空sp存储的数据(xxx.xml仍然存在，但是内部没有数据)
     */
    public void clearAll() {
        getSpObject().edit().clear().apply();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key string key
     */
    public void remove(String key) {
        getSpObject().edit().remove(key).apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key string key
     * @return result of local persist storage.
     */
    public boolean contains(String key) {
        return getSpObject().contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return result of local persist storage.
     */
    public Map<String, ?> getAll() {
        return getSpObject().getAll();
    }

    /**
     * 处理加密过渡（从未加密过渡到加密数据，在下次升级前调用一次即可）
     */
    public void handleTransition() {
        boolean handleTransition = getBoolean("handleTransition", false);
        if (!handleTransition) {
            getSpObject().handleTransition();
            save("handleTransition", true);
        }
    }

}
