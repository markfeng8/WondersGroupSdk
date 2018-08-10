package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model.AfterPayHomeModel;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public class AfterPayHomePresenter<T extends AfterPayHomeContract.IView>
        extends MvpBasePresenter<T> implements AfterPayHomeContract.IPresenter {

    private AfterPayHomeContract.IModel mModel = new AfterPayHomeModel();

    public AfterPayHomePresenter() {
    }

    @Override
    public void getAfterPayState(HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            mModel.getAfterPayState(map, new OnAfterPayStateListener() {
                @Override
                public void onSuccess(AfterPayStateEntity entity) {
                    if (isNonNull()) {
                        mViewRef.get().returnResult(entity);
                    }
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
