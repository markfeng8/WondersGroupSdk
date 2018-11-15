/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.CombineFeeRecord;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentresult.view.PaymentResultActivity;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.FeeRecordLayout;

import java.util.List;

/**
 * Created by x-sir on 2018/9/19 :)
 * Function:门诊缴费记录页面（已完成/未完成订单）的 Adapter
 */
public class FeeRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<CombineFeeRecord> mDetails;
    private boolean payViewVisibility;

    public FeeRecordAdapter(Context context, List<CombineFeeRecord> details, boolean visible) {
        this.mContext = context;
        this.mDetails = details;
        this.payViewVisibility = visible;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDetails(List<CombineFeeRecord> mDetails) {
        this.mDetails = mDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(
                R.layout.wonders_group_fee_record_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(mDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDetails == null ? 0 : mDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llHospitalItem;
        private LinearLayout llHideLayout;
        private TextView tvHospitalName;
        private TextView tvFeeDate;
        private TextView tvTradeNo;
        private TextView tvPayMoney;
        private ImageView ivArrow;
        private String payPlatTradeNo;
        private int position;
        private FeeRecordEntity.DetailsBean detailsBean;
        private List<FeeBillEntity.DetailsBean> feeDetail;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHospitalName = itemView.findViewById(R.id.tvHospitalName);
            tvFeeDate = itemView.findViewById(R.id.tvFeeDate);
            tvTradeNo = itemView.findViewById(R.id.tvTradeNo);
            tvPayMoney = itemView.findViewById(R.id.tvPayMoney);
            llHideLayout = itemView.findViewById(R.id.llHideLayout);
            llHospitalItem = itemView.findViewById(R.id.llHospitalItem);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            initListener();
            setVisibility();
        }

        private void setVisibility() {
            tvPayMoney.setVisibility(payViewVisibility ? View.VISIBLE : View.GONE);
        }

        private void initListener() {
            llHospitalItem.setOnClickListener(v -> {
                // TODO: 2018/10/19 点击时的展开布局的 bug
                int visibility = llHideLayout.getVisibility();
                boolean visible = visibility == View.VISIBLE;
                llHideLayout.setVisibility(visible ? View.GONE : View.VISIBLE);
                ivArrow.setImageResource(visible ? R.drawable.wonders_group_down_arrow : R.drawable.wonders_group_up_arrow);
                if (!visible) {
                    // 当里面为空的时候才去请求，请求过一次就不用再次请求了
                    if (llHideLayout.getChildCount() == 0) {
                        ((FeeRecordActivity) mContext).getFeeDetails(payPlatTradeNo, position);
                    }
                }
            });
            tvPayMoney.setOnClickListener(view -> {
                // 需要判断医保移动支付状态是否开通，如果没开通就提示去开通
                String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
                if ("01".equals(mobPayStatus)) {

                    String orgCode = detailsBean.getOrg_code();
                    String orgName = detailsBean.getOrg_name();
                    String feeState = detailsBean.getFee_state();

                    if (!TextUtils.isEmpty(feeState)) {
                        // 判断是否是全部未结算还是医保未结算跳转不同的处理逻辑
                        // 00 全部未结算 01 医保未结算、自费已结(作保留）
                        switch (feeState) {
                            case "00":
                                PaymentDetailsActivity.actionStart(mContext, orgCode, orgName, true);
                                break;
                            case "01":
                                // 当里面为空的时候才去请求，请求过一次就不用再次请求了
                                if (llHideLayout.getChildCount() == 0) {
                                    ((FeeRecordActivity) mContext).getFeeDetails(payPlatTradeNo, position);
                                } else {
                                    String feeTotal = detailsBean.getFee_total();
                                    String feeCashTotal = detailsBean.getFee_cash_total();
                                    String feeYbTotal = detailsBean.getFee_yb_total();
                                    // 传递参数过去
                                    PaymentResultActivity.actionStart(mContext, true, true, orgName, orgCode,
                                            feeTotal, feeCashTotal, feeYbTotal);
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        // 全部未结算，跳转到 "缴费详情" 页面
                        PaymentDetailsActivity.actionStart(mContext, orgCode, orgName, false);
                    }

                } else {
                    WToastUtil.show("您未开通医保移动支付，请先开通！");
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void setData(CombineFeeRecord item, int position) {
            this.position = position;
            if (item != null) {
                detailsBean = item.getRecordDetail();
                feeDetail = item.getFeeDetail();
                if (detailsBean != null) {
                    String orgName = detailsBean.getOrg_name();
                    String feeTotal = detailsBean.getFee_total();
                    payPlatTradeNo = detailsBean.getPayplat_tradno();
                    String shopOrderTime = detailsBean.getShop_order_time();
                    tvHospitalName.setText(orgName);
                    tvFeeDate.setText("订单日期：" + shopOrderTime.substring(0, 10));
                    tvTradeNo.setText("订单号：" + payPlatTradeNo);
                }

                if (feeDetail != null) {
                    if (llHideLayout.getChildCount() > 0) {
                        llHideLayout.removeAllViews();
                    }
                    for (int i = 0; i < feeDetail.size(); i++) {
                        FeeBillEntity.DetailsBean bean = feeDetail.get(i);
                        FeeRecordLayout layout = new FeeRecordLayout(mContext);
                        // 此处取两个 order_name 那有个有值就用那个（后台返回的数据很垃圾！）
                        layout.setFeeName(bean.getOrdername());
                        layout.setFeeName(bean.getOrder_name());

                        layout.setFeeNum(bean.getFee_order());
                        layout.setTimestamp(bean.getHis_order_time());
                        llHideLayout.addView(layout);

                        // TODO: 2018/9/19 如果点击的是屏幕最后一个可见的位置就向上滑动
                    }
                }
            }
        }
    }

}
