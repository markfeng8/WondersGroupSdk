package com.wondersgroup.android.jkcs_sdk.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

import java.lang.ref.WeakReference;

/**
 * Created by x-sir on 2018/8/22 :)
 * Function:封装一个全局通用的 Loading
 */
public class LoadingView {

    private String mText;
    private int mTextSize;
    private View mLoadingView;
    private Context mContext;
    private String mTextColor;
    private int mCornerRadius;
    private int mLoadingWidth;
    private int mLoadingHeight;
    private int mTextMarginTop;
    private String mLoadingBgColor;
    private WeakReference<Activity> mActivity;
    private OnLoadingListener mListener;
    private boolean isShowing = false;
    private Dialog mLoadingDialog;

    private static final String DEFAULT_TEXT = "加载中..."; // default text
    private static final int DEFAULT_TEXT_SIZE = 12; // default text size
    private static final int DEFAULT_TEXT_MARGIN_TOP = 6; // default text margin top
    private static final String DEFAULT_TEXT_COLOR = "#FFFFFF"; // default text color
    private static final int DEFAULT_CORNER_RADIUS = 4; // default loading background radius size
    private static final String DEFAULT_LOADING_BG_COLOR = "#CC000000"; // default loading background color
    private static final String TAG = "LoadingView";

    /**
     * Constructor.
     */
    LoadingView(Builder builder) {
        this.mText = builder.text;
        this.mActivity = builder.activity;
        this.mListener = builder.listener;
        this.mTextSize = builder.textSize;
        this.mTextColor = builder.textColor;
        this.mCornerRadius = builder.cornerRadius;
        this.mContext = builder.applicationContext;
        this.mLoadingBgColor = builder.loadingBgColor;
        this.mLoadingWidth = builder.loadingWidth;
        this.mLoadingHeight = builder.loadingHeight;
        this.mTextMarginTop = builder.textMarginTop;
    }

    private View getLoadingView() {
        View loadingView = View.inflate(mContext, R.layout.wonders_group_loading_popupwindow, null);
        LinearLayout llLoadingBg = loadingView.findViewById(R.id.llLoadingBg);
        TextView tvContent = loadingView.findViewById(R.id.tvContent);
        RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) llLoadingBg.getLayoutParams();
        if (mLoadingWidth != -1 && mLoadingHeight != -1) {
            rlParams.width = dp2px(mLoadingWidth);
            rlParams.height = dp2px(mLoadingHeight);
        } else {
            rlParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            rlParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }
        llLoadingBg.setLayoutParams(rlParams);
        GradientDrawable mGroupDrawable = new GradientDrawable();
        /*设置 Drawable 的形状为矩形*/
        mGroupDrawable.setShape(GradientDrawable.RECTANGLE);
        /*设置背景颜色*/
        mGroupDrawable.setColor(Color.parseColor(mLoadingBgColor));
        /*设置圆角大小*/
        mGroupDrawable.setCornerRadius(dp2px(mCornerRadius));
        llLoadingBg.setBackground(mGroupDrawable);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvContent.getLayoutParams();
        params.topMargin = dp2px(mTextMarginTop);
        tvContent.setLayoutParams(params);
        /*设置显示文本*/
        tvContent.setText(mText);
        /*设置文本大小(以 SP 为单位)*/
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        /*设置文本颜色*/
        tvContent.setTextColor(Color.parseColor(mTextColor));
        return loadingView;
    }

    /**
     * Show normal loading, can clickable.
     */
    public void showLoadingNormal() {
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
        }
        if (mActivity.get() != null) {
            FrameLayout rootContainer = mActivity.get().findViewById(android.R.id.content);
            // 先移除再添加，防止添加两次出现异常！
            rootContainer.removeView(mLoadingView);
            rootContainer.addView(mLoadingView);
            isShowing = true;
        }
    }

    /**
     * Show loading dialog, can't clickable.
     */
    public void showLoadingDialog() {
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
        }
        if (mActivity.get() != null) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new Dialog(mActivity.get(), R.style.loading_dialog_style);
            }
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setContentView(mLoadingView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            mLoadingDialog.dismiss();
            mLoadingDialog.show();
            isShowing = true;
            mLoadingDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mLoadingDialog.dismiss();
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * Cancel loading showing.
     */
    public void dismissLoadingNormal() {
        if (mActivity.get() != null) {
            FrameLayout rootContainer = mActivity.get().findViewById(android.R.id.content);
            rootContainer.removeView(mLoadingView);
            isShowing = false;
            if (mListener != null) {
                mListener.onDismiss();
            }
        }
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            isShowing = false;
            if (mListener != null) {
                mListener.onDismiss();
            }
        }
    }

    /**
     * Invoke on Activity onDestroy() method.
     */
    public void dispose() {
        if (mLoadingView != null) {
            mLoadingView.destroyDrawingCache();
            mLoadingView = null;
        }
        if (mActivity.get() != null) {
            mActivity.clear();
        }
    }

    /**
     * Loading is or not showing.
     *
     * @return
     */
    public boolean isShowing() {
        return isShowing;
    }

    /**
     * Builder inner class.
     */
    public static final class Builder {
        private String text;
        private String textColor;
        private int textSize = -1;
        private String loadingBgColor;
        private int cornerRadius = -1;
        private int loadingWidth = -1;
        private int loadingHeight = -1;
        private int textMarginTop = -1;
        private WeakReference<Activity> activity;
        private OnLoadingListener listener;
        private Context applicationContext;

        /**
         * Constructor
         */
        public Builder(Activity activity) {
            this.activity = new WeakReference<>(activity);
            this.applicationContext = activity.getApplicationContext();
        }

        /**
         * Set content text.
         *
         * @param text
         * @return
         */
        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Set text size.
         *
         * @param textSize
         * @return
         */
        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * Set text margin top dimen.
         *
         * @param textMarginTop
         * @return
         */
        public Builder setTextMarginTop(int textMarginTop) {
            this.textMarginTop = textMarginTop;
            return this;
        }

        /**
         * Set gif loadingView width.
         *
         * @param loadingWidth
         * @return
         */
        public Builder setLoadingWidth(int loadingWidth) {
            this.loadingWidth = loadingWidth;
            return this;
        }

        /**
         * Set gif loadingView height.
         *
         * @param loadingHeight
         * @return
         */
        public Builder setLoadingHeight(int loadingHeight) {
            this.loadingHeight = loadingHeight;
            return this;
        }

        /**
         * Set text color.
         *
         * @param textColor
         * @return
         */
        public Builder setTextColor(String textColor) {
            this.textColor = textColor;
            return this;
        }

        /**
         * Set loadingView corner radius.
         *
         * @param cornerRadius
         * @return
         */
        public Builder setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        /**
         * Set loadingView background color.
         *
         * @param loadingBgColor
         * @return
         */
        public Builder setLoadingBgColor(String loadingBgColor) {
            this.loadingBgColor = loadingBgColor;
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

        public LoadingView build() {
            if (TextUtils.isEmpty(text)) {
                text = DEFAULT_TEXT;
            }
            if (textSize == -1) {
                textSize = DEFAULT_TEXT_SIZE;
            }
            if (textMarginTop == -1) {
                textMarginTop = DEFAULT_TEXT_MARGIN_TOP;
            }
            if (TextUtils.isEmpty(textColor)) {
                textColor = DEFAULT_TEXT_COLOR;
            }
            if (TextUtils.isEmpty(loadingBgColor)) {
                loadingBgColor = DEFAULT_LOADING_BG_COLOR;
            }
            if (cornerRadius == -1) {
                cornerRadius = DEFAULT_CORNER_RADIUS;
            }
            if (activity == null) {
                throw new IllegalArgumentException("must be point parent view!");
            }

            return new LoadingView(this);
        }
    }

    /**
     * dp convert to px.
     *
     * @param dpValue
     * @return
     */
    private int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Define popupWindow dismissLoadingDialog listener.
     */
    interface OnLoadingListener {
        void onDismiss();
    }
}
