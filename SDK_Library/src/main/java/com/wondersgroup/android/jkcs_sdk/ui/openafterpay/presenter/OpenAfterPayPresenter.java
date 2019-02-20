package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.SmsEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.OpenAfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.model.OpenAfterPayModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:开通医后付页面的 Presenter
 */
public class OpenAfterPayPresenter<T extends OpenAfterPayContract.IView>
        extends MvpBasePresenter<T> implements OpenAfterPayContract.IPresenter {

    private static final String TAG = "OpenAfterPayPresenter";
    private OpenAfterPayContract.IModel mModel = new OpenAfterPayModel();

    public OpenAfterPayPresenter() {
    }

    @Override
    public void sendSmsCode() {
        String phoneNumber = "";
        if (isNonNull()) {
            phoneNumber = mViewRef.get().getPhoneNumber();
        }

        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11) {
            if (isNonNull()) {
                mViewRef.get().showCountDownView();
            }
            mModel.sendSmsCode(phoneNumber, new HttpRequestCallback<SmsEntity>() {
                @Override
                public void onSuccess(SmsEntity smsEntity) {
                    LogUtil.i(TAG, "发送成功~" + smsEntity.getIden_code());
                    WToastUtil.show("发送成功！");
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "发送失败===" + errCodeDes);
                    WToastUtil.show(errCodeDes);
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
            mModel.openAfterPay(phone, idenCode, new HttpRequestCallback<SmsEntity>() {
                @Override
                public void onSuccess(SmsEntity smsEntity) {
                    LogUtil.i(TAG, "openAfterPay() -> onSuccess()");
                    WToastUtil.show("开通成功！" + smsEntity.getIden_code());
                    if (isNonNull()) {
                        mViewRef.get().onAfterPayOpenSuccess();
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "openAfterPay() -> onFailed()===" + errCodeDes);
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().onAfterPayOpenFailed();
                    }
                }
            });
        } else {
            LogUtil.eLogging(TAG, "openAfterPay():" + Exceptions.PARAM_IS_NULL);
        }
    }
}
