# 湖州医后付SDK集成文档V2.2.0

>**名称：** 湖州医后付 Android SDK 集成文档  
 **版本：** V2.2.0  
 **作者：** 辛鹏飞  
 **更新日期：** 2019-08-27

## 一、历史修订记录

### V1.0.0

> 2018-09-06 

- 发布初版;

### V1.1.0

> 2018-09-10 

- 完成缴纳详情页面逻辑;
- 增加 demo 测试数据；
- 优化 SDK 加载；

### V1.2.0

> 2018-09-17 

- 完成获取账单及详情数据；
- 发起锁单和试结算；
- 完成支付宝个人支付及医保结算流程；
- 更多细节优化；

### V1.3.0

> 2018-10-10 

- 获取已完成账单 & 未完成订单；
- 发起正式结算；
- 完成未完成订单支付跳转；
- 更多细节优化；

### V1.3.1

> 2018-10-15 

- 更多细节优化；

### V1.4.0

> 2018-11-02 

- 修改支付结算流程；
- 修改主页面缴费逻辑；
- 更多细节优化及 bug fix；

### V1.5.0

> 2018-11-15 

- 增加自费卡结算；
- 修改门诊订单页面；
- 修改调用接口方法名及参数；
- 完成住院部分布局；
- 更多细节优化及 bug fix；

### V1.5.1

> 2018-11-20 

- 增加门诊订单二维码；
- 修改已完成订单页面；
- 自费卡显示优化；
- bugs fix；

### V1.6.0

> 2018-12-25 

- 完成出院结算；
- 完成出院结算结果页面；
- 历史住院记录查询；
- 优化细节，提升用户体验；
- bugs fix；

### V2.0.0

> 2019-06-14 

- 替换新的医保SDK；
- 发布到远程仓库；
- bugs fix；

### V2.1.0

> 2019-07-05 

- 更新医保SDK；
- 个人信息加密处理；
- bugs fix；

### V2.2.0

> 2019-08-27

- 更新医保SDK；
- 电子社保卡SDK切换为正式环境；
- 修改相关文案；
- 身份证号码脱敏处理；
- bugs fixed;

## 二、集成步骤

### 1.

在工程的 build.gradle 文件的 repositories 闭包下添加如下三个远程仓库源：

```
maven { url "https://jitpack.io" }
flatDir { dirs 'libs' }
maven { url "https://dl.bintray.com/wondersgroupandroid863/WondersGroupSdk" }
```

### 2.

将提供的 PALiveDetect1.0.0.aar 和 zj_essc_sdk-1.0.6.aar 两个 aar 包放入 app 的 libs 目录下。

### 3.

在 app 的 build.gradle 文件 的 dependencies 闭包中添加如下依赖：

```
implementation(name: 'PALiveDetect1.0.0', ext: 'aar')
implementation(name: 'zj_essc_sdk-1.0.6', ext: 'aar')
implementation 'com.wondersgroup.android:WondersGroupSdk:0.1.6'
```

注：SDK 使用 28 api 构建，依赖了 support/appcompat 28.0.0 库，因此集成方需要统一处理相同的依赖版本，防止崩溃！！！

### 4.

将 so 文件复制到你们的 libs 目录或者 jniLibs 目录下，然后设置 so 文件的架构，默认 sdk 内置了 'armeabi-v7a' 和 'arm64-v8a' 两种 so 文件，可根据实际情况按需使用一套或多套即可，全部添加会增加 apk 包的大小，使用如下代码可以进行过滤：

然后在 app 的 *build.gradle* 文件中的 **android** 闭下的 **defaultConfig** 闭包中添加如下代码：

```
ndk {
	abiFilters 'armeabi-v7a', 'arm64-v8a'
}
```

注：不支持 armeabi 架构！！！

### 5.

配置 Java 8 编译环境，SDK 中使用了 Java 8 的相关特性，因此在编译时需要配置 Java 8 编译环境，在 app 的 build.gradle 文件的 **android** 闭包中添加如下代码：

```
	compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

### 6.

以上所有配置完成后，同步一下工程即可。

## 三、入口参数及调用

### 1.初始化医后付 SDK 

再 Application 的 onCreate() 方法中添加如下代码：

```
ConfigOption option = new ConfigOption()
                .setDebug(true) // 设置是否为调试模式(调试模式可以打印日志，上线后建议设为 false)
                .setEnv("test"); // 设置环境(test 或 TEST 为测试环境，不设置或者其他默认为正式环境)
// Application context & option parameters.
WondersSdk.getInstance().init(this, option);
```

### 2.业务调用接口

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

## 四、代码混淆

关于代码混淆，如果你们自己项目中已经配置了相关的混淆规则，可按需添加需要混淆的规则即可，无需添加重复的混淆规则，下面是 **example** 中配置的打包时的混淆规则，更多可参考 example app 下的 **proguard-rules.pro** 文件；

```
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
	-keep class com.wondersgroup.android.jkcs_sdk.entity.**{*;}
	
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
```

## 五、FAQ

### 1.第三方依赖库打包冲突

*okhttp3* 依赖冲突问题

在 **app** 的 **build.gradle** 中添加进行过滤去重：

```
configurations {
    compile.exclude module: 'okhttp'
}
```

```
configurations {
	all*.exclude group: 'com.squareup.okhttp3', module: 'okhttp'
}
```

### 2.关于 64k 问题

如果在集成的时候遇到 64k 错误，请按如下配置：

1.添加 multiDexEnabled true

```
android {
	...
	defaultConfig {
		...
       multiDexEnabled true
   }
}
```

2.添加依赖并同步一下工程：

```
implementation 'com.android.support:multidex:1.0.3'
```

3.在你们自己的 Application 类中，继承于 MultiDexApplication 类，并重写如下方法：

```
@Override
protected void attachBaseContext(Context base) {
	super.attachBaseContext(base);
	MultiDex.install(this);
}
```
### 3.Android P httpclient 库

从 Android P 开始，默认移除了 httpclient 库，由于 SDK 内部使用了该库，因此，需要做如下适配：

在 app 的 build.gradle 文件的 android 闭包下添加如下代码：

```
useLibrary 'org.apache.http.legacy'
```

然后打开 AndroidManifest.xml 文件，在 application 标签内部添加如下代码：

```
<uses-library
	android:name="org.apache.http.legacy"
	android:required="false" />
```

### 4.微信 jar 包冲突问题

由于 SDK 内部依赖了微信支付的 jar 包，有可能会和你们项目中的 jar 发生冲突，此时，可以需要 exclude 掉即可：

```
implementation('com.wondersgroup.android:WondersGroupSdk:0.1.6',{
	exclude group: "com.tencent.mm"
})
``` 

## 六、其他

如有其他问题沟通请联系：

QQ:542270559