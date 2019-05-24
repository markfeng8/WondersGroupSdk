package com.wondersgroup.android.sdk.entity;

/**
 * Created by x-sir on 2018/10/10 :)
 * Function:sdk 初始化时的参数配置类
 */
public class ConfigOption {

    private boolean isDebug; // 是否是调试模式
    private String env; // 设置环境（"test"为测试环境，为空或其他为正式环境）

    public ConfigOption() {
    }

    public ConfigOption setDebug(boolean isDebug) {
        this.isDebug = isDebug;
        return this;
    }

    public ConfigOption setEnv(String env) {
        this.env = env;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getEnv() {
        return env;
    }
}
