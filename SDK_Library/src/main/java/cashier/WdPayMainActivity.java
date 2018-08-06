package cashier;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.R;

import cashier.appnative.PayDemoActivity;
import cashier.web.JSPayActivity;

public class WdPayMainActivity extends Activity implements OnClickListener,
        OnItemClickListener {

    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdpay_main);
        initView();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.main_lv);

        Integer[] payIcons = new Integer[]{R.drawable.icon_app_pay
                , R.drawable.icon_hybrid_pay
                , R.drawable.icon_hybrid_pay
                , R.drawable.icon_scan_pay
                , R.drawable.icon_hybrid_pay};
        final String[] payNames = new String[]{"APP原生支付", "手机网页支付", "JS交互支付", "扫码支付", "银联无跳转支付"};
        String[] payDescs = new String[]{"", "", "", "", "", "", ""};

        PayMethodListItem adapter = new PayMethodListItem(this, payIcons, payNames, payDescs);
        mLv.setAdapter(adapter);

        mLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		 int id = (Integer) SPUtils.get(getApplicationContext(), SPUtils.WAYTAG, 1);
        int id = ParamConfig.Networke_way;
        switch (arg2) {
            case 0:
                startActivity(new Intent(WdPayMainActivity.this, PayDemoActivity.class));
                break;
            case 1:
//			startActivity(new Intent(WdPayMainActivity.this, PayWebActivity.class));
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = null;

                switch (id) {
                    case 0:
                        content_url = Uri.parse(CASHTEST_URL);
                        break;
                    case 1:
                        content_url = Uri.parse(URL_20080);
                        break;
                    case 2:
                        content_url = Uri.parse(URL_30080);
                        break;
                    default:
                        break;
                }
                intent.setData(content_url);
                startActivity(intent);
                break;
            case 2:
                startActivity(new Intent(WdPayMainActivity.this, JSPayActivity.class));
                break;
            case 3:
                Toast.makeText(WdPayMainActivity.this, "敬请期待！", Toast.LENGTH_LONG).show();
                break;
            case 4:
                //银联无跳转支付
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(URL_0605);
                intent2.setData(url);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    //	private static final String DEFAULT_URL = "http://10.1.64.205:18001/WebCashierDesk/text.jsp";
    private static final String CASHTEST_URL = "http://cashtest.wdepay.cn:20080/WebCashierDesk/text.jsp";
    //	private static final String URL_20080 = "http://cash.wdepay.cn:20080/WebCashierDesk/text.jsp" ;
    private static final String URL_20080 = "http://cashzsc.wdepay.cn:20080/WebCashierDesk/text.jsp";
    private static final String URL_30080 = "http://cash.wdepay.cn:30080/WebCashierDesk/text.jsp";
    private static final String URL_0605 = "http://cashkaif.wdepay.cn/CHINAPAY_DEMO_NEW/main.jsp";

    @Override
    public void onClick(View arg0) {

    }

}
