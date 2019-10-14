/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by x-sir on 2019/10/14.
 * Function:
 */
public class GsonHelper {

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
    private static Gson gson = null;

    public static Gson getDeserializer() {
        if (gson == null) {
            gson = GSON_BUILDER.create();
        }
        return gson;
    }

    public static <T> String arrayToJson(T[] paramArrayOfT) {
        return getDeserializer().toJson(paramArrayOfT);
    }

    public static <T> T jsonToArray(String paramString, Class<T> paramClass) {
        try {
            return getDeserializer().fromJson(paramString, paramClass);
        } catch (Exception localException) {
            return null;
        }
    }

    public static <T> List jsonToList(String paramString, TypeToken<List<T>> paramTypeToken) {
        try {
            return (List) getDeserializer().fromJson(paramString,
                    paramTypeToken.getType());
        } catch (Exception localException) {
            return null;
        }
    }

    public static <K, V> Map<K, V> jsonToMap(String paramString,
                                             TypeToken<Map<K, V>> paramTypeToken) {
        //noinspection unchecked
        return (Map<K, V>) getDeserializer().fromJson(paramString,
                paramTypeToken.getType());
    }

    public static <T> String listToJson(List<T> paramList) {
        return getDeserializer().toJson(paramList);
    }

    public static <K, V> String mapToJson(Map<K, V> paramMap) {
        return getDeserializer().toJson(paramMap);
    }

    public static String writeValue(Object paramObject) {
        return getDeserializer().toJson(paramObject);
    }
}
