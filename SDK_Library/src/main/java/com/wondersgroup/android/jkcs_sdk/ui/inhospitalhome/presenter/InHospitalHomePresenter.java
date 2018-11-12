/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.presenter;

import android.app.Activity;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model.AfterPayHomeModel;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.lang.ref.WeakReference;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面的 Presenter
 */
public class InHospitalHomePresenter<T extends InHospitalHomeContract.IView>
        extends MvpBasePresenter<T> implements InHospitalHomeContract.IPresenter {

    private static final String TAG = "InHospitalHomePresenter";
    //private InHospitalHomeContract.IModel mModel = new InHospitalHomeModel();
    private AfterPayHomeContract.IModel mModel = new AfterPayHomeModel();

    @Override
    public void queryYiBaoOpenStatus(Activity activity) {
        if (activity != null) {
            WeakReference<Activity> weakReference = new WeakReference<>(activity);
            mModel.queryYiBaoOpenStatus(weakReference, status -> {
                if (isNonNull()) {
                    mViewRef.get().onYiBaoOpenStatusResult(status);
                }
            });

        } else {
            LogUtil.e(TAG, "activity is null!");
        }
    }

    @Override
    public void getHospitalList() {
//        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
//            showLoading();
//        }

        mModel.getHospitalList(new OnHospitalListListener() {
            @Override
            public void onSuccess(HospitalEntity body) {
                LogUtil.i(TAG, "get hospital list success~");
                //dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onHospitalListResult(body);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "get hospital list failed!");
                //dismissLoading();
                WToastUtil.show(errCodeDes);
                if (isNonNull()) {
                    mViewRef.get().onHospitalListResult(null);
                }
            }
        });
    }
}
