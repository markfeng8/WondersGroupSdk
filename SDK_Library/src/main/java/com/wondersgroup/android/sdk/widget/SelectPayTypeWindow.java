package com.wondersgroup.android.sdk.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wondersgroup.android.sdk.R;

import java.lang.ref.WeakReference;

/**
 * Created by x-sir on 2018/9/16 :)
 * Function:选择支付方式的 PopupWindow
 */
public class SelectPayTypeWindow {

    private View mPopupView;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private WeakReference<View> mView;
    private OnLoadingListener mListener;
    private TranslateAnimation mAnimation;
    private OnCheckedListener mCheckedListener;
    private int mPayType = 1; // 默认是1，1 支付宝 2 微信 3 银行卡

    public SelectPayTypeWindow(Builder builder) {
        this.mView = builder.view;
        this.mListener = builder.listener;
        this.mContext = builder.applicationContext;
        this.mCheckedListener = builder.checkedListener;
        initView();
    }

    /**
     * Initialize view parameters.
     */
    private void initView() {
        if (mPopupView == null) {
            mPopupView = View.inflate(mContext, R.layout.wonders_group_pay_popupwindow, null);
        }
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mPopupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }

        mPopupWindow.setOnDismissListener(() -> {
            if (mListener != null) {
                mListener.onDismiss();
            }
        });
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 当 mIsFocusable 为 true 时，响应返回键消失，为 false 时响应 activity 返回操作，默认为 false
        mPopupWindow.setFocusable(true);
        mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setDuration(200);

        RadioGroup rgPayType = (RadioGroup) mPopupView.findViewById(R.id.rgPayType);
        RadioButton rbAlipay = (RadioButton) mPopupView.findViewById(R.id.rbAlipay);
        RadioButton rbWeChatPay = (RadioButton) mPopupView.findViewById(R.id.rbWeChatPay);
        RadioButton rbUnionPay = (RadioButton) mPopupView.findViewById(R.id.rbUnionPay);

        rbAlipay.setText(Html.fromHtml(mContext.getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(mContext.getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(mContext.getResources().getString(R.string.wonders_text_union_pay)));

        // 默认选中支付宝
        rgPayType.check(R.id.rbAlipay);

        rgPayType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbAlipay) {
                mPayType = 1;
            } else if (checkedId == R.id.rbWeChatPay) {
                mPayType = 2;
            } else if (checkedId == R.id.rbUnionPay) {
                mPayType = 3;
            }

            if (mCheckedListener != null) {
                mCheckedListener.onSelected(mPayType);
            }

            dismiss();
        });
    }

    /**
     * Show popupWindow.
     */
    public void show() {
        dismiss();
        if (mPopupWindow != null) {
            // 必须要 post runnable，如果在onCreate中调用则会抛：
            // android.view.WindowManager$BadTokenException: Unable to add window -- token
            mView.get().post(() -> mPopupWindow.showAtLocation(mView.get(),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0));
            mPopupView.startAnimation(mAnimation);
        }
    }

    /**
     * Cancel popupWindow showing.
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * Invoke on Activity onDestroy() method.
     */
    public void dispose() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        mPopupWindow = null;
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    /**
     * PopupWindow is or not showing.
     *
     * @return
     */
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    /**
     * Builder inner class.
     */
    public static final class Builder {
        private WeakReference<View> view;
        private OnLoadingListener listener;
        private Context applicationContext;
        private OnCheckedListener checkedListener;

        /**
         * Constructor
         */
        public Builder(Context context) {
            this.applicationContext = context.getApplicationContext();
        }

        /**
         * Set location at parent view, because popupWindow must be dependency activity.
         *
         * @param view
         * @return
         */
        public Builder setDropView(View view) {
            if (view != null) {
                this.view = new WeakReference<>(view);
            } else {
                throw new IllegalArgumentException("must be point parent view!");
            }
            return this;
        }

        /**
         * set on popupWindow dismissLoadingDialog listener.
         *
         * @param listener
         * @return
         */
        public Builder setListener(OnLoadingListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * set on popupWindow checked listener.
         *
         * @param listener
         * @return
         */
        public Builder setCheckedListener(OnCheckedListener listener) {
            this.checkedListener = listener;
            return this;
        }

        public SelectPayTypeWindow build() {
            if (view == null) {
                throw new IllegalArgumentException("must be point parent view!");
            }
            return new SelectPayTypeWindow(this);
        }
    }

    /**
     * Define popupWindow dismissLoadingDialog listener.
     */
    public interface OnLoadingListener {
        void onDismiss();
    }

    /**
     * Define popupWindow checked listener.
     */
    public interface OnCheckedListener {
        void onSelected(int type);
    }
}
