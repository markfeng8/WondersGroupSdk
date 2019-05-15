package com.wondersgroup.android.jkcs_sdk;

import android.app.Application;
import android.content.Context;

import com.wondersgroup.android.jkcs_sdk.constants.Exceptions;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:Wonders sdk global application.
 */
public class WondersApplication extends Application {

    static Context sContext;

    public WondersApplication() {
        sContext = this;
    }

    public static Context getsContext() {
        checkNotNull(sContext, Exceptions.APPLICATION_CONTEXT_NULL);
        return sContext;
    }

    private static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
