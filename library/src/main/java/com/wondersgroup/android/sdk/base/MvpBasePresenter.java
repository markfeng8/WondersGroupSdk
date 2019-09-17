package com.wondersgroup.android.sdk.base;

import java.lang.ref.WeakReference;

/**
 * Created by xpf on 2018/8/1 :)
 * Function:Base Presenter.
 */
public class MvpBasePresenter<T> {

    protected WeakReference<T> mViewRef;

    /**
     * attach view reference
     *
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    /**
     * detach view reference
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

    /**
     * is or not nullable
     */
    protected boolean isNonNull() {
        return mViewRef.get() != null;
    }
}
