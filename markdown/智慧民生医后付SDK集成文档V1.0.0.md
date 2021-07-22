# 智慧民生医后付SDK集成文档v1.0.0

>**名称：** 智慧民生医后付SDK集成文档V1.0.0  
 **版本：** V1.1.3
 **作者：** 冯传田
 **更新日期：** 2021.7.16

## 一、版本记录

### v1.0.0

> 2021.5.6

* 发布初版；医后付功能

### v1.1.2

> 2021.7.14

* 修改接口超时请求异常提示

## 二、集成步骤

### 1

在project目录下的bulid.gradle文件 的 allprojects闭包里添加 jitpack 远程依赖
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

在app目录下的bulid.gradle文件 的 dependencies 闭包中添加sdk依赖
```
dependencies {
implementation ' implementation 'com.github.markfeng8:zhmsSdk:1.1.2''
}
```
### 2
将提供的 PALiveDetect4.9.2.aar、alipaySdk-15.6.5-20190718211148.aar和 zj_essc_sdk-2.0.9.aar 3个 aar 包放入 app 的libs文件夹内。

### 3
在 app 的 build.gradle 文件 的 dependencies 闭包中添加如下依赖：
```
    implementation(name: 'PALiveDetect4.9.2', ext: 'aar')
    implementation(name: 'zj_essc_sdk-2.0.9', ext: 'aar')
    implementation(name: 'alipaySdk-15.6.5-20190718211148', ext: 'aar')
```

### 4

然后在 app 的 *build.gradle* 文件中的 **android** 闭下的 **defaultConfig** 闭包中添加如下代码：（电子社保卡需要的配置项，如果已配置可以忽略）

```
ndk {
	abiFilters 'armeabi-v7a', 'arm64-v8a'
}
```

### 5

配置 Java 8 编译环境，SDK 中使用了 Java 8 的相关特性，因此在编译时需要配置 Java 8 编译环境，在 app 的 build.gradle 文件的 **android** 闭包中添加如下代码：

```
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

### 6

以上所有配置完成后，同步一下工程即可。


## 三、入口参数及调用


### 1. 初始化医后付sdk
在 Application 的 onCreate() 方法中添加如下代码：

```
ConfigOption option = new ConfigOption()
                .setDebug(true) // 设置是否为调试模式(调试模式可以打印日志，上线后建议设为 false)
                .setEnv("test"); // 设置环境(test 或 TEST 为测试环境，不设置或者其他默认为正式环境)
// Application context & option parameters.
//WondersSdk的初始化不包含电子社保卡SDK的初始化，需要外部自行初始化
WondersSdk.getInstance().init(this, option);
```

### 2. 业务调用

跳转到医后付、自费卡、住院服务首页，请仔细查看对应参数的说明！！！

```
	/**
     * 《 跳转到医后付首页、自费卡、住院页面 》
     *
     * @param flag 业务标志 0 医后付 1 自费卡 2 住院
     *             注：flag 为 0 和 2 时 cardType 一定传 0(社保卡)，
     *             flag 为 1 时 cardType 传 2(自费卡)
     */
    private void startBusiness(int flag) {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String idType = etIdType.getText().toString().trim();
        String idNum = etIdNum.getText().toString().trim();
        String cardType = etCardType.getText().toString().trim();
        String cardNum = etCardNum.getText().toString().trim();
        String homeAddress = etHomeAddress.getText().toString().trim();

        // 设置需要传递的参数，所有参数都为必须！！！
        UserBuilder userBuilder = new UserBuilder()
                .setName(name) // 姓名
                .setPhone(phone) // 手机号
                .setIdType(idType) // 证件类型(01：身份证)
                .setIdNum(idNum) // 证件号码
                .setCardType(cardType) // 就诊卡类型(0：社保卡 2：自费卡)
                .setCardNum(cardNum) // 就诊卡号
                .setAddress(homeAddress); // 家庭地址

        WondersGroup.startBusiness(MainActivity.this, userBuilder, flag);
    }
```

### 3. 外部参数传入

SDK内部需要外部载体的参数通过接口实现的方法传入参数。
```
 /**
        WondersImp.setWondersExternParamsImp(new WondersImp.WondersParamsImp() {
                    @Override
                    public WondersExternParams getExternParams(WondersOutParams outParams,
                                                               WondersImp.WondersSignImp signImp) {
                        Log.i("WondersOutParams", outParams.toString());
                        WondersExternParams params = new WondersExternParams();
                        if ("0".equals(outParams.getType())) {//获取渠道信息、渠道号
                            params.setChannelNo("渠道号");
                            params.setQDCODE("渠道信息");
                        } else if ("1".equals(outParams.getType())) {//申领社保卡sign
                            params.setSign("申领社保卡sign");
                            signImp.getSignParams(params);
                        } else if ("2".equals(outParams.getType())) {//支付验证sign
                            params.setSign("支付验证sign");
                            signImp.getSignParams(params);
                        } else if ("3".equals(outParams.getType())) {//sdk内部返回电子社保卡sdk回调的内容
                            String result = outParams.getZjEsscSDKResult();
                        }
                        return params;
                    }
                });
```

## 四、代码混淆

关于代码混淆，如果你们自己项目中已经配置了相关的混淆规则，可按需添加需要混淆的规则即可，无需添加重复的混淆规则，下面是 **example** 中配置的打包时的混淆规则，更多可参考 example app 下的 **proguard-rules.pro** 文件；

```
	#友盟分享
    -dontshrink
    -dontoptimize
    -dontwarn com.google.android.maps.**
    -dontwarn android.webkit.WebView
    -dontwarn com.umeng.**
    -dontwarn com.tencent.weibo.sdk.**
    -dontwarn com.facebook.**
    -keep public class javax.**
    -keep public class android.webkit.**
    -dontwarn androidx.**
    -keep enum com.facebook.**
    -keepattributes Exceptions,InnerClasses,Signature
    -keepattributes *Annotation*
    -keepattributes SourceFile,LineNumberTable
    -keep public interface com.facebook.**
    -keep public interface com.tencent.**
    -keep public interface com.umeng.socialize.**
    -keep public interface com.umeng.socialize.sensor.**
    -keep public interface com.umeng.scrshot.**
    -keep public class com.umeng.socialize.* {*;}
    -keep class com.facebook.**
    -keep class com.facebook.** { *; }
    -keep class com.umeng.scrshot.**
    -keep public class com.tencent.** {*;}
    -keep class com.umeng.socialize.sensor.**
    -keep class com.umeng.socialize.handler.**
    -keep class com.umeng.socialize.handler.*
    -keep class com.umeng.weixin.handler.**
    -keep class com.umeng.weixin.handler.*
    -keep class com.umeng.qq.handler.**
    -keep class com.umeng.qq.handler.*
    -keep class UMMoreHandler{*;}
    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
    -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
    -keep class im.yixin.sdk.api.YXMessage {*;}
    -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
    -keep class com.tencent.mm.sdk.** {
       *;
    }
    -keep class com.tencent.mm.opensdk.** {
       *;
    }
    -keep class com.tencent.wxop.** {
       *;
    }
    -keep class com.tencent.mm.sdk.** {
       *;
    }
    -keep class com.twitter.** { *; }
    -keep class com.tencent.** {*;}
    -dontwarn com.tencent.**
    -keep class com.kakao.** {*;}
    -dontwarn com.kakao.**
    -keep public class com.umeng.com.umeng.soexample.R$*{
        public static final int *;
    }
    -keep public class com.linkedin.android.mobilesdk.R$*{
        public static final int *;
    }
    -keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
    }
    -keep class com.tencent.open.TDialog$*
    -keep class com.tencent.open.TDialog$* {*;}
    -keep class com.tencent.open.PKDialog
    -keep class com.tencent.open.PKDialog {*;}
    -keep class com.tencent.open.PKDialog$*
    -keep class com.tencent.open.PKDialog$* {*;}
    -keep class com.umeng.socialize.impl.ImageImpl {*;}
    -keep class com.sina.** {*;}
    -dontwarn com.sina.**
    -keep class  com.alipay.share.sdk.** {
       *;
    }
    -keepnames class * implements android.os.Parcelable {
        public static final ** CREATOR;
    }
    -keep class com.linkedin.** { *; }
    -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
    -keepattributes Signature
    #Glide
    #-keep public class * implements com.bumptech.glide.module.GlideModule
    #-keep public class * extends com.bumptech.glide.AppGlideModule
    #-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    #  **[] $VALUES;
    #  public *;
    #}

    #okhttp3
    -dontwarn com.squareup.okhttp3.**
    -keep class com.squareup.okhttp3.** { *;}
    -keep class okhttp3.** { *;}
    -keep class okio.** { *;}
    -dontwarn sun.security.**
    -keep class sun.security.** { *;}
    -dontwarn okio.**
    -dontwarn okhttp3.**


    #butterknife
    -keep class butterknife.** { *; }
    -dontwarn butterknife.internal.**
    -keep class **$$ViewBinder { *; }
    -keepclasseswithmembernames class * {
        @butterknife.* <fields>;
    }
    -keepclasseswithmembernames class * {
        @butterknife.* <methods>;
    }

    #retrofit2
    -dontwarn retrofit2.**
    -keep class retrofit2.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions

    #rxjava
    -dontwarn rx.**
    -keep class rx.** { *; }

    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }

    #gson
    -keepattributes Signature
    -keepattributes *Annotation*
    -dontwarn com.google.gson.**
    -keep class com.google.gson.** { *;}
    -keep class sun.misc.Unsafe { *; }
    -keep class com.google.gson.stream.** { *; }
    # Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类
    #-keep class com.example.bean.** { *; }

    # RxPermissions
    -keep class com.tbruyelle.rxpermissions2.** { *; }
    -keep interface com.tbruyelle.rxpermissions2.** { *; }
    -keep class android.net.http.SslError

    #eventbus
    -keepattributes *Annotation*
    -keepclassmembers class ** {
        @org.greenrobot.eventbus.Subscribe <methods>;
    }
    -keep enum org.greenrobot.eventbus.ThreadMode { *; }

    # Only required if you use AsyncExecutor
    -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(java.lang.Throwable);
    }
    #友盟推送
    -dontwarn com.umeng.**
    -dontwarn com.taobao.**
    -dontwarn anet.channel.**
    -dontwarn anetwork.channel.**
    -dontwarn org.android.**
    -dontwarn org.apache.thrift.**
    -dontwarn com.xiaomi.**
    -dontwarn com.huawei.**
    -dontwarn com.meizu.**
    -keepattributes *Annotation*
    -keep class com.taobao.** {*;}
    -keep class org.android.** {*;}
    -keep class anet.channel.** {*;}
    -keep class com.umeng.** {*;}
    -keep class com.xiaomi.** {*;}
    -keep class com.huawei.** {*;}
    -keep class com.meizu.** {*;}
    -keep class org.apache.thrift.** {*;}
    -keep class com.alibaba.sdk.android.**{*;}
    -keep class com.ut.**{*;}
    -keep class com.ta.**{*;}
    -keep public class **.R$*{
       public static final int *;
    }
    #加载动画
    -keep class com.wang.avi.** { *; }
    -keep class com.wang.avi.indicators.** { *; }
    #本地文件混淆
    -keep class **.R$* { *; }
    -keep class com.cosw.zhoushanPublicTrafic.** { *; }
    #PictureSelector 2.0
    -keep class com.luck.picture.lib.** { *; }
    -dontwarn com.yalantis.ucrop**
    -keep class com.yalantis.ucrop** { *; }
    -keep interface com.yalantis.ucrop** { *; }
    #rxandroid
    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
       long producerIndex;
       long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }
    #adapter混淆
    -keep class com.chad.library.adapter.** {
    *;
    }
    -keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
    -keep public class * extends com.chad.library.adapter.base.BaseViewHolder
    -keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
         <init>(...);
    }
    -keepattributes InnerClasses
    #immersionbar
     -keep class com.gyf.immersionbar.* {*;}
     -dontwarn com.gyf.immersionbar.**
     #百度地图混淆
     -keep class com.baidu.** {*;}
     -keep class vi.com.** {*;}
     -dontwarn com.baidu.**
    # 支付宝
    -libraryjars libs/alipaySdk-15.6.5-20190718211148.aar
    -keep class com.alipay.android.app.IAlixPay{*;}
    -keep class com.alipay.android.app.IAlixPay$Stub{*;}
    -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
    -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
    -keep class com.alipay.sdk.app.PayTask{ public *;}
    -keep class com.alipay.sdk.app.AuthTask{ public *;}
    #微信支付
    -keep class com.tencent.mm.opensdk.** {
        *;
    }
    -keep class com.tencent.wxop.** {
        *;
    }
    -keep class com.tencent.mm.sdk.** {
        *;
    }
    #银联支付
    -dontwarn com.unionpay.**
    -keep class com.unionpay.** {*;}
    #云从人脸识别
    -dontwarn cn.cloudwalk.**
    -keep class cn.cloudwalk.**{*;}
    #电子社保卡
    #保留annotation， 例如 @JavascriptInterface 等 annotation
    -keepattributes *Annotation*
    #保留跟 javascript相关的属性
    -keepattributes *JavascriptInterface*
    #保留JavascriptInterface中的方法
    -keepclassmembers class * {@android.webkit.JavascriptInterface <methods>;}
    #对sdk混淆的处理
    -dontwarn hc.mhis.paic.com.essclibrary.**
    -keep class hc.mhis.paic.com.essclibrary.**{*;}
    -dontwarn essclib.**
    -keep class essclib.**{*;}
    -dontwarn pingan.ai.**
    -keep class pingan.ai.**{*;}
    -dontwarn cn.com.epsoft.zjessc.**
    -keep class cn.com.epsoft.zjessc.**{*;}
    -optimizationpasses 5
    	-dontusemixedcaseclassnames
    	-dontskipnonpubliclibraryclasses
    	-verbose
    	-dontskipnonpubliclibraryclassmembers
    	-dontpreverify
    	-keepattributes *Annotation*,InnerClasses
    	-keepattributes Signature
    	-keepattributes SourceFile,LineNumberTable
    	-optimizations !code/simplification/cast,!field/*,!class/merging/*

    	-keep public class * extends android.app.Activity
    	-keep public class * extends android.app.Appliction
    	-keep public class * extends android.app.Service
    	-keep public class * extends android.content.BroadcastReceiver
    	-keep public class * extends android.content.ContentProvider
    	-keep public class * extends android.app.backup.BackupAgentHelper
    	-keep public class * extends android.preference.Preference
    	-keep public class * extends android.view.View
    	-keep public class com.android.vending.licensing.ILicensingService

    	-keep class android.support.** {*;}

    	-keep public class * extends android.support.v4.**
    	-keep public class * extends android.support.v7.**
    	-keep public class * extends android.support.annotation.**

    	-keep class **.R$* {*;}

    	-keepclasseswithmembernames class * {
    	    native <methods>;
    	}

    	-keepclassmembers enum * {
    	    public static **[] values();
    	    public static ** valueOf(java.lang.String);
    	}

    	-keep public class * extends android.view.View{
    	    *** get*();
    	    void set*(***);
    	    public <init>(android.content.Context);
    	    public <init>(android.content.Context, android.util.AttributeSet);
    	    public <init>(android.content.Context, android.util.AttributeSet, int);
    	}

    	-keep class * implements android.os.Parcelable {
    	    public static final android.os.Parcelable$Creator *;
    	}

    	-keepclassmembers class * implements java.io.Serializable {
    	    static final long serialVersionUID;
    	    private static final java.io.ObjectStreamField[] serialPersistentFields;
    	    !static !transient <fields>;
    	    !private <fields>;
    	    !private <methods>;
    	    private void writeObject(java.io.ObjectOutputStream);
    	    private void readObject(java.io.ObjectInputStream);
    	    java.lang.Object writeReplace();
    	    java.lang.Object readResolve();
    	}

    	-keepclassmembers class * {
    	    void *(**On*Event);
    	    void *(**On*Listener);
    	}

    	-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    	    public *;
    	}
    	-keepclassmembers class * extends android.webkit.webViewClient {
    	    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    	    public boolean *(android.webkit.WebView, java.lang.String);
    	}
    	-keepclassmembers class * extends android.webkit.webViewClient {
    	    public void *(android.webkit.webView, jav.lang.String);
    	}

    	-dontwarn android.net.**
    	-keep class android.net.SSLCertificateSocketFactory{*;}

    	-keep class com.shrb.wallet.**
    	-keep class com.shrb.walletsdk.**
    	-dontwarn com.shrb.wallet.**
    	-dontwarn com.shrb.walletsdk.**

    	-keep class cn.keyou.**{ *;}
    	-dontwarn cn.keyou.**

    	-keep class com.alibaba.fastjson.**{ *;}
    	-dontwarn com.alibaba.fastjson.**

    	-keep class okhttp3.**{ *;}
    	-dontwarn okhttp3.**

    	-keep class com.bumptech.glide.**{ *;}
    	-dontwarn com.bumptech.glide.**

    	-keep class com.union.keyboard.**{ *;}
    	-dontwarn com.union.keyboard.**

    	-keep class com.bqs.risk.df.android.**{ *;}
    	-keep class com.bqs.risk.df.android.contact.**{ *;}
    	-dontwarn com.bqs.risk.df.android.**
    	-dontwarn com.bqs.risk.df.android.contact.**

    	-keep class org.apache.http.**{ *;}
    	-dontwarn org.apache.http.**

    	#-dontwarn okio.**

    	-keepclassmembers class * {
    	public <methods>;
    	}
    	-keepattributes InnerClasses,Signature

    	-dontwarn com.android.volley.**

    	-keep class com.android.volley.** {*;}
    	-dontwarn com.alipay.**

    	-keep class com.alipay.** {*;}


    	# ########## WdPay  ###########
    	-dontwarn cn.wd.checkout.**
    	-keep class cn.wd.checkout.** {*;}

    	# ########## gson ##########
    	-keep class com.google.**{*;}

    	# ########## 链支付 ##########
    	-dontwarn cn.wanda.lianpay.**
    	-keep class cn.wanda.lianpay.** {*;}
    	-keep class cn.wanda.processor.** {*;}
    	-keep class cn.wanda.support.** {*;}
    	-dontwarn cn.wanda.processor.**

    	# ########## 支付宝 ##########
    	-keep class com.alipay.android.app.IAlixPay{*;}
    	-keep class com.alipay.android.app.IAlixPay$Stub{*;}
    	-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
    	-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
    	-keep class com.alipay.sdk.app.PayTask{ public *;}
    	-keep class com.alipay.sdk.app.AuthTask{ public *;}

    	# ########## 银联 ##########
    	-dontwarn org.simalliance.openmobileapi.**
    	-dontwarn org.simalliance.openmobileapi.service.**

    	-keep class org.simalliance.openmobileapi.** {*;}
    	-keep class org.simalliance.openmobileapi.service.** {*;}

    	-keep class com.unionpay.** {*;}

    	-keep  public class com.unionpay.uppay.net.HttpConnection {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.net.HttpParameters {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.model.BankCardInfo {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.model.PAAInfo {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.model.ResponseInfo {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.model.PurchaseInfo {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.util.DeviceInfo {
    		public <methods>;
    	}
    	-keep  public class java.util.HashMap {
    		public <methods>;
    	}
    	-keep  public class java.lang.String {
    		public <methods>;
    	}
    	-keep  public class java.util.List {
    		public <methods>;
    	}
    	-keep  public class com.unionpay.uppay.util.PayEngine {
    		public <methods>;
    		native <methods>;
    	}

    	-keepclasseswithmembernames class * {
    	    native <methods>;
    	}

    	-keepclasseswithmembers class * {
    	    public <init>(android.content.Context, android.util.AttributeSet);
    	}

    	-keepclasseswithmembers class * {
    	    public <init>(android.content.Context, android.util.AttributeSet, int);
    	}

    	-keepclassmembers class * extends android.app.Activity {
    	   public void *(android.view.View);
    	}

    	-keepclassmembers enum * {
    	    public static **[] values();
    	    public static ** valueOf(java.lang.String);
    	}

    	-keep class * implements android.os.Parcelable {
    	  public static final android.os.Parcelable$Creator *;
    	}
    	########## 统一支付 end ##################---------------------------------

    	# ########### epsoft start ##############---------------------------------
    	-keep class com.epsoft.**{*;}
    	-keep class com.itsea.cplusplus.** { *; }

    	# 保留 annotation， 例如 @JavascriptInterface 等 annotation
    	-keepattributes *Annotation*
    	# 保留跟 javascrip t相关的属性
    	-keepattributes *JavascriptInterface*
    	# 保留 JavascriptInterface 中的方法
    	-keepclassmembers class * {@android.webkit.JavascriptInterface <methods>;}

    	-dontwarn cn.com.epsoft.zjessc.**
    	-keep class cn.com.epsoft.zjessc.**{*;}
    	-dontwarn hc.mhis.paic.com.essclibrary.**
    	-keep class hc.mhis.paic.com.essclibrary.** { *;}
    	-dontwarn com.pingan.ai.**
    	-keep class com.pingan.ai.** { *;}
    	-dontwarn pingan.ai.**
    	-keep class pingan.ai.** { *;}
    	-dontwarn com.google.zxing.**
    	-keep class com.google.zxing.**{*;}
    	-dontwarn com.esscpermission.**
    	-keep class com.esscpermission.**{*;}

    	#okhttp
    	-dontwarn okhttp3.**
    	-keep class okhttp3.**{*;}

    	#okio
    	-dontwarn okio.**
    	-keep class okio.**{*;}

    	#okgo
    	-dontwarn com.lzy.okgo.**
    	-keep class com.lzy.okgo.**{*;}
    	#-keep class com.google.gson.**{*;}
    	-keep class com.orhanobut.logger.**{*;}

    	-dontwarn com.tencent.bugly.**
    	-keep public class com.tencent.bugly.**{*;}

    	-dontwarn com.tencent.**
    	-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
    	-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
    	-keep class im.yixin.sdk.api.YXMessage {*;}
    	-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

    	# 友盟
    	-dontshrink
    	-dontoptimize
    	-dontwarn com.google.android.maps.**
    	-dontwarn android.webkit.WebView
    	-dontwarn com.umeng.**
    	-dontwarn com.tencent.weibo.sdk.**
    	-dontwarn com.facebook.**

    	-keep enum com.facebook.**
    	-keepattributes Exceptions,InnerClasses,Signature
    	-keepattributes *Annotation*
    	-keepattributes SourceFile,LineNumberTable

    	-keep public interface com.facebook.**
    	-keep public interface com.tencent.**
    	-keep public interface com.umeng.socialize.**
    	-keep public interface com.umeng.socialize.sensor.**
    	-keep public interface com.umeng.scrshot.**

    	-keep public class com.umeng.socialize.* {*;}
    	-keep public class javax.**
    	-keep public class android.webkit.**

    	-keep class com.facebook.**
    	-keep class com.umeng.scrshot.**
    	-keep public class com.tencent.** {*;}
    	-keep class com.umeng.socialize.sensor.**

    	-keep class com.alipay.android.app.IAlixPay{*;}
    	-keep class com.alipay.android.app.IAlixPay$Stub{*;}
    	-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
    	-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
    	-keep class com.alipay.sdk.app.PayTask{ public *;}
    	-keep class com.alipay.sdk.app.AuthTask{ public *;}
    	-keep class com.alipay.sdk.app.H5PayCallback {
    	    <fields>;
    	    <methods>;
    	}
    	-keep class com.alipay.android.phone.mrpc.core.** { *; }
    	-keep class com.alipay.apmobilesecuritysdk.** { *; }
    	-keep class com.alipay.mobile.framework.service.annotation.** { *; }
    	-keep class com.alipay.mobilesecuritysdk.face.** { *; }
    	-keep class com.alipay.tscenter.biz.rpc.** { *; }
    	-keep class org.json.alipay.** { *; }
    	-keep class com.alipay.tscenter.** { *; }
    	-keep class com.ta.utdid2.** { *;}
    	-keep class com.ut.device.** { *;}
    	-dontwarn com.alipay.**
    	# ########### epsoft end ##############

    	# 保留所有实体类不被混淆
        -keep class com.wondersgroup.android.sdk.entity.**{*;}
        # 保留所有自定义view不被混淆
        -keep class com.wondersgroup.android.sdk.widget.**{*;}

    	# ButterKnife
    	-keep class butterknife.** { *; }
    	-dontwarn butterknife.internal.**
    	-keep class **$$ViewBinder { *; }
    	-keepclasseswithmembernames class * {
    	    @butterknife.* <fields>;
    	}
    	-keepclasseswithmembernames class * {
    	    @butterknife.* <methods>;
    	}

    	# Gson
    	-keep class com.google.gson.**{*;}
    	-keep interface com.google.gson.**{*;}

    	# Retrofit
    	-dontwarn okio.**
    	-dontwarn javax.annotation.**
    	# Platform calls Class.forName on types which do not exist on Android to determine platform.
    	-dontnote retrofit2.Platform
    	# Platform used when running on RoboVM on iOS. Will not be used at runtime.
    	-dontnote retrofit2.Platform$IOS$MainThreadExecutor
    	# Platform used when running on Java 8 VMs. Will not be used at runtime.
    	-dontwarn retrofit2.Platform$Java8

        # BaseRecyclerViewAdapterHelper
        -keep class com.chad.library.adapter.** { *; }
        -keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
        -keep public class * extends com.chad.library.adapter.base.BaseViewHolder
        -keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
             <init>(...);
        }

        #--------------------------------- 项目个性化配置 start ------------------------------------
        # 保留所有实体类不被混淆
        -keep class com.wondersgroup.android.sdk.entity.**{*;}

        # 不混淆接口数据响应实体类的内部类
        -keep class com.wondersgroup.android.sdk.entity.HospitalEntity$* {
            *;
        }
        -keep class com.wondersgroup.android.sdk.entity.FeeRecordEntity$* {
            *;
        }
        -keep class com.wondersgroup.android.sdk.entity.Cy0001Entity$* {
            *;
        }
        -keep class com.wondersgroup.android.sdk.entity.Cy0005Entity$* {
            *;
        }
        -keep class com.wondersgroup.android.sdk.entity.FeeRecordEntity$* {
            *;
        }
        -keep class com.wondersgroup.android.sdk.entity.OrderDetailsEntity$* {
            *;
        }
        -keep class com.wondersgroup.android.sdk.entity.SettleEntity$* {
            *;
        }

        # 不混淆实现 Parcelable 接口的类
        -keep public class com.wondersgroup.android.sdk.entity.CityBean { *; }
        -keep public class com.wondersgroup.android.sdk.entity.HospitalBean { *; }

        # 保持对外的 SDK 初始化的类及相关方法不被混淆(注意方法的参数及返回值类型要使用全类名)
        -keep class com.wondersgroup.android.sdk.WondersSdk {
            public static com.wondersgroup.android.jkcs_sdk.WondersSdk getInstance();
            public void init(android.content.Context, com.wondersgroup.android.jkcs_sdk.entity.ConfigOption);
        }

        # 保持对外的 SDK 入参类及相关方法不被混淆(注意方法的参数及返回值类型要使用全类名)
        -keep class com.wondersgroup.android.sdk.WondersImp {
            public static com.wondersgroup.android.sdk.WondersImp setWondersExternParamsImp(com.wondersgroup.android.sdk.WondersImp.WondersParamsImp);
        }

        -keep interface com.wondersgroup.android.sdk.WondersImp$WondersParamsImp {
            public *;
        }

        -keep interface com.wondersgroup.android.sdk.WondersImp$WondersSignImp {
            public *;
        }

        # 保持对外的调用类 WondersGroup 的类及相关方法不被混淆(注意方法的参数及返回值类型要使用全类名)
        -keep class com.wondersgroup.android.sdk.api.WondersGroup {
            public static void startBusiness(android.content.Context, com.wondersgroup.android.jkcs_sdk.entity.UserBuilder, int);
        }

        # 保持对外的调用类 PaymentUtils 的类及相关方法不被混淆(注意方法的参数及返回值类型要使用全类名)
        -keep class com.wondersgroup.android.sdk.api.PaymentUtils {
            public static void toPay(android.content.Context, java.lang.String);
        }
        #--------------------------------- 项目个性化配置 end --------------------------------------
```