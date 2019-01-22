/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.widget.selecthospital;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.CityBean;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalBean;
import com.wondersgroup.android.jkcs_sdk.utils.AssetUtils;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.wheel.OnWheelChangedListener;
import com.wondersgroup.android.jkcs_sdk.widget.timepicker.wheel.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by x-sir on 2019/1/21 :)
 * Function:自定义选择医院二级联动视图
 */
public class HospitalPickerView implements CanShow, OnWheelChangedListener {

    private static final String TAG = "HospitalPickerView";

    private PopupWindow popupWindow;
    private View popupView;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private RelativeLayout mRelativeTitleBg;
    private TextView mTvOK;
    private TextView mTvTitle;
    private TextView mTvCancel;
    private OnCityItemClickListener mBaseListener;

    private ParseHelper mParseHelper;
    private CityConfig mConfig;
    private Context mContext;
    private CityBean[] proArray;

    public void setOnCityItemClickListener(OnCityItemClickListener listener) {
        mBaseListener = listener;
    }

    /**
     * provide public empty parameters constructor.
     */
    public HospitalPickerView() {

    }

    /**
     * 设置配置属性
     *
     * @param config cityConfig
     */
    public void setConfig(final CityConfig config) {
        this.mConfig = config;
    }

    /**
     * 初始化，默认解析城市数据，提升加载速度
     */
    public void init(Context context) {
        this.mContext = context;
        mParseHelper = new ParseHelper();
        // 如果列表数据为空，就解析初始数据
        if (mParseHelper.getmCityBeanArrayList().isEmpty()) {
            mParseHelper.initData(context);
        }
    }

    /**
     * 初始化 popupWindow
     */
    private void initCityPickerPopupWindow() {
        if (mConfig == null) {
            throw new IllegalArgumentException("please set cityConfig first！");
        }

        // 解析初始数据
        if (mParseHelper == null) {
            mParseHelper = new ParseHelper();
            if (mParseHelper.getmCityBeanArrayList().isEmpty()) {
                throw new IllegalArgumentException("please call init() method in your Activity!");
            }
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        popupView = layoutInflater.inflate(R.layout.wonders_group_pop_citypicker, null);

        mViewProvince = popupView.findViewById(R.id.id_province);
        mViewCity = popupView.findViewById(R.id.id_city);
        mViewDistrict = popupView.findViewById(R.id.id_district);
        mRelativeTitleBg = popupView.findViewById(R.id.rl_title);
        mTvOK = popupView.findViewById(R.id.tv_confirm);
        mTvTitle = popupView.findViewById(R.id.tv_title);
        mTvCancel = popupView.findViewById(R.id.tv_cancel);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        popupWindow.setOnDismissListener(() -> {
            if (mConfig.isShowBackground()) {
                AssetUtils.setBackgroundAlpha(mContext, 1.0f);
            }
        });

        // 设置标题背景颜色
        if (!TextUtils.isEmpty(mConfig.getTitleBackgroundColorStr())) {
            if (mConfig.getTitleBackgroundColorStr().startsWith("#")) {
                mRelativeTitleBg.setBackgroundColor(Color.parseColor(mConfig.getTitleBackgroundColorStr()));
            } else {
                mRelativeTitleBg.setBackgroundColor(Color.parseColor("#" + mConfig.getTitleBackgroundColorStr()));

            }
        }

        // 标题
        if (!TextUtils.isEmpty(mConfig.getTitle())) {
            mTvTitle.setText(mConfig.getTitle());
        }

        // 标题文字大小
        if (mConfig.getTitleTextSize() > 0) {
            mTvTitle.setTextSize(mConfig.getTitleTextSize());
        }

        // 标题文字颜色
        if (!TextUtils.isEmpty(mConfig.getTitleTextColorStr())) {
            if (mConfig.getTitleTextColorStr().startsWith("#")) {
                mTvTitle.setTextColor(Color.parseColor(mConfig.getTitleTextColorStr()));
            } else {
                mTvTitle.setTextColor(Color.parseColor("#" + mConfig.getTitleTextColorStr()));
            }
        }

        // 设置确认按钮文字大小颜色
        if (!TextUtils.isEmpty(mConfig.getConfirmTextColorStr())) {
            if (mConfig.getConfirmTextColorStr().startsWith("#")) {
                mTvOK.setTextColor(Color.parseColor(mConfig.getConfirmTextColorStr()));
            } else {
                mTvOK.setTextColor(Color.parseColor("#" + mConfig.getConfirmTextColorStr()));
            }
        }

        if (!TextUtils.isEmpty(mConfig.getConfirmText())) {
            mTvOK.setText(mConfig.getConfirmText());
        }

        if ((mConfig.getConfirmTextSize() > 0)) {
            mTvOK.setTextSize(mConfig.getConfirmTextSize());
        }

        // 设置取消按钮文字颜色
        if (!TextUtils.isEmpty(mConfig.getCancelTextColorStr())) {
            if (mConfig.getCancelTextColorStr().startsWith("#")) {
                mTvCancel.setTextColor(Color.parseColor(mConfig.getCancelTextColorStr()));
            } else {
                mTvCancel.setTextColor(Color.parseColor("#" + mConfig.getCancelTextColorStr()));
            }
        }

        if (!TextUtils.isEmpty(mConfig.getCancelText())) {
            mTvCancel.setText(mConfig.getCancelText());
        }

        if ((mConfig.getCancelTextSize() > 0)) {
            mTvCancel.setTextSize(mConfig.getCancelTextSize());
        }

        // 只显示省市两级联动
        if (mConfig.getWheelType() == CityConfig.WheelType.PRO) {
            mViewCity.setVisibility(View.GONE);
            mViewDistrict.setVisibility(View.GONE);
        } else if (mConfig.getWheelType() == CityConfig.WheelType.PRO_CITY) {
            mViewDistrict.setVisibility(View.GONE);
        } else {
            mViewProvince.setVisibility(View.VISIBLE);
            mViewCity.setVisibility(View.VISIBLE);
            mViewDistrict.setVisibility(View.VISIBLE);
        }

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mTvCancel.setOnClickListener(v -> {
            mBaseListener.onCancel();
            hide();
        });

        // 确认选择
        mTvOK.setOnClickListener(v -> {
            if (mParseHelper != null) {
                if (mConfig.getWheelType() == CityConfig.WheelType.PRO) {
                    mBaseListener.onSelected(mParseHelper.getmCityBean(), new HospitalBean());
                } else if (mConfig.getWheelType() == CityConfig.WheelType.PRO_CITY) {
                    mBaseListener.onSelected(mParseHelper.getmCityBean(),
                            mParseHelper.getmHospitalBean());
                } else {
                    mBaseListener.onSelected(mParseHelper.getmCityBean(),
                            mParseHelper.getmHospitalBean());
                }

            } else {
                mBaseListener.onSelected(new CityBean(), new HospitalBean());
            }
            hide();
        });

        // 显示省市区数据
        setUpData();

        // 背景半透明
        if (mConfig != null && mConfig.isShowBackground()) {
            AssetUtils.setBackgroundAlpha(mContext, 0.5f);
        }
    }

    /**
     * 根据是否显示港澳台数据来初始化最新的数据
     *
     * @param array
     * @return
     */
    private CityBean[] getProArrData(CityBean[] array) {
        List<CityBean> provinceBeanList = new ArrayList<>(Arrays.asList(array));
        proArray = new CityBean[provinceBeanList.size()];

        for (int i = 0; i < provinceBeanList.size(); i++) {
            proArray[i] = provinceBeanList.get(i);
        }

        return proArray;
    }

    /**
     * 加载数据
     */
    private void setUpData() {
        if (mParseHelper == null || mConfig == null) {
            return;
        }

        // 根据是否显示港澳台数据来初始化最新的数据
        getProArrData(mParseHelper.getmCityBeanArray());

        int provinceDefault = -1;
        if (!TextUtils.isEmpty(mConfig.getDefaultProvinceName()) && proArray.length > 0) {
            for (int i = 0; i < proArray.length; i++) {
                if (proArray[i].getArea_name().contains(mConfig.getDefaultProvinceName())) {
                    provinceDefault = i;
                    break;
                }
            }
        }

        ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter<>(mContext, proArray);
        mViewProvince.setViewAdapter(arrayWheelAdapter);

        // 自定义item
        if (!mConfig.getCustomItemLayout().equals(CityConfig.NONE) && !mConfig.getCustomItemTextViewId().equals(CityConfig.NONE)) {
            arrayWheelAdapter.setItemResource(mConfig.getCustomItemLayout());
            arrayWheelAdapter.setItemTextResource(mConfig.getCustomItemTextViewId());
        } else {
            arrayWheelAdapter.setItemResource(R.layout.wonders_group_default_item_city);
            arrayWheelAdapter.setItemTextResource(R.id.default_item_city_name_tv);
        }

        // 获取所设置的省的位置，直接定位到该位置
        if (-1 != provinceDefault) {
            mViewProvince.setCurrentItem(provinceDefault);
        }

        // 设置可见条目数量
        mViewProvince.setVisibleItems(mConfig.getVisibleItems());
        mViewCity.setVisibleItems(mConfig.getVisibleItems());
        mViewDistrict.setVisibleItems(mConfig.getVisibleItems());
        mViewProvince.setCyclic(mConfig.isProvinceCyclic());
        mViewCity.setCyclic(mConfig.isCityCyclic());
        mViewDistrict.setCyclic(mConfig.isDistrictCyclic());

        // 显示滚轮模糊效果
        mViewProvince.setDrawShadows(mConfig.isDrawShadows());
        mViewCity.setDrawShadows(mConfig.isDrawShadows());
        mViewDistrict.setDrawShadows(mConfig.isDrawShadows());

        // 中间线的颜色及高度
        mViewProvince.setLineColorStr(mConfig.getLineColor());
        mViewProvince.setLineWidth(mConfig.getLineHeight());
        mViewCity.setLineColorStr(mConfig.getLineColor());
        mViewCity.setLineWidth(mConfig.getLineHeight());
        mViewDistrict.setLineColorStr(mConfig.getLineColor());
        mViewDistrict.setLineWidth(mConfig.getLineHeight());

        updateCities();
        updateAreas();
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        if (mParseHelper == null || mConfig == null) {
            return;
        }

        // 省份滚轮滑动的当前位置
        int pCurrent = mViewProvince.getCurrentItem();

        // 省份选中的名称
        CityBean mProvinceBean = proArray[pCurrent];
        mParseHelper.setmCityBean(mProvinceBean);

        if (mParseHelper.getmCityHosMap() == null) {
            return;
        }

        HospitalBean[] cities = mParseHelper.getmCityHosMap().get(mProvinceBean.getArea_name());
        if (cities == null) {
            return;
        }

        // 设置最初的默认城市
        int cityDefault = -1;
        if (!TextUtils.isEmpty(mConfig.getDefaultCityName()) && cities.length > 0) {
            for (int i = 0; i < cities.length; i++) {
                if (mConfig.getDefaultCityName().contains(cities[i].getOrg_name())) {
                    cityDefault = i;
                    break;
                }
            }
        }

        ArrayWheelAdapter cityWheel = new ArrayWheelAdapter<>(mContext, cities);

        // 自定义item
        if (!mConfig.getCustomItemLayout().equals(CityConfig.NONE) && !mConfig.getCustomItemTextViewId().equals(CityConfig.NONE)) {
            cityWheel.setItemResource(mConfig.getCustomItemLayout());
            cityWheel.setItemTextResource(mConfig.getCustomItemTextViewId());
        } else {
            cityWheel.setItemResource(R.layout.wonders_group_default_item_city);
            cityWheel.setItemTextResource(R.id.default_item_city_name_tv);
        }

        mViewCity.setViewAdapter(cityWheel);
        if (-1 != cityDefault) {
            mViewCity.setCurrentItem(cityDefault);
        } else {
            mViewCity.setCurrentItem(0);
        }

        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        if (mParseHelper.getmCityHosMap() == null || mParseHelper.getmCityHosMap() == null) {
            return;
        }

        if (mConfig.getWheelType() == CityConfig.WheelType.PRO_CITY
                || mConfig.getWheelType() == CityConfig.WheelType.PRO_CITY_DIS) {

            HospitalBean hospitalBean = mParseHelper.getmCityHosMap().get(mParseHelper.getmCityBean().getArea_name())[pCurrent];
            mParseHelper.setmHospitalBean(hospitalBean);
        }
    }

    public void showCityPicker() {
        initCityPickerPopupWindow();
        if (!isShow()) {
            popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void hide() {
        if (isShow()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public boolean isShow() {
        return popupWindow.isShowing();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            if (mParseHelper != null && mParseHelper.getmCityHosMap() != null) {

                HospitalBean mDistrictBean = mParseHelper.getmCityHosMap()
                        .get(mParseHelper.getmCityBean().getArea_name() + mParseHelper.getmHospitalBean().getOrg_name())[newValue];

                mParseHelper.setmHospitalBean(mDistrictBean);
            }
        }
    }

}
