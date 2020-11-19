/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.leavehospital.contract;

import com.wondersgroup.android.sdk.entity.Cy0006Entity;
import com.wondersgroup.android.sdk.entity.Cy0007Entity;
import com.wondersgroup.android.sdk.entity.EleCardTokenEntity;
import com.wondersgroup.android.sdk.entity.PayParamEntity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public interface LeaveHospitalContract {
    interface IModel {
        void requestCy0006(String orgCode, String token, HttpRequestCallback<Cy0006Entity> callback);

        void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl, HttpRequestCallback<Cy0007Entity> callback);

        void getPayParam(String orgCode, HttpRequestCallback<PayParamEntity> callback);
    }

    interface IView {
        void showLoading(boolean show);

        void onCy0006Result(Cy0006Entity entity);

        void onCy0007Result(Cy0007Entity entity);

        void onPayParamResult(PayParamEntity body);

        void timeoutAfter60s();

        void onApplyElectronicSocialSecurityCardToken(EleCardTokenEntity body);
    }

    interface IPresenter {
        void requestCy0006(String orgCode, String token);

        void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl);

        void getPayParam(String orgCode);

        void applyElectronicSocialSecurityCardToken(String businessType,String hiCode);
    }
}
