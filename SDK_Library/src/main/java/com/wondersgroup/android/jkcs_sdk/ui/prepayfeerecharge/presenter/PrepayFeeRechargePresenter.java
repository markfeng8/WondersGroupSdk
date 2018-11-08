/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.prepayfeerecharge.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.prepayfeerecharge.contract.PrepayFeeRechargeContract;
import com.wondersgroup.android.jkcs_sdk.ui.prepayfeerecharge.model.PrepayFeeRechargeModel;

/**
 * Created by x-sir on 2018/11/8 :)
 * Function:
 */
public class PrepayFeeRechargePresenter<T extends PrepayFeeRechargeContract.IView>
        extends MvpBasePresenter<T> implements PrepayFeeRechargeContract.IPresenter {

    private static final String TAG = "PrepayFeeRechargePresenter";
    private PrepayFeeRechargeContract.IModel mModel = new PrepayFeeRechargeModel();

}
