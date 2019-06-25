/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.widget.selecthospital;

/**
 * Created by x-sir on 2019/1/21 :)
 * Function:城市选择器样式配置构建者类
 */
public class CityConfig {

    public static final Integer NONE = -1111;
    /**
     * 滚轮显示的item个数
     */
    private int visibleItems = 5;
    /**
     * 省滚轮是否循环滚动
     */
    private boolean isProvinceCyclic = true;
    /**
     * 市滚轮是否循环滚动
     */
    private boolean isCityCyclic = true;
    /**
     * 区滚轮是否循环滚动
     */
    private boolean isDistrictCyclic = true;
    /**
     * Color.BLACK
     */
    private String cancelTextColorStr = "#333333";
    /**
     * Color.BLACK
     */
    private String cancelText = "取消";

    private int cancelTextSize = 14;
    /**
     * Color.BLUE
     */
    private String confirmTextColorStr = "#333333";
    /**
     * Color.BLUE
     */
    private String confirmText = "确定";
    /**
     * Color.BLUE
     */
    private int confirmTextSize = 14;
    /**
     * 标题
     */
    private String mTitle = "选择地区";
    /**
     * 标题背景颜色
     */
    private String titleBackgroundColorStr = "#F0F0F0";
    /**
     * 标题颜色
     */
    private String titleTextColorStr = "#333333";
    /**
     * 标题字体大小
     */
    private int titleTextSize = 16;
    /**
     * 第一次默认的显示省份，一般配合定位，使用
     */
    private String defaultProvinceName = "浙江";
    /**
     * 第一次默认得显示城市，一般配合定位，使用
     */
    private String defaultCityName = "杭州";

    /**
     * 自定义的item布局
     */
    private Integer customItemLayout;

    /**
     * 自定义的item txt id
     */
    private Integer customItemTextViewId;

    /**
     * 是否显示滚轮上面的模糊阴影效果
     */
    private boolean drawShadows = false;

    /**
     * 中间线的颜色
     */
    private String lineColor = "#000000";

    /**
     * 中间线的宽度
     */
    private int lineHeight = 1;

    /**
     * 默认显示的城市数据，只包含省市区名称
     * 定义显示省市区三种滚轮的显示状态
     * PRO:只显示省份的一级选择器
     * PRO_CITY:显示省份和城市二级联动的选择器
     * PRO_CITY_DIS:显示省份和城市和县区三级联动的选择器
     */
    public enum WheelType {
        PRO, PRO_CITY, PRO_CITY_DIS
    }

    /**
     * 是否显示半透明的背景
     */
    private boolean isShowBackground = true;

    /**
     * 定义默认显示省市区三级联动的滚轮选择器
     */
    private WheelType mWheelType = WheelType.PRO_CITY;

    public CityConfig.WheelType getWheelType() {
        return mWheelType;
    }

    public boolean isShowBackground() {
        return isShowBackground;
    }

    public String getLineColor() {
        return lineColor == null ? "" : lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public boolean isDrawShadows() {
        return drawShadows;
    }

    public void setDrawShadows(boolean drawShadows) {
        this.drawShadows = drawShadows;
    }

    public int getVisibleItems() {
        return visibleItems;
    }

    public void setVisibleItems(int visibleItems) {
        this.visibleItems = visibleItems;
    }

    public boolean isProvinceCyclic() {
        return isProvinceCyclic;
    }

    public void setProvinceCyclic(boolean provinceCyclic) {
        isProvinceCyclic = provinceCyclic;
    }

    public boolean isCityCyclic() {
        return isCityCyclic;
    }

    public void setCityCyclic(boolean cityCyclic) {
        isCityCyclic = cityCyclic;
    }

    public boolean isDistrictCyclic() {
        return isDistrictCyclic;
    }

    public void setDistrictCyclic(boolean districtCyclic) {
        isDistrictCyclic = districtCyclic;
    }

    public String getCancelTextColorStr() {
        return cancelTextColorStr == null ? "" : cancelTextColorStr;
    }

    public void setCancelTextColorStr(String cancelTextColorStr) {
        this.cancelTextColorStr = cancelTextColorStr;
    }

    public String getCancelText() {
        return cancelText == null ? "" : cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public int getCancelTextSize() {
        return cancelTextSize;
    }

    public void setCancelTextSize(int cancelTextSize) {
        this.cancelTextSize = cancelTextSize;
    }

    public String getConfirmTextColorStr() {
        return confirmTextColorStr == null ? "" : confirmTextColorStr;
    }

    public void setConfirmTextColorStr(String confirmTextColorStr) {
        this.confirmTextColorStr = confirmTextColorStr;
    }

    public String getConfirmText() {
        return confirmText == null ? "" : confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public int getConfirmTextSize() {
        return confirmTextSize;
    }

    public void setConfirmTextSize(int confirmTextSize) {
        this.confirmTextSize = confirmTextSize;
    }

    public String getTitle() {
        return mTitle == null ? "" : mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleBackgroundColorStr() {
        return titleBackgroundColorStr == null ? "" : titleBackgroundColorStr;
    }

    public void setTitleBackgroundColorStr(String titleBackgroundColorStr) {
        this.titleBackgroundColorStr = titleBackgroundColorStr;
    }

    public String getTitleTextColorStr() {
        return titleTextColorStr == null ? "" : titleTextColorStr;
    }

    public void setTitleTextColorStr(String titleTextColorStr) {
        this.titleTextColorStr = titleTextColorStr;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public String getDefaultProvinceName() {
        return defaultProvinceName == null ? "" : defaultProvinceName;
    }

    public void setDefaultProvinceName(String defaultProvinceName) {
        this.defaultProvinceName = defaultProvinceName;
    }

    public String getDefaultCityName() {
        return defaultCityName == null ? "" : defaultCityName;
    }

    public void setDefaultCityName(String defaultCityName) {
        this.defaultCityName = defaultCityName;
    }

    public Integer getCustomItemLayout() {
        return customItemLayout == null ? NONE : customItemLayout;
    }

    public void setCustomItemLayout(int customItemLayout) {
        this.customItemLayout = customItemLayout;
    }

    public Integer getCustomItemTextViewId() {
        return customItemTextViewId == null ? NONE : customItemTextViewId;
    }

    public void setCustomItemTextViewId(Integer customItemTextViewId) {
        this.customItemTextViewId = customItemTextViewId;
    }

    public void setShowBackground(boolean showBackground) {
        isShowBackground = showBackground;
    }

    public CityConfig(Builder builder) {
        /**
         * 标题栏相关的属性：
         * 0、标题栏背景颜色
         * 1、标题文字、大小、颜色
         *
         * 2、取消字体的颜色、大小、内容
         * 3、确认字体的颜色、大小、内容
         */
        this.titleBackgroundColorStr = builder.titleBackgroundColorStr;

        this.mTitle = builder.mTitle;
        this.titleTextColorStr = builder.titleTextColorStr;
        this.titleTextSize = builder.titleTextSize;

        this.cancelTextColorStr = builder.cancelTextColorStr;
        this.cancelText = builder.cancelText;
        this.cancelTextSize = builder.cancelTextSize;

        this.confirmTextColorStr = builder.confirmTextColorStr;
        this.confirmText = builder.confirmText;
        this.confirmTextSize = builder.confirmTextSize;

        /**
         * 滚轮相关的属性：
         * 1、item显示的个数
         * 2、省份是否可以循环
         * 3、城市是否可以循环
         * 4、地区是否可以循环
         */
        this.visibleItems = builder.visibleItems;
        this.isProvinceCyclic = builder.isProvinceCyclic;
        this.isDistrictCyclic = builder.isDistrictCyclic;
        this.isCityCyclic = builder.isCityCyclic;

        /**
         * 默认的省市区地址
         */
        this.defaultCityName = builder.defaultCityName;
        this.defaultProvinceName = builder.defaultProvinceName;

        /**
         * 是否显示城市和地区
         */
        this.mWheelType = builder.mWheelType;

        /**
         * 是否显示半透明
         */
        this.isShowBackground = builder.isShowBackground;

        /**
         * 自定义item的布局，必须制定Layout和id
         */
        this.customItemLayout = builder.customItemLayout;
        this.customItemTextViewId = builder.customItemTextViewId;

        /**
         * 是否显示滚轮上面的模糊阴影效果
         */
        this.drawShadows = builder.drawShadows;
        this.lineColor = builder.lineColor;
        this.lineHeight = builder.lineHeight;
    }

    public static class Builder {

        /**
         * 滚轮显示的 item 个数(默认是 5 个)
         */
        private int visibleItems = 5;
        /**
         * 省滚轮是否循环滚动
         */
        private boolean isProvinceCyclic = false;
        /**
         * 市滚轮是否循环滚动
         */
        private boolean isCityCyclic = false;
        /**
         * 区滚轮是否循环滚动
         */
        private boolean isDistrictCyclic = false;
        /**
         * Color.BLACK
         */
        private String cancelTextColorStr = "#000000";
        /**
         * Color.BLACK
         */
        private String cancelText = "取消";

        private int cancelTextSize = 14;
        /**
         * Color.BLUE
         */
        private String confirmTextColorStr = "#000000";
        /**
         * Color.BLUE
         */
        private String confirmText = "确定";
        /**
         * 16sp
         */
        private int confirmTextSize = 14;
        /**
         * 标题
         */
        private String mTitle = "请选择医院";
        /**
         * 标题背景颜色
         */
        private String titleBackgroundColorStr = "#E0E0E0";
        /**
         * 标题颜色
         */
        private String titleTextColorStr = "#000000";
        /**
         * 标题字体大小
         */
        private int titleTextSize = 16;
        /**
         * 第一次默认的显示省份，一般配合定位，使用
         */
        private String defaultProvinceName = "浙江";
        /**
         * 第一次默认得显示城市，一般配合定位，使用
         */
        private String defaultCityName = "杭州";
        /**
         * 定义默认显示省市区三级联动的滚轮选择器
         */
        private CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY;
        /**
         * 是否显示半透明的背景
         */
        private boolean isShowBackground = true;
        /**
         * 自定义的 item 布局
         */
        private Integer customItemLayout;
        /**
         * 自定义的item txt id
         */
        private Integer customItemTextViewId;
        /**
         * 是否显示滚轮上面的模糊阴影效果
         */
        private boolean drawShadows = false;
        /**
         * 中间线的颜色
         */
        private String lineColor = "#000000";
        /**
         * 是否显示港澳台城市数据
         */
        private boolean showGAT = false;
        /**
         * 中间线的宽度
         */
        private int lineHeight = 1;

        public Builder() {
        }

        /**
         * 是否显示港澳台城市数据，默认不显示
         *
         * @param showGAT showGAT
         * @return Builder
         */
        public Builder setShowGAT(boolean showGAT) {
            this.showGAT = showGAT;
            return this;
        }

        /**
         * 中间线的宽度
         *
         * @param lineHeight lineHeight
         * @return Builder
         */
        public Builder setLineHeight(int lineHeight) {
            this.lineHeight = lineHeight;
            return this;
        }

        /**
         * 中间线的颜色
         *
         * @param lineColor lineColor
         * @return Builder
         */
        public Builder setLineColor(String lineColor) {
            this.lineColor = lineColor;
            return this;
        }

        /**
         * 是否显示滚轮上面的模糊阴影效果
         *
         * @param drawShadows drawShadows
         * @return Builder
         */
        public Builder drawShadows(boolean drawShadows) {
            this.drawShadows = drawShadows;
            return this;
        }

        /**
         * 设置标题背景颜色
         *
         * @param colorBg colorBg
         * @return Builder
         */
        public Builder titleBackgroundColor(String colorBg) {
            this.titleBackgroundColorStr = colorBg;
            return this;
        }

        /**
         * 设置标题字体颜色
         *
         * @param titleTextColorStr titleTextColorStr
         * @return Builder
         */
        public Builder titleTextColor(String titleTextColorStr) {
            this.titleTextColorStr = titleTextColorStr;
            return this;
        }

        /**
         * 设置标题字体大小
         *
         * @param titleTextSize titleTextSize
         * @return Builder
         */
        public Builder titleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title title
         * @return Builder
         */
        public Builder title(String title) {
            this.mTitle = title;
            return this;
        }

        /**
         * 确认按钮文字
         *
         * @param confirmTextSize confirmTextSize
         * @return Builder
         */
        public Builder confirmTextSize(int confirmTextSize) {
            this.confirmTextSize = confirmTextSize;
            return this;
        }

        /**
         * 确认按钮文字
         *
         * @param confirmText confirmText
         * @return Builder
         */
        public Builder confirmText(String confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        /**
         * 确认按钮文字颜色
         *
         * @param color color
         * @return Builder
         */
        public Builder confirTextColor(String color) {
            this.confirmTextColorStr = color;
            return this;
        }

        /**
         * 取消按钮文字颜色
         *
         * @param color color
         * @return Builder
         */
        public Builder cancelTextColor(String color) {
            this.cancelTextColorStr = color;
            return this;
        }

        /**
         * 取消按钮文字大小
         *
         * @param cancelTextSize cancelTextSize
         * @return Builder
         */
        public Builder cancelTextSize(int cancelTextSize) {
            this.cancelTextSize = cancelTextSize;
            return this;
        }

        /**
         * 取消按钮文字
         *
         * @param cancelText cancelText
         * @return Builder
         */
        public Builder cancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        /**
         * 滚轮显示的item个数
         *
         * @param visibleItems visibleItems
         * @return Builder
         */
        public Builder visibleItemsCount(int visibleItems) {
            this.visibleItems = visibleItems;
            return this;
        }

        /**
         * 省滚轮是否循环滚动
         *
         * @param isProvinceCyclic isProvinceCyclic
         * @return Builder
         */
        public Builder provinceCyclic(boolean isProvinceCyclic) {
            this.isProvinceCyclic = isProvinceCyclic;
            return this;
        }

        /**
         * 市滚轮是否循环滚动
         *
         * @param isCityCyclic isCityCyclic
         * @return Builder
         */
        public Builder cityCyclic(boolean isCityCyclic) {
            this.isCityCyclic = isCityCyclic;
            return this;
        }

        /**
         * 区滚轮是否循环滚动
         *
         * @param isDistrictCyclic isDistrictCyclic
         * @return Builder
         */
        public Builder districtCyclic(boolean isDistrictCyclic) {
            this.isDistrictCyclic = isDistrictCyclic;
            return this;
        }

        /**
         * 显示省市区三级联动的显示状态
         * PRO:只显示省份的一级选择器
         * PRO_CITY:显示省份和城市二级联动的选择器
         * PRO_CITY_DIS:显示省份和城市和县区三级联动的选择器
         *
         * @param wheelType wheelType
         * @return Builder
         */
        public Builder setCityWheelType(CityConfig.WheelType wheelType) {
            this.mWheelType = wheelType;
            return this;
        }

        /**
         * 第一次默认的显示省份，一般配合定位，使用
         *
         * @param defaultProvinceName defaultProvinceName
         * @return Builder
         */
        public Builder defaultCity(String defaultProvinceName) {
            this.defaultProvinceName = defaultProvinceName;
            return this;
        }

        /**
         * 第一次默认得显示城市，一般配合定位，使用
         *
         * @param defaultCityName defaultCityName
         * @return Builder
         */
        public Builder defaultHospital(String defaultCityName) {
            this.defaultCityName = defaultCityName;
            return this;
        }

        /**
         * 是否显示半透明的背景
         *
         * @param isShowBackground isShowBackground
         * @return Builder
         */
        public Builder showBackground(boolean isShowBackground) {
            this.isShowBackground = isShowBackground;
            return this;
        }

        /**
         * 自定义item布局
         *
         * @param itemLayout itemLayout
         * @return Builder
         */
        public Builder setCustomItemLayout(Integer itemLayout) {
            this.customItemLayout = itemLayout;
            return this;
        }

        /**
         * 自定义item布局中的id
         *
         * @param setCustomItemTextViewId setCustomItemTextViewId
         * @return result
         */
        public Builder setCustomItemTextViewId(Integer setCustomItemTextViewId) {
            this.customItemTextViewId = setCustomItemTextViewId;
            return this;
        }

        public CityConfig build() {
            return new CityConfig(this);
        }
    }
}
