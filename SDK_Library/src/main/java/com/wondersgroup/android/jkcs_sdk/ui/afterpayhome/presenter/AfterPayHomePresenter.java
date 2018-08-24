package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnMobilePayStateListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnUnclearedBillListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model.AfterPayHomeModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

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
        showLoading();
        if (map != null && !map.isEmpty()) {
            mModel.getAfterPayState(map, new OnAfterPayStateListener() {
                @Override
                public void onSuccess(AfterPayStateEntity entity) {
                    if (isNonNull()) {
                        mViewRef.get().afterPayResult(entity);
                    }
                    dismissLoading();
                }

                @Override
                public void onFailed() {
                    dismissLoading();
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void getMobilePayState(HashMap<String, String> map) {
        showLoading();
        if (map != null && !map.isEmpty()) {
            mModel.getMobilePayState(map, new OnMobilePayStateListener() {
                @Override
                public void onSuccess(MobilePayEntity entity) {
                    if (isNonNull()) {
                        mViewRef.get().mobilePayResult(entity);
                    }
                    dismissLoading();
                }

                @Override
                public void onFailed() {
                    dismissLoading();
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void getUnclearedBill(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            mModel.getUnclearedBill(map, new OnUnclearedBillListener() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "getUnclearedBill() -> onSuccess()");
                    if (isNonNull()) {
                        mViewRef.get().feeBillResult(entity);
                    }
                }

                @Override
                public void onFailed() {
                    LogUtil.e(TAG, "getUnclearedBill() -> onFailed()");
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
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
