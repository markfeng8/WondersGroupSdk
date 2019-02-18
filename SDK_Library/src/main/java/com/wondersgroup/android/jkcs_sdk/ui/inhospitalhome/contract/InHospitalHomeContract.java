/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面接口的契约类
 */
public interface InHospitalHomeContract {
    interface IModel {
        void requestCy0001(String orgCode, String inState, HttpRequestCallback<Cy0001Entity> callback);
    }

    interface IView {
        void showLoading();

        void dismissLoading();

        void onHospitalListResult(HospitalEntity body);

        void onCy0001Result(Cy0001Entity entity);
    }

    interface IPresenter {
        void getHospitalList(String version, String type);

        void requestCy0001(String orgCode, String inState);

        void uploadMobilePayState();
    }
}
