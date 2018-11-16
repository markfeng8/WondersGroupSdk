package com.wondersgroup.android.jkcs_sdk.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by xpf on 2018/8/1 :)
 * Function:Activity的基类
 */
public abstract class MvpBaseActivity<V, T extends MvpBasePresenter<V>> extends AppCompatActivity {

    public T mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        bindView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * create presenter with subclass to implementation.
     */
    protected abstract T createPresenter();

    /**
     * 绑定视图让子Activity去实现
     */
    protected abstract void bindView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
