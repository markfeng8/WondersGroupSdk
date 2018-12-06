/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model.LeaveHospitalModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class LeaveHospitalPresenter<T extends LeaveHospitalContract.IView>
        extends MvpBasePresenter<T> implements LeaveHospitalContract.IPresenter {

    private static final String TAG = "LeaveHospitalPresenter";
    private LeaveHospitalContract.IModel mModel = new LeaveHospitalModel();

    @Override
    public void requestCy0006(String orgCode, String token) {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }

        mModel.requestCy0006(orgCode, token, new OnCy0006RequestListener() {
            @Override
            public void onSuccess(Cy0006Entity entity) {
                LogUtil.i(TAG, "requestCy0006() -> success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onCy0006Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0006() -> failed!");
                dismissLoading();
                WToastUtil.show(errCodeDes);
            }
        });
    }

    private void showLoading() {
        if (isNonNull()) {
            mViewRef.get().showLoading();
        }
    }

    private void dismissLoading() {
        if (isNonNull()) {
            mViewRef.get().dismissLoading();
        }
    }
}
