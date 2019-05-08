package cn.com.epsoft.zjessc.demo.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author 启研
 * @created at 2018/11/16 10:00
 */
public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("请稍候...");
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示加载条
     */
    public void showProgress(boolean show) {
        showProgress(show, "请稍候...");
    }

    /**
     * 显示加载条
     */
    protected void showProgress(boolean show, String msg) {
        if (show) {
            dialog.setMessage(msg);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else {
            dialog.dismiss();
        }
    }

    /**
     * 显示不同文案
     */
    public void showProgress(String loadMessage) {
        showProgress(true, loadMessage);
    }
}
