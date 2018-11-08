package com.wondersgroup.android.jkcs_sdk.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;


/**
 * Created by x-sir on 2018-07-31 :)
 * Function:logger printer.
 */
public class LogUtil {

    private static final String DEFAULT_TAG = "Wonders=SDK=log=";
    private static boolean IS_NEED_PRINT_LOG = false; // default

    public static void setIsNeedPrintLog(boolean isNeedPrintLog) {
        IS_NEED_PRINT_LOG = isNeedPrintLog;
    }

    public static void v(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.v(DEFAULT_TAG, msg);
        }
    }

    public static void d(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    public static void i(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.e(DEFAULT_TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.i(getTag(tag), msg);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.w(getTag(tag), msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.e(getTag(tag), msg);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.v(getTag(tag), msg);
        }
    }

    public static void d(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Log.d(getTag(tag), msg);
        }
    }

    public static void iLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).i(msg);
        }
    }

    public static void wLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).w(msg);
        }
    }

    public static void eLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).e(msg);
        }
    }

    public static void vLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).v(msg);
        }
    }

    public static void dLogging(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).d(msg);
        }
    }

    public static void json(String tag, String msg) {
        if (IS_NEED_PRINT_LOG) {
            Logger.t(tag).json(msg);
        }
    }

    private static String getTag(String tag) {
        return DEFAULT_TAG + tag;
    }
}
