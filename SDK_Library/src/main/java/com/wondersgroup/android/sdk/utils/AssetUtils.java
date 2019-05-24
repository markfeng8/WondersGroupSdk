/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by x-sir on 2019/01/21 :)
 * Function:
 */
public class AssetUtils {

    /**
     * 读取本地 assets 目录文件的方法
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return json
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {
        if (bgAlpha == 1f) {
            clearDim((Activity) mContext);
        } else {
            applyDim((Activity) mContext, bgAlpha);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private static void applyDim(Activity activity, float bgAlpha) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            // activity跟布局
            Drawable dim = new ColorDrawable(Color.BLACK);
            dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            dim.setAlpha((int) (255 * bgAlpha));
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.add(dim);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private static void clearDim(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.clear();
        }
    }
}
