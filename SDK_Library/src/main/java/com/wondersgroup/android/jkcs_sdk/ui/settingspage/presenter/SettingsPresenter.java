package com.wondersgroup.android.jkcs_sdk.ui.settingspage.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.jkcs_sdk.listener.OnOpenResultListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnTerminationListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnVerifySendListener;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.model.SettingsModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

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
                    LogUtil.i(TAG, "sendOpenRequest() -> onSuccess()");
                    WToastUtil.show("修改成功！");
                    dismissPopupWindow();
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendOpenRequest() -> onFailed()===" + errCodeDes);
                    dismissPopupWindow();
                    WToastUtil.show(errCodeDes);
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
                    LogUtil.i(TAG, "sendVerifyCode() -> onSuccess()");
                    WToastUtil.show("发送成功！");
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendVerifyCode() -> onFailed()===" + errCodeDes);
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            WToastUtil.show(WondersApplication.getsContext()
                    .getString(R.string.wonders_text_phone_number_nullable));
        }
    }

    @Override
    public void termination(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            mModel.termination(map, new OnTerminationListener() {
                @Override
                public void onSuccess() {
                    LogUtil.i(TAG, "医后付解约成功~");
                    WToastUtil.show("医后付解约成功");
                    dismissPopupWindow();
                    if (isNonNull()) {
                        mViewRef.get().terminationSuccess();
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "医后付解约失败！");
                    dismissPopupWindow();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    private void dismissPopupWindow() {
        if (isNonNull()) {
            mViewRef.get().dismissPopupWindow();
        }
    }
}
