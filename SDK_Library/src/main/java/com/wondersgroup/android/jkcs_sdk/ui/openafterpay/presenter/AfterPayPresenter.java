package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.listener.OnOpenAfterPayListener;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.listener.OnSmsSendListener;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.model.AfterPayModel;
import com.wondersgroup.android.jkcs_sdk.utils.WonderToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class AfterPayPresenter<T extends AfterPayContract.IView>
        extends MvpBasePresenter<T> implements AfterPayContract.IPresenter {

    private AfterPayContract.IModel mModel = new AfterPayModel();

    public AfterPayPresenter() {
    }

    @Override
    public void sendSmsCode() {
        String phoneNumber = "";
        if (isNonNull()) {
            phoneNumber = mViewRef.get().getPhoneNumber();
        }

        if (!TextUtils.isEmpty(phoneNumber)) {
            mModel.sendSmsCode(phoneNumber, new OnSmsSendListener() {
                @Override
                public void onSuccess() {
                    WonderToastUtil.show("发送成功！");
                }

                @Override
                public void onFailed() {
                    WonderToastUtil.show("发送失败！");
                }
            });
        } else {
            WonderToastUtil.show(WondersApplication.getsContext()
                    .getString(R.string.wonders_text_phone_number_nullable));
        }

    }

    @Override
    public void openAfterPay(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            mModel.openAfterPay(map, new OnOpenAfterPayListener() {
                @Override
                public void onSuccess() {
                    WonderToastUtil.show("开通成功！");
                }

                @Override
                public void onFailed() {
                    WonderToastUtil.show("开通失败！");
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }
}
