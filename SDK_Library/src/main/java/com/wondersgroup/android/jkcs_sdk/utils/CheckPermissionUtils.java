package com.wondersgroup.android.jkcs_sdk.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/4/2  18:48
 */

public class CheckPermissionUtils {
    private Activity activity;
    public CheckPermissionUtils(Activity activity){
       this.activity=new WeakReference<>(activity).get();
    }
    protected boolean checkCmeraOrWriteStorage(){
        int selfPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        boolean hasPermission = selfPermission== PackageManager.PERMISSION_GRANTED;

        int selfPermission1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean hasPermission1 = selfPermission1== PackageManager.PERMISSION_GRANTED;
        
        if (!hasPermission||!hasPermission1){
            showPermissionDialog("需要相机和读写存储的权限,请前往打开!");
        }
        return hasPermission&&hasPermission1;
    }
    public boolean checkCmera(){
        int selfPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        boolean hasPermission = selfPermission== PackageManager.PERMISSION_GRANTED;

        if (!hasPermission){
            showPermissionDialog("需要相机权限,请前往打开!");
        }
        return hasPermission;
    }
    protected boolean checkWriteStorage(){
        int selfPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean hasPermission = selfPermission== PackageManager.PERMISSION_GRANTED;
        if (!hasPermission){
            showPermissionDialog("需要读写存储的权限,请前往打开!");
        }
        return hasPermission;
    }
    protected boolean checkPhoneState(){
        int selfPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        boolean hasPermission = selfPermission== PackageManager.PERMISSION_GRANTED;
        if (!hasPermission){
            showPermissionDialog("需要读取手机权限,请前往打开!");
        }
        return hasPermission;
    } 
    protected void showPermissionDialog(String message){
        new AlertDialog.Builder(activity)
                .setTitle("温馨提示!")
                .setMessage(message)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JumpPermissionManagement.GoToSetting(activity);
                    }
                }).show().setCancelable(false);
        
    }
}
