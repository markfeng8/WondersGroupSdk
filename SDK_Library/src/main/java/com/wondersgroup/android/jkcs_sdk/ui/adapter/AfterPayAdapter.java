package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view.AfterPayHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view.OpenAfterPayActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.view.SettingsActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.List;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:医后付首页数据的 Adapter
 */
public class AfterPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = AfterPayAdapter.class.getSimpleName();
    /**
     * 头部信息类型
     */
    private static final int TYPE_HEADER = 1;
    /**
     * 未缴清账单类型
     */
    private static final int TYPE_LIST = 2;
    /**
     * 温馨提示类型
     */
    private static final int TYPE_NOTICE = 3;
    /**
     * 初始化布局加载器
     */
    private LayoutInflater mLayoutInflater;
    /**
     * 当前Item的类型
     */
    private int mCurrentType = -1;
    private Context mContext;
    private List<Object> mItemList;

    public AfterPayAdapter(Context context, List<Object> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置并刷新数据
     *
     * @param itemList
     */
    public void setmItemList(List<Object> itemList) {
        this.mItemList = itemList;
        notifyDataSetChanged(); // 刷新适配器
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(mLayoutInflater.inflate(R.layout.wonders_group_item_after_pay_header, parent, false));
                break;
            case TYPE_LIST:
                viewHolder = new ListViewHolder(mLayoutInflater.inflate(R.layout.wonders_group_item_after_pay_list, parent, false));
                break;
            case TYPE_NOTICE:
                viewHolder = new NoticeViewHolder(mLayoutInflater.inflate(R.layout.wonders_group_item_after_pay_notice, parent, false));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.setData((AfterHeaderBean) mItemList.get(position));
                break;
            case TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.setData((FeeBillEntity.DetailsBean) mItemList.get(position));
                break;
            case TYPE_NOTICE:
                NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
                noticeViewHolder.setData((String) mItemList.get(position));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemList != null && position < mItemList.size()) {
            Object object = mItemList.get(position);
            if (object instanceof AfterHeaderBean) {
                mCurrentType = TYPE_HEADER;
            } else if (object instanceof FeeBillEntity.DetailsBean) {
                mCurrentType = TYPE_LIST;
            } else if (object instanceof String) {
                mCurrentType = TYPE_NOTICE;
            }
        }
        return mCurrentType;
    }

    /**
     * 1.Header 类型
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llSettings;
        private LinearLayout llPayRecord;
        private LinearLayout llCenterMessage;
        private TextView tvHospitalName;
        private TextView tvSelectHospital;
        private TextView tvTreatName;
        private TextView tvSocialNum;
        private TextView tvAfterPayState;
        private TextView tvMobilePayState;
        private TextView tvToPay;
        private TextView tvPayInfo;
        private LinearLayout llToPayFee;
        private String orgCode;
        private String orgName;
        private String feeState;
        private String feeTotals;
        private String feeCashTotal;
        private String feeYbTotal;
        private String feeOrgName;
        private String feeOrgCode;

        HeaderViewHolder(View itemView) {
            super(itemView);
            llSettings = (LinearLayout) itemView.findViewById(R.id.llSettings);
            llPayRecord = (LinearLayout) itemView.findViewById(R.id.llPayRecord);
            llCenterMessage = (LinearLayout) itemView.findViewById(R.id.llCenterMessage);
            tvHospitalName = (TextView) itemView.findViewById(R.id.tvHospitalName);
            tvSelectHospital = (TextView) itemView.findViewById(R.id.tvSelectHospital);
            tvTreatName = (TextView) itemView.findViewById(R.id.tvTreatName);
            tvSocialNum = (TextView) itemView.findViewById(R.id.tvSocialNum);
            tvAfterPayState = (TextView) itemView.findViewById(R.id.tvAfterPayState);
            tvMobilePayState = (TextView) itemView.findViewById(R.id.tvMobilePayState);
            tvToPay = (TextView) itemView.findViewById(R.id.tvToPay);
            tvPayInfo = (TextView) itemView.findViewById(R.id.tvPayInfo);
            llToPayFee = (LinearLayout) itemView.findViewById(R.id.llToPayFee);
            initListener();
        }

        private void initListener() {
            // 选择医院
            tvSelectHospital.setOnClickListener(v -> {
                String feeTotal = SpUtil.getInstance().getString(SpKey.FEE_TOTAL, "");
                if (TextUtils.isEmpty(feeTotal)) {
                    ((AfterPayHomeActivity) mContext).getHospitalList();
                } else {
                    WToastUtil.show("您有欠费记录，需先缴清欠费！");
                }
            });
            // 缴费记录
            llPayRecord.setOnClickListener(v -> {
                mContext.startActivity(new Intent(mContext, FeeRecordActivity.class));
            });
            // 去缴费
            llToPayFee.setOnClickListener(v -> {
                // 需要判断医保移动支付状态是否开通，如果没开通就提示去开通
                String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
                if ("01".equals(mobPayStatus)) {
                    // 需要取出 yd0008 的 size ，如果没有记录说明是第一次去，就直接跳转到缴费详情页面，
                    // 如果有的话就跳转到医保缴费页面，发起结算时使用的时yd0009 的 details
                    int yd0008Size = SpUtil.getInstance().getInt(SpKey.YD0008_SIZE, -1);
                    if (yd0008Size == -1) {
                        PaymentDetailsActivity.actionStart(mContext, orgCode, orgName, false);
                    } else {
                        // TODO: 2018/10/16 跳转到医保支付页面
                    }

                } else {
                    WToastUtil.show("您未开通医保移动支付，请先开通！");
                }
            });
            // 去开通医后付
            tvAfterPayState.setOnClickListener(v -> mContext.startActivity(
                    new Intent(mContext, OpenAfterPayActivity.class)));
            // 去开通医保移动支付
            tvMobilePayState.setOnClickListener(v -> openMobilePay());
            // 设置
            llSettings.setOnClickListener(v -> {
                String signingStatus = SpUtil.getInstance().getString(SpKey.SIGNING_STATUS, "");
                if ("01".equals(signingStatus)) {
                    mContext.startActivity(new Intent(mContext, SettingsActivity.class));
                } else {
                    WToastUtil.show("您未开通医后付，请先开通医后付！");
                }
            });
            tvPayInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.i(TAG, "点击医后付首页中间的欠费信息去缴费～");
                    // 需要判断医保移动支付状态是否开通，如果没开通就提示去开通
                    String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
                    if ("01".equals(mobPayStatus)) {

                        switch (feeState) {
                            case "00": // 全部未结算
                                PaymentDetailsActivity.actionStart(mContext, orgCode, orgName, false);
                                break;
                            case "01": // 医保未结算
                                // TODO: 2018/10/16 跳转到医保结算页面
                                break;
                            default:
                                break;
                        }

                    } else {
                        WToastUtil.show("您未开通医保移动支付，请先开通！");
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setData(AfterHeaderBean afterHeaderBean) {
            if (afterHeaderBean != null) {
                String name = afterHeaderBean.getName();
                String cardNo = afterHeaderBean.getSocialNum();
                String signingStatus = afterHeaderBean.getSigningStatus();
                String mobPayStatus = afterHeaderBean.getMobPayStatus();
                String hospitalName = afterHeaderBean.getHospitalName();
                orgCode = afterHeaderBean.getOrgCode();
                orgName = afterHeaderBean.getOrgName();
                // yd0008
                feeState = afterHeaderBean.getFeeState();
                feeTotals = afterHeaderBean.getFeeTotals();
                feeCashTotal = afterHeaderBean.getFeeCashTotal();
                feeYbTotal = afterHeaderBean.getFeeYbTotal();
                feeOrgName = afterHeaderBean.getFeeOrgName();
                feeOrgCode = afterHeaderBean.getFeeOrgCode();

                if (!TextUtils.isEmpty(feeState)) {
                    String html = "";
                    // 代码中使用 html 不需要拼接 <![CDATA[ 标签，xml 中需要拼接
                    String head = "<font size=\"40\" color=\"#333333\">";
                    String tail = "</font><font size=\"40\" color=\"#007edf\">继续支付！</font>";
                    if ("00".equals(feeState)) {
                        html = head + "您有一笔未完成的订单" + feeOrgName + "，总金额" + feeTotals + "元，请" + tail;
                    } else if ("".equals(feeState)) {
                        html = head + "您有一笔未完成的订单" + feeOrgName + "，总金额" + feeTotals + "元，还需支付医保" + feeYbTotal + "元，请" + tail;
                    }

                    llCenterMessage.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(html)) {
                        LogUtil.i(TAG, "html===" + html);
                        tvPayInfo.setText(Html.fromHtml(html));
                    }
                }

                tvTreatName.setText(name);
                if (!TextUtils.isEmpty(hospitalName)) {
                    tvHospitalName.setText(hospitalName);
                }
                tvSocialNum.setText(mContext.getString(R.string.wonders_text_social_number) + cardNo);

                // 00未签约（医后付状态给NULL）
                if ("00".equals(signingStatus)) {
                    setAfterPayState(true);
                } else if ("01".equals(signingStatus)) { // 01已签约
                    setAfterPayState(false);
                    /*
                     * 1：正常（缴清或未使用医后付服务）2：欠费(医后付后有欠费的概要信
                     */
                    String paymentStatus = afterHeaderBean.getPaymentStatus();
                    if ("1".equals(paymentStatus)) {
                        llToPayFee.setVisibility(View.INVISIBLE);
                    } else if ("2".equals(paymentStatus)) {
                        llToPayFee.setVisibility(View.VISIBLE);
                        String feeTotal = afterHeaderBean.getFeeTotal();
                        String content = mContext.getString(R.string.wonders_to_pay_fee1) +
                                feeTotal + mContext.getString(R.string.wonders_to_pay_fee2);
                        tvToPay.setText(content);
                    }
                } else if ("02".equals(signingStatus)) { // 02 其他
                    setAfterPayState(false);
                }

                if ("00".equals(mobPayStatus)) { // 00 未签约
                    setMobilePayState(true);
                } else if ("01".equals(mobPayStatus)) { // 01 已签约
                    setMobilePayState(false);
                }
            }
        }

        /**
         * 设置医后付状态
         */
        private void setAfterPayState(boolean enable) {
            if (enable) {
                tvAfterPayState.setText(mContext.getString(R.string.wonders_to_open_after_pay));
                tvAfterPayState.setEnabled(true);
                tvAfterPayState.setCompoundDrawables(null, null, null, null);
            } else {
                tvAfterPayState.setText(mContext.getString(R.string.wonders_open_after_pay));
                tvAfterPayState.setEnabled(false);
            }
        }

        /**
         * 设置医保移动付状态
         */
        private void setMobilePayState(boolean enable) {
            if (enable) {
                tvMobilePayState.setText(mContext.getString(R.string.wonders_to_open_mobile_pay));
                tvMobilePayState.setEnabled(true);
                tvMobilePayState.setCompoundDrawables(null, null, null, null);
            } else {
                tvMobilePayState.setText(mContext.getString(R.string.wonders_open_mobile_pay));
                tvMobilePayState.setEnabled(false);
            }
        }

        private void openMobilePay() {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                AuthCall.businessProcess(mContext,
                        MakeArgsFactory.getOpenArgs(), WToastUtil::show);
            } else {
                WToastUtil.show("网络连接错误，请检查您的网络连接！");
            }
        }
    }

    // 2.List 数据类型
    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFeeName;
        private TextView tvDepartment;
        private TextView tvTimestamp;
        private TextView tvMoney;

        ListViewHolder(View itemView) {
            super(itemView);
            tvFeeName = itemView.findViewById(R.id.tvFeeName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvMoney = itemView.findViewById(R.id.tvMoney);
        }

        public void setData(FeeBillEntity.DetailsBean detailsBean) {
            if (detailsBean != null) {
                String orderName = detailsBean.getOrdername();
                String hisOrderTime = detailsBean.getHis_order_time();
                String feeOrder = detailsBean.getFee_order();
                tvFeeName.setText(orderName);
                tvTimestamp.setText(hisOrderTime);
                tvMoney.setText(feeOrder);
            }
        }
    }

    // 3.notice 数据类型
    class NoticeViewHolder extends RecyclerView.ViewHolder {

        NoticeViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(String info) {

        }
    }
}
