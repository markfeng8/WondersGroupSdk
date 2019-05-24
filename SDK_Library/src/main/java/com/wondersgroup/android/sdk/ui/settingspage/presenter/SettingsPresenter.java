package com.wondersgroup.android.sdk.ui.settingspage.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.WondersApplication;
import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.constants.Exceptions;
import com.wondersgroup.android.sdk.entity.BaseEntity;
import com.wondersgroup.android.sdk.entity.SmsEntity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.sdk.ui.settingspage.model.SettingsModel;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:设置页面的 Presenter
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
            mModel.sendOpenRequest(map, new HttpRequestCallback<BaseEntity>() {
                @Override
                public void onSuccess(BaseEntity baseEntity) {
                    LogUtil.i(TAG, "sendOpenRequest() -> onSuccess()");
                    dismissPopupWindow();
                    WToastUtil.show("修改成功！");
                    if (isNonNull()) {
                        mViewRef.get().onUpdateSuccessResult();
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendOpenRequest() -> onFailed()===" + errCodeDes);
                    dismissPopupWindow();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "sendOpenRequest():" + Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void sendVerifyCode(String phone, String idenClass) {
        if (!TextUtils.isEmpty(phone)) {
            mModel.sendVerifyCode(phone, idenClass, new HttpRequestCallback<SmsEntity>() {
                @Override
                public void onSuccess(SmsEntity smsEntity) {
                    LogUtil.i(TAG, "sendVerifyCode() -> onSuccess()" + smsEntity.getIden_code());
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
            mModel.termination(map, new HttpRequestCallback<BaseEntity>() {
                @Override
                public void onSuccess(BaseEntity baseEntity) {
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
            LogUtil.eLogging(TAG, "termination():" + Exceptions.MAP_SET_NULL);
        }
    }

    private void dismissPopupWindow() {
        if (isNonNull()) {
            mViewRef.get().dismissPopupWindow();
        }
    }
}
