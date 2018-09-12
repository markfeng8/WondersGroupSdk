package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.listener.OnOpenAfterPayListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSmsSendListener;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.model.AfterPayModel;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

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
                    WToastUtil.show("发送成功！");
                }

                @Override
                public void onFailed() {
                    WToastUtil.show("发送失败！");
                }
            });
        } else {
            WToastUtil.show(WondersApplication.getsContext()
                    .getString(R.string.wonders_text_phone_number_nullable));
        }

    }

    @Override
    public void openAfterPay(String phone, String idenCode) {
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(idenCode)) {
            mModel.openAfterPay(phone, idenCode, new OnOpenAfterPayListener() {
                @Override
                public void onSuccess() {
                    WToastUtil.show("开通成功！");
                }

                @Override
                public void onFailed() {
                    WToastUtil.show("开通失败！");
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }
}
