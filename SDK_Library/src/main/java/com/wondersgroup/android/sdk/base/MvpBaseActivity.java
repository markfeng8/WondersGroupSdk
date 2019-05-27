package com.wondersgroup.android.sdk.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.wondersgroup.android.sdk.widget.LoadingView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xpf on 2018/8/1 :)
 * Function:Activity的基类
 */
public abstract class MvpBaseActivity<V, T extends MvpBasePresenter<V>> extends AppCompatActivity {

    public T mPresenter;
    protected LoadingView mBaseLoading;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        bindView();
        initLoading();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initLoading() {
        mBaseLoading = new LoadingView.Builder(this)
                .build();
    }

    /**
     * create presenter with subclass to implementation.
     */
    protected abstract T createPresenter();

    /**
     * 绑定视图让子Activity去实现
     */
    protected abstract void bindView();

    public void showLoadingView(boolean show) {
        if (mBaseLoading == null) {
            return;
        }

        if (show) {
            mBaseLoading.showLoadingDialog();
        } else {
            mBaseLoading.dismissLoadingDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBaseLoading != null) {
            mBaseLoading.dispose();
        }
        mPresenter.detachView();
        mCompositeDisposable.clear();
    }
}
