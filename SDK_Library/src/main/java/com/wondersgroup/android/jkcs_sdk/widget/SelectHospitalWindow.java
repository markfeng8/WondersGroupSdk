package com.wondersgroup.android.jkcs_sdk.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.HospitalAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by x-sir on 2018/9/16 :)
 * Function:选择医院列表的 PopupWindow
 */
public class SelectHospitalWindow {

    private View mPopupView;
    private Context mContext;
    private ListView listView;
    private PopupWindow mPopupWindow;
    private HospitalAdapter mAdapter;
    private WeakReference<View> mView;
    private OnLoadingListener mListener;
    private TranslateAnimation mAnimation;
    private OnItemClickListener mOnItemClickListener;
    private List<HospitalEntity.DetailsBean> mBeanList;

    SelectHospitalWindow(Builder builder) {
        this.mView = builder.view;
        this.mListener = builder.listener;
        this.mContext = builder.applicationContext;
        this.mOnItemClickListener = builder.onItemClickListener;
        initView();
    }

    /**
     * Initialize view parameters.
     */
    private void initView() {
        if (mPopupView == null) {
            mPopupView = View.inflate(mContext, R.layout.wonders_group_select_hospital_popupwindow, null);
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

        listView = (ListView) mPopupView.findViewById(R.id.listView);

        // 设置 Item 点击事件的监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(position);
                    dismiss();
                }
            }
        });
    }

    public void setBeanList(List<HospitalEntity.DetailsBean> mBeanList) {
        this.mBeanList = mBeanList;
        setAdapter();
    }

    private void setAdapter() {
        if (mBeanList != null && mBeanList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new HospitalAdapter(mContext, mBeanList);
                listView.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
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
        private OnItemClickListener onItemClickListener;

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
         * set on popupWindow dismiss listener.
         *
         * @param listener
         * @return
         */
        public Builder setListener(OnLoadingListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * set on popupWindow item click listener.
         *
         * @param listener
         * @return
         */
        public Builder setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
            return this;
        }

        public SelectHospitalWindow build() {
            if (view == null) {
                throw new IllegalArgumentException("must be point parent view!");
            }
            return new SelectHospitalWindow(this);
        }
    }

    /**
     * Define popupWindow dismiss listener.
     */
    public interface OnLoadingListener {
        void onDismiss();
    }

    /**
     * Define popupWindow on item click listener.
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }
}
