package cashier.appnative;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cashier.PayMethodListItem;
import cashier.ParamConfig;
import cashier.SPUtils;
import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDCallBack;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;
import cn.wd.checkout.api.WDReqParams;
import cn.wd.checkout.api.WDReqParams.WDChannelTypes;
import cn.wd.checkout.api.WDResult;

public class PayDemoActivity extends Activity {

    private ListView payMethod;
    private ProgressDialog loadingDialog;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHss",
            Locale.CHINA);
    SimpleDateFormat simpleDateFormattemp = new SimpleDateFormat("SSS",
            Locale.CHINA);
    /**
     * (result) |--成功 WDPayResult.RESULT_SUCCESS_HANDLER = 1 ;
     * WDPayResult.RESULT_SUCCESS = "SUCCESS"; | |--用户取消
     * WDPayResult.RESULT_CANCEL_HANDLER = -1; WDPayResult.RESULT_CANCEL =
     * "CANCEL"; | | |-- 调用sdk失败 (errMsg) |WDPayResult.FAIL_UNKNOWN_WAY =
     * "UNKNOWN_WAY" 未知的支付渠道 | | |WDPayResult.FAIL_EXCEPTION = "FAIL_EXCEPTION";
     * 参数初始错误 或 调起微信支付sdk错误 | | |WDPayResult.FAIL_INVALID_PARAMS =
     * "FAIL_INVALID_PARAMS" ; 支付参数不合法 、 支付渠道参数不合法 |--失败
     * WDPayResult.RESULT_FAIL= "FAIL"--- | |WDPayResult.FAIL_NETWORK_ISSUE =
     * "FAIL_NETWORK_ISSUE"; 网络问题造成的支付失败 | WDPayResult.RESULT_FAIL_HANDLER = 0;|
     * | | | |--支付渠道返回失败 (errMsg)|WDPayResult.RESULT_PAYING_UNCONFIRMED =
     * "RESULT_PAYING_UNCONFIRMED"; 订单正在处理中，无法获取成功确认信息
     * |WDPayResult.FAIL_ERR_FROM_CHANNEL =
     * "FAIL_ERR_FROM_CHANNEL";从第三方app支付渠道返回的错误信息（支付渠道返回失败）
     */
    // 支付结果返回入口
    WDCallBack bcCallback = new WDCallBack() {
        @Override
        public void done(final WDResult bcResult) {
            final WDPayResult bcPayResult = (WDPayResult) bcResult;
            PayDemoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CloesLoading();
                    String result = bcPayResult.getResult();
                    Log.i("demo", "done   result=" + result);
                    if (result.equals(WDPayResult.RESULT_SUCCESS))
                        Toast.makeText(PayDemoActivity.this, "用户支付成功", Toast.LENGTH_LONG).show();
                    else if (result.equals(WDPayResult.RESULT_CANCEL))
                        Toast.makeText(PayDemoActivity.this, "用户取消支付", Toast.LENGTH_LONG).show();
                    else if (result.equals(WDPayResult.RESULT_FAIL)) {
                        String info = "支付失败, 原因: " + bcPayResult.getErrMsg() + ", " + bcPayResult.getDetailInfo();
                        Toast.makeText(PayDemoActivity.this, info,
                                Toast.LENGTH_LONG).show();
                    } else if (result.equals(WDPayResult.FAIL_UNKNOWN_WAY)) {
                        Toast.makeText(PayDemoActivity.this, "未知支付渠道", Toast.LENGTH_LONG).show();
                    } else if (result
                            .equals(WDPayResult.FAIL_WEIXIN_VERSION_ERROR)) {
                        Toast.makeText(PayDemoActivity.this,
                                "针对微信 支付版本错误（版本不支持）", Toast.LENGTH_LONG).show();
                    } else if (result.equals(WDPayResult.FAIL_EXCEPTION)) {
                        Toast.makeText(PayDemoActivity.this, "支付过程中的Exception",
                                Toast.LENGTH_LONG).show();
                    } else if (result.equals(WDPayResult.FAIL_ERR_FROM_CHANNEL)) {
                        Toast.makeText(
                                PayDemoActivity.this,
                                "从第三方app支付渠道返回的错误信息，原因: "
                                        + bcPayResult.getErrMsg(),
                                Toast.LENGTH_LONG).show();
                    } else if (result.equals(WDPayResult.FAIL_INVALID_PARAMS)) {
                        Toast.makeText(PayDemoActivity.this, "参数不合法造成的支付失败",
                                Toast.LENGTH_LONG).show();
                    } else if (result
                            .equals(WDPayResult.RESULT_PAYING_UNCONFIRMED)) {
                        Toast.makeText(PayDemoActivity.this, "表示支付中，未获取确认信息",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(PayDemoActivity.this, "invalid return",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    };

    private EditText mGoodsMoney;
    private EditText mGoodsTitle;
    private EditText mGoodsTitleDesc;
    private EditText mOrderTitle;
    private EditText mOrderTitleDesc;
    // appid
    private String strAppid;
    // app secret 工作密钥
    private String strAppsecret;
    // 填写当前appid 对应的子商户号
    private String submerno;
    // 访问环境标识 1:联调测试环境 2：正式环境 0:开发测试环境
    private int wayid;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_native_pay);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mContext = this;
        strAppid = SPUtils.getString(getApplicationContext(), SPUtils.MERAPPID, "");
        strAppsecret = SPUtils.getString(getApplicationContext(), SPUtils.MERAPPKEY, "");
        submerno = SPUtils.getString(getApplicationContext(), SPUtils.MERSUBMERNO, "");


        wayid = ParamConfig.Networke_way;

        payMethod = (ListView) this.findViewById(R.id.payMethod);
        Integer[] payIcons = new Integer[]{
                R.drawable.wechat, R.drawable.alipay,
                R.drawable.unionpay};
        final String[] payNames = new String[]{"微信支付",
                "支付宝支付", "银联支付"};
        String[] payDescs = new String[]{
                "使用微信支付，以人民币CNY计费", "使用支付宝支付，以人民币CNY计费",
                "使用银联支付，以人民币CNY计费"};
        PayMethodListItem adapter = new PayMethodListItem(this, payIcons,
                payNames, payDescs);
        payMethod.setAdapter(adapter);

        setListViewHeightBasedOnChildren(payMethod);

        mGoodsMoney = (EditText) findViewById(R.id.edt_main_money);
        mGoodsTitle = (EditText) findViewById(R.id.edt_main_goods_title);
        mGoodsTitleDesc = (EditText) findViewById(R.id.edt_main_goods_title_desc);
        mOrderTitle = (EditText) findViewById(R.id.edt_main_order_title);
        mOrderTitleDesc = (EditText) findViewById(R.id.edt_main_order_title_desc);
        mOrderTitle.setText(getBillNum());

        // 如果调起支付太慢, 可以在这里开启动画, 以progressdialog为例
        loadingDialog = new ProgressDialog(PayDemoActivity.this);
        loadingDialog.setMessage("启动第三方支付，请稍候...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        payMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                CheckOut.setIsPrint(true);
                /**
                 * 设置访问网络环境 CT 为联调测试环境 不调用此方法为生产环境
                 */
                if (wayid == 1) {
                    Toast.makeText(getApplicationContext(), "联调测试环境",
                            Toast.LENGTH_SHORT).show();
                    CheckOut.setNetworkWay("CT");
                } else if (wayid == 2) {
                    Toast.makeText(getApplicationContext(), "正式环境",
                            Toast.LENGTH_SHORT).show();
                    CheckOut.setNetworkWay("");

                } else {
                    Toast.makeText(getApplicationContext(), "开发测试环境",
                            Toast.LENGTH_SHORT).show();
                    CheckOut.setNetworkWay("CST");
                }
//				CheckOut.setCustomURL("http://cashzsc.wdepay.cn:20080", "directpay/payService.do?");

                mOrderTitle.setText(getBillNum());
                String money = mGoodsMoney.getText().toString().trim();
                String goodsTitle = mGoodsTitle.getText().toString().trim();
                String goodsDesc = mGoodsTitleDesc.getText().toString().trim();
                String orderTitle = mOrderTitle.getText().toString().trim();
                String orderDesc = mOrderTitleDesc.getText().toString().trim();
                Long i = 0l;
                if (isNumeric(money)) {
                    i = Long.parseLong(money);
                } else {
                    Toast.makeText(PayDemoActivity.this, "请输入正确的交易金额（单位：分）",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                switch (position) {

                    case 0: // 微信
                        loadingDialog.show();
                        // 对于微信支付, 手机内存太小会有OutOfResourcesException造成的卡顿, 以致无法完成支付
                        // 这个是微信自身存在的问题
                        WDPay.reqPayAsync(mContext,
                                strAppid, strAppsecret,
                                WDChannelTypes.wepay, submerno,
                                goodsTitle, // 订单标题
                                goodsDesc, i, // 订单金额(分)
                                orderTitle, // 订单流水号
                                orderDesc, null, // 扩展参数(可以null)
                                bcCallback);

                        break;
                    case 1: // 支付宝支付
                        loadingDialog.show();

                        WDPay.reqPayAsync(mContext,
                                strAppid, strAppsecret,
                                WDReqParams.WDChannelTypes.alipay, submerno,
                                goodsTitle, // 订单标题
                                goodsDesc, i, // 订单金额(分)
                                orderTitle, // 订单流水号
                                orderDesc, null, // 扩展参数(可以null)
                                bcCallback);

                        break;
                    case 2: // 银联支付
                        loadingDialog.show();

                        WDPay.reqPayAsync(mContext,
                                strAppid, strAppsecret,
                                WDReqParams.WDChannelTypes.uppaydirect_appand, submerno,
                                goodsTitle, // 订单标题
                                goodsDesc, i, // 订单金额(分)
                                orderTitle, // 订单流水号
                                orderDesc, null, // 扩展参数(可以null)
                                bcCallback);

                        break;
                    default:
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        CloesLoading();
    }

    private void CloesLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            // 此处关闭loading界面
            loadingDialog.dismiss();
        }
    }

    ;

    String getBillNum() {
        return "974" + simpleDateFormat.format(new Date())
                + simpleDateFormattemp.format(new Date()) + "and";
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]+(.[0-9]{1,2})?$");
        else
            return false;
    }


    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //统一支付平台 调用支付结束
        WDPay.ReleasePayserver();
    }

}
