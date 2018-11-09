/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model.LeaveHospitalModel;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class LeaveHospitalPresenter<T extends LeaveHospitalContract.IView>
        extends MvpBasePresenter<T> implements LeaveHospitalContract.IPresenter {

    private static final String TAG = "LeaveHospitalPresenter";
    private LeaveHospitalContract.IModel mModel = new LeaveHospitalModel();
}
