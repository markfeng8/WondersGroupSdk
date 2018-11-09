/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.model.InHospitalHomeModel;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面的 Presenter
 */
public class InHospitalHomePresenter<T extends InHospitalHomeContract.IView>
        extends MvpBasePresenter<T> implements InHospitalHomeContract.IPresenter {

    private static final String TAG = "InHospitalHomePresenter";
    private InHospitalHomeContract.IModel mModel = new InHospitalHomeModel();

}
