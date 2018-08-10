package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract;

import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnAfterPayStateListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public interface AfterPayHomeContract {
    interface IModel {
        void getAfterPayState(HashMap<String, String> map, OnAfterPayStateListener listener);
    }

    interface IView {
        void returnResult(AfterPayStateEntity entity);
    }

    interface IPresenter {
        void getAfterPayState(HashMap<String, String> map);
    }
}
