/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.contract;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;

/**
 * Created by x-sir on 2018/12/18 :)
 * Function:
 */
public interface InHosHisContract {
    interface IModel {
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
    }
}
