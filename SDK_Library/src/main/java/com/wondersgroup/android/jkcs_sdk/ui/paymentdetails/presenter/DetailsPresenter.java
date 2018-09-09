package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnUnclearedBillListener;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.listener.OnLockOrderListener;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model.DetailsModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class DetailsPresenter<T extends DetailsContract.IView>
        extends MvpBasePresenter<T> implements DetailsContract.IPresenter {

    private static final String TAG = "DetailsPresenter";
    private DetailsContract.IModel mModel = new DetailsModel();

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

    @Override
    public void lockOrder(HashMap<String, Object> map, int totalCount) {
        if (map != null && !map.isEmpty()) {
            mModel.lockOrder(map, totalCount, new OnLockOrderListener() {
                @Override
                public void onSuccess(LockOrderEntity entity) {
                    LogUtil.i(TAG, "lockOrder() -> onSuccess()");
                    if (isNonNull()) {
                        mViewRef.get().lockOrderResult(entity);
                    }
                }

                @Override
                public void onFailed() {
                    LogUtil.e(TAG, "lockOrder() -> onFailed()");
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }
}
