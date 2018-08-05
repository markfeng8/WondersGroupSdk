package com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.contract.SmsAuthContract;
import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.listener.OnSmsSendListener;
import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.model.SmsAuthModel;
import com.wondersgroup.android.jkcs_sdk.utils.WonderToastUtil;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class SmsAuthPresenter<T extends SmsAuthContract.IView>
        extends MvpBasePresenter<T> implements SmsAuthContract.IPresenter {

    private SmsAuthContract.IModel mModel = new SmsAuthModel();

    public SmsAuthPresenter() {
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

                }

                @Override
                public void onFailed() {

                }
            });
        } else {
            WonderToastUtil.show(WondersApplication.getsContext()
                    .getString(R.string.wonders_text_phone_number_nullable));
        }

    }
}
