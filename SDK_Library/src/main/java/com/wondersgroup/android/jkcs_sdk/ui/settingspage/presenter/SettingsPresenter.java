package com.wondersgroup.android.jkcs_sdk.ui.settingspage.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.listener.OnOpenResultListener;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.listener.OnTerminationListener;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.listener.OnVerifySendListener;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.model.SettingsModel;
import com.wondersgroup.android.jkcs_sdk.utils.WonderToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class SettingsPresenter<T extends SettingsContract.IView>
        extends MvpBasePresenter<T> implements SettingsContract.IPresenter {

    private static final String TAG = SettingsPresenter.class.getSimpleName();
    private SettingsContract.IModel mModel = new SettingsModel();

    public SettingsPresenter() {
    }

    @Override
    public void sendOpenRequest(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            mModel.sendOpenRequest(map, new OnOpenResultListener() {
                @Override
                public void onSuccess() {
                    WonderToastUtil.show("修改成功！");
                    if (isNonNull()) {
                        mViewRef.get().dismissPopupWindow();
                    }
                }

                @Override
                public void onFailed() {
                    WonderToastUtil.show("修改失败！");
                    if (isNonNull()) {
                        mViewRef.get().dismissPopupWindow();
                    }
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void sendVerifyCode(String phone, String idenClass) {
        if (!TextUtils.isEmpty(phone)) {
            mModel.sendVerifyCode(phone, idenClass, new OnVerifySendListener() {
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
    public void termination(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            mModel.termination(map, new OnTerminationListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailed() {

                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }
}
