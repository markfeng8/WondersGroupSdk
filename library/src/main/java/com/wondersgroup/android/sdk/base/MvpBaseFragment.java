package com.wondersgroup.android.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by x-sir on 2017/03/23.
 * Function :MvpFragment的基类
 */
public abstract class MvpBaseFragment<V, T extends MvpBasePresenter<V>> extends Fragment {

    public T mPresenter;
    public Context mContext;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * create presenter with subclass to implementation.
     */
    protected abstract T createPresenter();

    /**
     * 交给子类实现，让子类实现自己特有的效果
     */
    public abstract View initView();

    /**
     * 当子类需要绑定数据到ui的时候，重写该方法
     * 1.绑定数据  2.联网请求
     */
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
