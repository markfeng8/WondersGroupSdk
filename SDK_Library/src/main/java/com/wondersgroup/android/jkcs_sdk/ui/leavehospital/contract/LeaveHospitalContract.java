/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0007Entity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0007RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public interface LeaveHospitalContract {
    interface IModel {
        void requestCy0006(String orgCode, String token, OnCy0006RequestListener listener);

        void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl, OnCy0007RequestListener listener);

        void getPayParam(String orgCode, OnPayParamListener listener);
    }

    interface IView {
        void showLoading();

        void dismissLoading();

        void onCy0006Result(Cy0006Entity entity);

        void onCy0007Result(Cy0007Entity entity);

        void onPayParamResult(PayParamEntity body);

        void timeoutAfter60s();
    }

    interface IPresenter {
        void requestCy0006(String orgCode, String token);

        void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl);

        void getPayParam(String orgCode);
    }
}
