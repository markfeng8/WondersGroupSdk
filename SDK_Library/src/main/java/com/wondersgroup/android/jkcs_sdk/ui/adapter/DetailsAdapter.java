package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.wondersgroup.android.jkcs_sdk.widget.FeeDetailLayout;
import com.wondersgroup.android.jkcs_sdk.widget.PayItemLayout;

import java.util.List;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情的 Adapter
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

        HeaderViewHolder(View itemView) {
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
        private LinearLayout llItem;
        private LinearLayout llDetails;
        private String hisOrderNo;
        private int position;

        ListViewHolder(View itemView) {
            super(itemView);
            tvOrderName = (TextView) itemView.findViewById(R.id.tvOrderName);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tvOrderTime);
            llItem = (LinearLayout) itemView.findViewById(R.id.llItem);
            llDetails = (LinearLayout) itemView.findViewById(R.id.llDetails);
            initListener();
        }

        private void initListener() {
            llItem.setOnClickListener(v -> {
                boolean visible = llDetails.getVisibility() == View.GONE;
                llDetails.setVisibility((visible) ? View.VISIBLE : View.GONE);
                int childCount = llDetails.getChildCount();
                if (visible && childCount == 1) {
                    ((PaymentDetailsActivity) mContext).getOrderDetails(hisOrderNo, position);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void setData(CombineDetailsBean combineDetails, int position) {
            this.position = position;
            if (combineDetails != null) {
                FeeBillEntity.DetailsBean defaultDetails = combineDetails.getDefaultDetails();
                List<OrderDetailsEntity.DetailsBean> openDetails = combineDetails.getOpenDetails();
                if (defaultDetails != null) {
                    String orderName = defaultDetails.getOrdername();
                    String feeOrder = defaultDetails.getFee_order();
                    String orderTime = defaultDetails.getHis_order_time();
                    hisOrderNo = defaultDetails.getHis_order_no();

                    if (!TextUtils.isEmpty(orderName)) {
                        tvOrderName.setText(orderName);
                    }
                    if (!TextUtils.isEmpty(feeOrder)) {
                        tvMoney.setText(feeOrder);
                    }
                    if (!TextUtils.isEmpty(orderTime)) {
                        tvOrderTime.setText("订单时间：" + orderTime);
                    }
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
                                    .append(price)
                                    .append("*")
                                    .append(amount)
                                    .append(unit);

                            FeeDetailLayout layout = new FeeDetailLayout(mContext);
                            layout.setFeeName(itemName);
                            layout.setFeeNum(stringBuilder.toString());
                            llDetails.addView(layout);
                        }
                    }
                }
            }
        }
    }

    // 3.支付的数据类型
    class PayViewHolder extends RecyclerView.ViewHolder {
        private PayItemLayout plTotalMoney;
        private PayItemLayout plPersonalPay;
        private PayItemLayout plYiBaoPay;
        private TextView tvPayType;
        private LinearLayout llPayType;
        private LinearLayout llYiBaoPayLayout;
        private ToggleButton tbYiBaoEnable;
        private String totalPay;
        private String personalPay;
        private String yiBaoPay;

        PayViewHolder(View itemView) {
            super(itemView);
            plTotalMoney = (PayItemLayout) itemView.findViewById(R.id.plTotalMoney);
            plPersonalPay = (PayItemLayout) itemView.findViewById(R.id.plPersonalPay);
            plYiBaoPay = (PayItemLayout) itemView.findViewById(R.id.plYiBaoPay);
            tvPayType = (TextView) itemView.findViewById(R.id.tvPayType);
            llPayType = (LinearLayout) itemView.findViewById(R.id.llPayType);
            llYiBaoPayLayout = (LinearLayout) itemView.findViewById(R.id.llYiBaoPayLayout);
            tbYiBaoEnable = (ToggleButton) itemView.findViewById(R.id.tbYiBaoEnable);
            initData();
            initListener();
        }

        private void initData() {
            String mobPayStatus = SpUtil.getInstance().getString(SpKey.MOB_PAY_STATUS, "");
            if (!"01".equals(mobPayStatus)) {
                // 如果未开通医保移动支付就显示医保移动支付开关
                llYiBaoPayLayout.setVisibility(View.VISIBLE);
            }
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

                        // TODO: 2018/10/11 跳转到开通医保移动支付页面，当开通完成后回来需要刷新页面，隐藏开关布局
                    }

                } else {
                    SpUtil.getInstance().save(SpKey.YIBAO_ENABLE, false);
                }

                // 处理显示隐藏医保金额布局
                plYiBaoPay.setVisibility(isChecked ? View.VISIBLE : View.GONE);

                // 如果允许医保支付，则个人支付就是个人支付的金额，如果不允许医保支付，则个人支付就是总支付的金额
                plPersonalPay.setFeeNum(isChecked ? personalPay : totalPay);

                // 如果允许医保支付，则个人需要支付个人部分即可，如果不允许，则显示全部支付金额
                ((PaymentDetailsActivity) mContext).setPersonalPayAmount(isChecked ? personalPay : totalPay);
            });

            // TODO: 2018/10/9 隐藏选择支付方式 Layout
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
                totalPay = payBean.getTotalPay();
                personalPay = payBean.getPersonalPay();
                yiBaoPay = payBean.getYibaoPay();

                if (!TextUtils.isEmpty(totalPay)) {
                    plTotalMoney.setFeeName("总计金额：");
                    plTotalMoney.setFeeNum(totalPay);
                }
                if (!TextUtils.isEmpty(personalPay)) {
                    plPersonalPay.setFeeName("个人支付：");
                    plPersonalPay.setFeeNum(personalPay);
                }
                if (!TextUtils.isEmpty(yiBaoPay)) {
                    plYiBaoPay.setFeeName("医保支付：");
                    plYiBaoPay.setFeeNum(yiBaoPay);
                }
            }
        }
    }

    public interface OnCheckedCallback {
        void onSelected(int type);
    }
}
