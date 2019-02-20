package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model.AfterPayHomeModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页的 Presenter
 */
public class AfterPayHomePresenter<T extends AfterPayHomeContract.IView>
        extends MvpBasePresenter<T> implements AfterPayHomeContract.IPresenter {

    private static final String TAG = AfterPayHomePresenter.class.getSimpleName();
    private AfterPayHomeContract.IModel mModel = new AfterPayHomeModel();

    public AfterPayHomePresenter() {
    }

    @Override
    public void getAfterPayState(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.getAfterPayState(map, new HttpRequestCallback<AfterPayStateEntity>() {
                @Override
                public void onSuccess(AfterPayStateEntity afterPayStateEntity) {
                    LogUtil.i(TAG, "医后付状态查询成功~");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().afterPayResult(afterPayStateEntity);
                    }
                }

                @Override
                public void onFailed(String errMsg) {
                    LogUtil.e(TAG, "医后付状态查询失败===" + errMsg);
                    dismissLoading();
                    WToastUtil.show(errMsg);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "getAfterPayState():" + Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void requestYd0003(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.requestYd0003(orgCode, new HttpRequestCallback<FeeBillEntity>() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "requestYd0003() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onYd0003Result(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "requestYd0003() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().onYd0003Result(null);
                    }
                }
            });
        } else {
            LogUtil.eLogging(TAG, "requestYd0003():" + Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void getHospitalList(String version, String type) {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }

        mModel.getHospitalList(version, type, new HttpRequestCallback<HospitalEntity>() {
            @Override
            public void onSuccess(HospitalEntity body) {
                LogUtil.i(TAG, "get defaultHospital list success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onHospitalListResult(body);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "get defaultHospital list failed!" + errCodeDes);
                dismissLoading();
                WToastUtil.show(errCodeDes);
                if (isNonNull()) {
                    mViewRef.get().onHospitalListResult(null);
                }
            }
        });
    }

    @Override
    public void uploadMobilePayState() {
        mModel.uploadMobilePayState();
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
