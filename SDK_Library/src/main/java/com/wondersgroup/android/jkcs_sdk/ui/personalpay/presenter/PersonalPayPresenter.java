package com.wondersgroup.android.jkcs_sdk.ui.personalpay.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract.PersonalPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.model.PersonalPayModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public class PersonalPayPresenter<T extends PersonalPayContract.IView>
        extends MvpBasePresenter<T> implements PersonalPayContract.IPresenter {

    private static final String TAG = "PersonalPayPresenter";
    private PersonalPayContract.IModel mModel = new PersonalPayModel();

    @Override
    public void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            showLoading();
            mModel.sendOfficialPay(token, orgCode, map, new OnSettleListener() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "sendOfficialPay() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onOfficialSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendOfficialPay() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
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
