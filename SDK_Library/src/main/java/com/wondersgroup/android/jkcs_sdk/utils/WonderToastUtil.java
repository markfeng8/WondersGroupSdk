package com.wondersgroup.android.jkcs_sdk.utils;

import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:Toast utils.
 */
public class WonderToastUtil {

    /**
     * toast short.
     *
     * @param msg
     */
    public static void show(String msg) {
        Toast.makeText(WondersApplication.getsContext(),
                msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast long.
     *
     * @param msg
     */
    public static void showLong(String msg) {
        Toast.makeText(WondersApplication.getsContext(),
                msg, Toast.LENGTH_LONG).show();
    }
}
