/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.contract.SelfPayFeeContract;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.model.SelfPayFeeModel;

/**
 * Created by x-sir on 2018/10/31 :)
 * Function:
 */
public class SelfPayFeePresenter<T extends SelfPayFeeContract.IView>
        extends MvpBasePresenter<T> implements SelfPayFeeContract.IPresenter {

    private static final String TAG = "SelfPayFeePresenter";
    private SelfPayFeeContract.IModel mModel = new SelfPayFeeModel();


}
