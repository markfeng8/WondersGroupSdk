package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.CombineDetailsBean;
import com.wondersgroup.android.jkcs_sdk.entity.DetailHeadBean;
import com.wondersgroup.android.jkcs_sdk.entity.DetailPayBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.List;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DetailsAdapter";
    private static final int TYPE_HEADER = 1;  // 头部信息类型
    private static final int TYPE_LIST = 2;    // 未缴清账单类型
    private static final int TYPE_PAY = 3;    // 支付视图类型
    private Context mContext;
    private List<Object> mItemList;
    private LayoutInflater mLayoutInflater; // 初始化布局加载器
    private int mCurrentType = -1; // 当前Item的类型

    public DetailsAdapter(Context context, List<Object> itemList) {
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
                viewHolder = new HeaderViewHolder(mLayoutInflater.inflate(
                        R.layout.wonders_group_item_detail_pay_header, parent, false));
                break;
            case TYPE_LIST:
                viewHolder = new ListViewHolder(mLayoutInflater.inflate(
                        R.layout.wonders_group_item_detail_pay_list, parent, false));
                break;
            case TYPE_PAY:
                viewHolder = new PayViewHolder(mLayoutInflater.inflate(
                        R.layout.wonders_group_item_detail_pay_type, parent, false));
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
                headerViewHolder.setData((DetailHeadBean) mItemList.get(position));
                break;
            case TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.setData((CombineDetailsBean) mItemList.get(position), position);
                break;
            case TYPE_PAY:
                PayViewHolder payViewHolder = (PayViewHolder) holder;
                payViewHolder.setData((DetailPayBean) mItemList.get(position));
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
            if (object instanceof DetailHeadBean) {
                mCurrentType = TYPE_HEADER;
            } else if (object instanceof CombineDetailsBean) {
                mCurrentType = TYPE_LIST;
            } else if (object instanceof DetailPayBean) {
                mCurrentType = TYPE_PAY;
            }
        }
        return mCurrentType;
    }

    // 1.Header 类型
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvSocialNum;
        private TextView tvHospitalName;
        private TextView tvOrderNum;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSocialNum = (TextView) itemView.findViewById(R.id.tvSocialNum);
            tvHospitalName = (TextView) itemView.findViewById(R.id.tvHospitalName);
            tvOrderNum = (TextView) itemView.findViewById(R.id.tvOrderNum);
        }

        @SuppressLint("SetTextI18n")
        public void setData(DetailHeadBean headBean) {
            String name = headBean.getName();
            String socialNum = headBean.getSocialNum();
            String hospitalName = headBean.getHospitalName();
            String orderNum = headBean.getOrderNum();

            if (!TextUtils.isEmpty(name)) {
                tvName.setText(name);
            }
            if (!TextUtils.isEmpty(socialNum)) {
                tvSocialNum.setText(socialNum);
            }
            if (!TextUtils.isEmpty(hospitalName)) {
                tvHospitalName.setText(hospitalName);
            }
            if (!TextUtils.isEmpty(orderNum)) {
                tvOrderNum.setText("订单编号：" + orderNum);
            }
        }
    }

    // 2.List 数据类型
    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderName;
        private TextView tvMoney;
        private TextView tvOrderTime;
        private TextView tvDetail;
        private LinearLayout llDetails;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvOrderName = (TextView) itemView.findViewById(R.id.tvOrderName);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tvOrderTime);
            tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
            llDetails = (LinearLayout) itemView.findViewById(R.id.llDetails);
        }

        @SuppressLint("SetTextI18n")
        public void setData(CombineDetailsBean combineDetails, int position) {
            if (combineDetails != null) {
                FeeBillEntity.DetailsBean defaultDetails = combineDetails.getDefaultDetails();
                List<OrderDetailsEntity.DetailsBean> openDetails = combineDetails.getOpenDetails();
                if (defaultDetails != null) {
                    String orderName = defaultDetails.getOrdername();
                    String feeOrder = defaultDetails.getFee_order();
                    String orderTime = defaultDetails.getHis_order_time();
                    final String hisOrderNo = defaultDetails.getHis_order_no();

                    if (!TextUtils.isEmpty(orderName)) {
                        tvOrderName.setText(orderName);
                    }
                    if (!TextUtils.isEmpty(feeOrder)) {
                        tvMoney.setText(feeOrder);
                    }
                    if (!TextUtils.isEmpty(orderTime)) {
                        tvOrderTime.setText("订单时间：" + orderTime);
                    }

                    tvDetail.setOnClickListener(v -> {
                        boolean visible = llDetails.getVisibility() == View.GONE;
                        llDetails.setVisibility((visible) ? View.VISIBLE : View.GONE);
                        int childCount = llDetails.getChildCount();
                        if (visible && childCount == 1) {
                            ((PaymentDetailsActivity) mContext).getOrderDetails(hisOrderNo, position);
                        }
                    });
                }

                if (openDetails != null) {
                    if (openDetails.size() > 0) {
                        // 先清除就布局中的 Item
                        int count = llDetails.getChildCount();
                        if (count > 1) {
                            llDetails.removeViews(1, count - 1);
                        }
                        for (int i = 0; i < openDetails.size(); i++) {
                            StringBuilder stringBuilder = new StringBuilder();
                            OrderDetailsEntity.DetailsBean detailsBean = openDetails.get(i);
                            String itemName = detailsBean.getItemname();
                            String price = detailsBean.getPrice();
                            String amount = detailsBean.getAmount();
                            String unit = detailsBean.getUnit();

                            stringBuilder
                                    .append(itemName)
                                    .append("              ")
                                    .append(price)
                                    .append("*")
                                    .append(amount)
                                    .append(unit);

                            LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            // new a textView widget
                            TextView bigText = new TextView(mContext);
                            bigText.setText(stringBuilder.toString());
                            bigText.setPadding(2, 2, 2, 2);
                            bigText.setGravity(Gravity.CENTER);
                            bigText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                            bigText.setTextColor(Color.parseColor("#333333"));
                            llDetails.addView(bigText, textLp);
                        }
                    }
                }
            }
        }
    }

    // 3.支付的数据类型
    class PayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTotalMoney;
        private TextView tvPersonalPay;
        private TextView tvYiBaoPay;
        private TextView tvPayType;
        private LinearLayout llPayType;
        private ToggleButton tbYiBaoEnable;

        public PayViewHolder(View itemView) {
            super(itemView);
            tvTotalMoney = (TextView) itemView.findViewById(R.id.tvTotalMoney);
            tvPersonalPay = (TextView) itemView.findViewById(R.id.tvPersonalPay);
            tvYiBaoPay = (TextView) itemView.findViewById(R.id.tvYiBaoPay);
            tvPayType = (TextView) itemView.findViewById(R.id.tvPayType);
            llPayType = (LinearLayout) itemView.findViewById(R.id.llPayType);
            tbYiBaoEnable = (ToggleButton) itemView.findViewById(R.id.tbYiBaoEnable);
            initListener();
        }

        private void initListener() {
            tbYiBaoEnable.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
                    if ("01".equals(mobPayStatus)) { // 已开通
                        SpUtil.getInstance().save(SpKey.YIBAO_ENABLE, true);
                    } else { // 未开通
                        SpUtil.getInstance().save(SpKey.YIBAO_ENABLE, false);
                        WToastUtil.show("您未开通医保移动支付，不能进行医保结算！");
                        tbYiBaoEnable.setChecked(false);
                    }
                } else {
                    SpUtil.getInstance().save(SpKey.YIBAO_ENABLE, false);
                }
            });
            llPayType.setOnClickListener(v -> ((PaymentDetailsActivity) mContext).showSelectPayTypeWindow(type -> {
                if (type == 1) {
                    tvPayType.setText("支付宝");
                } else if (type == 2) {
                    tvPayType.setText("微信");
                } else if (type == 3) {
                    tvPayType.setText("银行卡");
                }
            }));
        }

        public void setData(DetailPayBean payBean) {
            if (payBean != null) {
                String totalPay = payBean.getTotalPay();
                String personalPay = payBean.getPersonalPay();
                String yibaoPay = payBean.getYibaoPay();

                if (!TextUtils.isEmpty(totalPay)) {
                    tvTotalMoney.setText(totalPay);
                }
                if (!TextUtils.isEmpty(personalPay)) {
                    tvPersonalPay.setText(personalPay);
                }
                if (!TextUtils.isEmpty(yibaoPay)) {
                    tvYiBaoPay.setText(yibaoPay);
                }
            }
        }
    }

    public interface OnCheckedCallback {
        void onSelected(int type);
    }
}
