/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.inhospitalrecord.presenter;

import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.ui.inhospitalrecord.contract.InHospitalRecordContract;
import com.wondersgroup.android.sdk.ui.inhospitalrecord.model.InHospitalRecordModel;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class InHospitalRecordPresenter<T extends InHospitalRecordContract.IView>
        extends MvpBasePresenter<T> implements InHospitalRecordContract.IPresenter {

    private static final String TAG = "InHospitalRecordPresent";
    private InHospitalRecordContract.IModel mModel = new InHospitalRecordModel();


}
