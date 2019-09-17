/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.rechargerecord.presenter;

import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.ui.rechargerecord.contract.RechargeRecordContract;
import com.wondersgroup.android.sdk.ui.rechargerecord.model.RechargeRecordModel;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class RechargeRecordPresenter<T extends RechargeRecordContract.IView>
        extends MvpBasePresenter<T> implements RechargeRecordContract.IPresenter {

    private static final String TAG = "RechargeRecordPresenter";
    private RechargeRecordContract.IModel mModel = new RechargeRecordModel();

}
