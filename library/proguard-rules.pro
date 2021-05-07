# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/tianjh/Documents/Andriod/android-sdk-mac_86/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-------------------------------------- 基本不用动区域 start ------------------------------------------

#--------------------------------- 基本指令区 start ----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,*JavascriptInterface*,InnerClasses,Signature,SourceFile,LineNumberTable
-ignorewarning
#--------------------------------- 基本指令区 start ----------------------------------

#--------------------------------- 默认保留区 start ----------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
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
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class **.R$* {*;}

-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
#-------------------------------- 默认保留区 end ------------------------------------------

#--------------------------------- webview start ------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
    public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#--------------------------------- webview end ------------------------------------

#------------------------------------- 基本不用动区域 end -------------------------------------------

#--------------------------------- 第三方库 start ------------------------------------
# okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

# okio
-dontwarn okio.**
-keep class okio.**{*;}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# Application classes that will be serialized/deserialized over Gson
-keep class com.wondersgroup.android.jkcs_sdk.entity.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

##---------------End: proguard configuration for Gson  ----------

# Retrofit2
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
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

# volley
-dontwarn com.android.volley.**
-keep class com.android.volley.** {*;}

# alipay
-keep class com.alipay.** {*;}
-dontwarn com.alipay.**

# fastjson
-keep class com.alibaba.fastjson.**{ *;}
-dontwarn com.alibaba.fastjson.**

# glide
-keep class com.bumptech.glide.**{ *;}
-dontwarn com.bumptech.glide.**

# union keyboard
-keep class com.union.keyboard.**{ *;}
-dontwarn com.union.keyboard.**

# apache http
-keep class org.apache.http.**{ *;}
-dontwarn org.apache.http.**

# ssl
-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}

# wallet
-keep class com.shrb.wallet.**
-keep class com.shrb.walletsdk.**
-dontwarn com.shrb.wallet.**
-dontwarn com.shrb.walletsdk.**

# keyou
-keep class cn.keyou.**{ *;}
-dontwarn cn.keyou.**

-keepclassmembers class * {
    public <methods>;
}
-keepattributes InnerClasses,Signature
#--------------------------------- 第三方库 end ------------------------------------

#--------------------------------- Epsoft(医保 SDK 相关) start ----------------------------------
# Epsoft
-keep class com.epsoft.**{*;}
-keep class com.itsea.cplusplus.** {*;}

# 保留 JavascriptInterface 中的方法
-keepclassmembers class * {@android.webkit.JavascriptInterface <methods>;}
-dontwarn cn.com.epsoft.zjessc.**
-keep class cn.com.epsoft.zjessc.**{*;}
-dontwarn hc.mhis.paic.com.essclibrary.**
-keep class hc.mhis.paic.com.essclibrary.** {*;}
-dontwarn com.pingan.ai.**
-keep class com.pingan.ai.** {*;}
-dontwarn pingan.ai.**
-keep class pingan.ai.** {*;}
-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{*;}
-dontwarn com.esscpermission.**
-keep class com.esscpermission.**{*;}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
#--------------------------------- Epsoft(医保 SDK 相关) end ------------------------------------

#------------------------- 万达统一支付 start --------------------------------------------
-dontwarn cn.wd.checkout.**
-keep class cn.wd.checkout.** {*;}

# 链支付
-dontwarn cn.wanda.lianpay.**
-keep class cn.wanda.lianpay.** {*;}
-keep class cn.wanda.processor.** {*;}
-keep class cn.wanda.support.** {*;}
-dontwarn cn.wanda.processor.**

# 支付宝
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

# 银联
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

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#------------------------- 万达统一支付 end ----------------------------------------------

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

# 保持对外的调用类 WondersGroup 的类及相关方法不被混淆(注意方法的参数及返回值类型要使用全类名)
-keep class com.wondersgroup.android.sdk.api.WondersGroup {
    public static void startBusiness(android.content.Context, com.wondersgroup.android.jkcs_sdk.entity.UserBuilder, int);
}

# 保持对外的调用类 PaymentUtils 的类及相关方法不被混淆(注意方法的参数及返回值类型要使用全类名)
-keep class com.wondersgroup.android.sdk.api.PaymentUtils {
    public static void toPay(android.content.Context, java.lang.String);
}
#--------------------------------- 项目个性化配置 end --------------------------------------