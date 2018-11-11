/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract;

import android.app.Activity;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面接口的契约类
 */
public interface InHospitalHomeContract {
    interface IModel {
    }

    interface IView {
        void onYiBaoOpenStatusResult(String status);
    }

    interface IPresenter {
        void queryYiBaoOpenStatus(Activity activity);
    }
}
