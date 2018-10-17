package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseFragment;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.CombineFeeRecord;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.view.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.utils.SettleUtil;
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
    private MvpBaseFragment mBaseFragment;
    private LayoutInflater mLayoutInflater;
    private List<CombineFeeRecord> mDetails;
    private boolean payViewVisibility;

    public FeeRecordAdapter(Context context, MvpBaseFragment baseFragment, List<CombineFeeRecord> details, boolean visible) {
        this.mContext = context;
        this.mBaseFragment = baseFragment;
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
        private TextView tvPayMoney;
        private ImageView ivArrow;
        private String payPlatTradeNo;
        private int position;
        private FeeRecordEntity.DetailsBean detailsBean;
        private List<FeeBillEntity.DetailsBean> feeDetail;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHospitalName = (TextView) itemView.findViewById(R.id.tvHospitalName);
            tvFeeDate = (TextView) itemView.findViewById(R.id.tvFeeDate);
            tvPayMoney = (TextView) itemView.findViewById(R.id.tvPayMoney);
            llHideLayout = (LinearLayout) itemView.findViewById(R.id.llHideLayout);
            llHospitalItem = (LinearLayout) itemView.findViewById(R.id.llHospitalItem);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
            initListener();
            setVisibility();
        }

        private void setVisibility() {
            tvPayMoney.setVisibility(payViewVisibility ? View.VISIBLE : View.GONE);
        }

        private void initListener() {
            llHospitalItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visibility = llHideLayout.getVisibility();
                    boolean visible = visibility == View.VISIBLE;
                    llHideLayout.setVisibility(visible ? View.GONE : View.VISIBLE);
                    ivArrow.setImageResource(visible ? R.drawable.wonders_group_down_arrow : R.drawable.wonders_group_up_arrow);
                    if (!visible) {
                        // 当里面为空的时候才去请求，请求过一次就不用再次请求了
                        if (llHideLayout.getChildCount() == 0) {
                            mBaseFragment.getFeeDetails(payPlatTradeNo, position, false);
                        }
                    }
                }
            });
            tvPayMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 需要判断医保移动支付状态是否开通，如果没开通就提示去开通
                    String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
                    if ("01".equals(mobPayStatus)) {

                        String orgCode = detailsBean.getOrg_code();
                        String orgName = detailsBean.getOrg_name();
                        String feeState = detailsBean.getFee_state();

                        // 判断是否是全部未结算还是医保未结算跳转不同的处理逻辑
                        // 00 全部未结算 01 医保未结算、自费已结(作保留）
                        switch (feeState) {
                            case "00":
                                PaymentDetailsActivity.actionStart(mContext, orgCode, orgName, true);
                                break;
                            case "01":
                                // 当里面为空的时候才去请求，请求过一次就不用再次请求了
                                if (llHideLayout.getChildCount() == 0) {
                                    mBaseFragment.getFeeDetails(payPlatTradeNo, position, true);
                                } else {
                                    String feeTotal = detailsBean.getFee_total();
                                    String feeCashTotal = detailsBean.getFee_cash_total();
                                    String feeYbTotal = detailsBean.getFee_yb_total();
                                    // 传递参数过去
                                    PersonalPayActivity.actionStart(mContext, true, true, orgName, orgCode,
                                            feeTotal, feeCashTotal, feeYbTotal, SettleUtil.getOfficialSettleParam(feeDetail));
                                }
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
                    // 如果是已支付页面显示 "已支付：" 否则显示 订单日期
                    // TODO: 2018/10/16 确认已完成页面显示日期还是已支付？& 订单日期过长？
                    if (payViewVisibility) {
                        tvFeeDate.setText("订单日期：" + shopOrderTime);
                    } else {
                        tvFeeDate.setText("已支付：" + feeTotal + "元");
                    }
                }

                if (feeDetail != null) {
                    if (llHideLayout.getChildCount() > 0) {
                        llHideLayout.removeAllViews();
                    }
                    for (int i = 0; i < feeDetail.size(); i++) {
                        FeeBillEntity.DetailsBean bean = feeDetail.get(i);
                        FeeRecordLayout layout = new FeeRecordLayout(mContext);
                        layout.setFeeName(bean.getOrdername());
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
