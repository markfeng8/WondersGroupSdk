/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public interface LeaveHospitalContract {
    interface IModel {
        void requestCy0006(String orgCode, String token, OnCy0006RequestListener listener);
    }

    interface IView {
        void showLoading();

        void dismissLoading();

        void onCy0006Result(Cy0006Entity entity);
    }

    interface IPresenter {
        void requestCy0006(String orgCode, String token);
    }
}
