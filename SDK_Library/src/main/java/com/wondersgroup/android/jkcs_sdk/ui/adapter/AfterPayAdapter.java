package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.epsoft.hzauthsdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view.OpenAfterPayActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.PayRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.view.SelectHospitalActivity;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.view.SettingsActivity;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.List;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:医后付首页数据的 Adapter
 */
public class AfterPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = AfterPayAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 1;  // 头部信息类型
    private static final int TYPE_LIST = 2;    // 未缴清账单类型
    private Context mContext;
    private List<Object> mItemList;
    private LayoutInflater mLayoutInflater; // 初始化布局加载器
    private int mCurrentType = -1; // 当前Item的类型

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
            }
        }
        return mCurrentType;
    }

    // 1.Header 类型
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSettings;
        private TextView tvPayRecord;
        private TextView tvPayToast;
        private TextView tvHospitalName;
        private TextView tvSelectHospital;
        private TextView tvTreatName;
        private TextView tvSocialNum;
        private TextView tvAfterPayState;
        private TextView tvMobilePayState;
        private TextView tvToPay;
        private TextView tvToPayFee;
        private LinearLayout llToPayFee;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvSettings = (TextView) itemView.findViewById(R.id.tvUpdateInfo);
            tvPayRecord = (TextView) itemView.findViewById(R.id.tvPayRecord);
            tvPayToast = (TextView) itemView.findViewById(R.id.tvPayToast);
            tvHospitalName = (TextView) itemView.findViewById(R.id.tvHospitalName);
            tvSelectHospital = (TextView) itemView.findViewById(R.id.tvSelectHospital);
            tvTreatName = (TextView) itemView.findViewById(R.id.tvTreatName);
            tvSocialNum = (TextView) itemView.findViewById(R.id.tvSocialNum);
            tvAfterPayState = (TextView) itemView.findViewById(R.id.tvAfterPayState);
            tvMobilePayState = (TextView) itemView.findViewById(R.id.tvMobilePayState);
            tvToPay = (TextView) itemView.findViewById(R.id.tvToPay);
            tvToPayFee = (TextView) itemView.findViewById(R.id.tvToPayFee);
            llToPayFee = (LinearLayout) itemView.findViewById(R.id.llToPayFee);
            initData();
            initListener();
        }

        private void initData() {
            if (tvPayToast.getVisibility() == View.VISIBLE) {
                tvPayToast.setText(Html.fromHtml(mContext.getString(R.string.wonders_mark_text)));
            }
        }

        private void initListener() {
            // 选择医院
            tvSelectHospital.setOnClickListener(v ->
                    ((Activity) mContext).startActivityForResult(new Intent(mContext,
                            SelectHospitalActivity.class), IntentExtra.REQUEST_CODE));
            // 缴费记录
            tvPayRecord.setOnClickListener(v -> mContext.startActivity(
                    new Intent(mContext, PayRecordActivity.class)));
            // 去缴费
            tvToPayFee.setOnClickListener(v -> mContext.startActivity(
                    new Intent(mContext, PaymentDetailsActivity.class)));
            // 去开通医后付
            tvAfterPayState.setOnClickListener(v -> mContext.startActivity(
                    new Intent(mContext, OpenAfterPayActivity.class)));
            // 去开通医保移动支付
            tvMobilePayState.setOnClickListener(v -> openMobilePay());
            // 设置
            tvSettings.setOnClickListener(v -> mContext.startActivity(
                    new Intent(mContext, SettingsActivity.class)));
        }

        @SuppressLint("SetTextI18n")
        public void setData(AfterHeaderBean afterHeaderBean) {
            if (afterHeaderBean != null) {
                String name = afterHeaderBean.getName();
                String cardNo = afterHeaderBean.getSocialNum();
                String signingStatus = afterHeaderBean.getSigningStatus();
                String mobPayStatus = afterHeaderBean.getMobPayStatus();
                String hospitalName = afterHeaderBean.getHospitalName();

                tvTreatName.setText(name);
                if (!TextUtils.isEmpty(hospitalName)) {
                    tvHospitalName.setText(hospitalName);
                }
                tvSocialNum.setText(mContext.getString(R.string.wonders_text_social_number) + cardNo);

                if ("00".equals(signingStatus)) { // 00未签约（医后付状态给NULL）
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

                if ("01".equals(mobPayStatus)) { // 01已签约
                    setMobilePayState(false);
                } else { // 00 02 未签约 其他
                    setMobilePayState(true);
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
            AuthCall.businessProcess(mContext,
                    MakeArgsFactory.getBussArgs(), WToastUtil::show);
        }
    }

    // 2.List 数据类型
    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFeeName;
        private TextView tvDepartment;
        private TextView tvTimestamp;
        private TextView tvMoney;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvFeeName = itemView.findViewById(R.id.tvFeeName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvMoney = itemView.findViewById(R.id.tvMoney);
        }

        public void setData(FeeBillEntity.DetailsBean detailsBean) {

        }
    }
}
