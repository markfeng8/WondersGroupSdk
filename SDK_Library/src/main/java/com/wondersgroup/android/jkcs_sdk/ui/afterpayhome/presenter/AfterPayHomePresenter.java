package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnMobilePayStateListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model.AfterPayHomeModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
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
            showLoading();
            mModel.getAfterPayState(map, new OnAfterPayStateListener() {
                @Override
                public void onSuccess(AfterPayStateEntity entity) {
                    LogUtil.i(TAG, "医后付状态查询成功~");
                    if (isNonNull()) {
                        mViewRef.get().afterPayResult(entity);
                    }
                    dismissLoading();
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "医后付状态查询失败===" + errCodeDes);
                    dismissLoading();
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void uploadMobilePayState(String status) {
        if (!TextUtils.isEmpty(status)) {
            mModel.uploadMobilePayState(status, new OnMobilePayStateListener() {
                @Override
                public void onSuccess() {
                    LogUtil.i(TAG, "移动医保状态上报成功~");
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "移动医保状态上报失败~");
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void getUnclearedBill(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            showLoading();
            mModel.getUnclearedBill(map, new OnFeeDetailListener() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "getUnclearedBill() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().feeBillResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getUnclearedBill() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().feeBillResult(null);
                    }
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void getHospitalList() {
        showLoading();
        mModel.getHospitalList(new OnHospitalListListener() {
            @Override
            public void onSuccess(HospitalEntity body) {
                LogUtil.i(TAG, "get hospital list success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onHospitalListResult(body);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "get hospital list failed!");
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
