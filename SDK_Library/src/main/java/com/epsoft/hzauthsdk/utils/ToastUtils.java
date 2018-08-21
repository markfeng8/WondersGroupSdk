package com.epsoft.hzauthsdk.utils;

import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/7  11:40
 */

public class ToastUtils {
    private static Toast toastM = null;
    private static Toast toast = null;

    public static void showToast(Context context, String text) {
        try{
            if (toastM == null) {
                toastM = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            } else {
                toastM.setText(text);
            }
            Logger.e(text);
            toastM.show();
        }catch (Exception e){}
    }
}
