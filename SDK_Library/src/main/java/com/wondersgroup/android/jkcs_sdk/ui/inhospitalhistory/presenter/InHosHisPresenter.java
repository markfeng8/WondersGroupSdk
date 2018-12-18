/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.contract.InHosHisContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.model.InHosHisModel;

/**
 * Created by x-sir on 2018/12/18 :)
 * Function:
 */
public class InHosHisPresenter<T extends InHosHisContract.IView>
        extends MvpBasePresenter<T> implements InHosHisContract.IPresenter {

    private static final String TAG = "InHosHisPresenter";
    private InHosHisContract.IModel mModel = new InHosHisModel();
}
