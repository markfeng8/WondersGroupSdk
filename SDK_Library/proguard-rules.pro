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

     # 指定代码的压缩级别
     -optimizationpasses 5
     # 包名不混合大小写
     -dontusemixedcaseclassnames
     # 不去忽略非公共的库类
     -dontskipnonpubliclibraryclasses
     # 优化不优化输入的类文件
     -dontoptimize
     # 预校验
     -dontpreverify
     # 混淆时是否记录日志
     -verbose
     # 混淆时所采用的算法
     -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
     # 保护注解
     -keepattributes *Annotation*

     # 保持哪些类不被混淆
     -keep public class * extends android.app.Fragment
     -keep public class * extends android.app.Activity
     -keep public class * extends android.app.Application
     -keep public class * extends android.app.Service
     -keep public class * extends android.content.BroadcastReceiver
     -keep public class * extends android.content.ContentProvider
     -keep public class * extends android.app.backup.BackupAgentHelper
     -keep public class * extends android.preference.Preference
     -keep public class com.android.vending.licensing.ILicensingService
     # 如果有引用v4包可以添加下面这行
     -keep public class * extends android.support.v4.app.Fragment

     # 不混淆某个包下的类
     -keep class com.anloq.utils.** {*;}
     # 忽略警告
     -ignorewarning

     ##记录生成的日志数据,gradle build时在本项目根目录输出

     #apk 包内所有 class 的内部结构
     -dump class_files.txt
     #未混淆的类和成员
     -printseeds seeds.txt
     #列出从 apk 中删除的代码
     -printusage unused.txt
     # 混淆前后的映射
     -printmapping mapping.txt

     ########记录生成的日志数据，gradle build时 在本项目根目录输出-end######

     #如果不想混淆 keep 掉
     -keep class com.lippi.recorder.iirfilterdesigner.** {*; }
     #友盟
     -keep class com.umeng.**{*;}
     #项目特殊处理代码

     #忽略警告
     -dontwarn com.lippi.recorder.utils**
     #保留一个完整的包
     -keep class com.lippi.recorder.utils.** {
         *;
      }

     -keep class  com.lippi.recorder.utils.AudioRecorder{*;}


     #如果引用了v4或者v7包
     -dontwarn android.support.**

     ####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####

     -keep public class * extends android.view.View {
         public <init>(android.content.Context);
         public <init>(android.content.Context, android.util.AttributeSet);
         public <init>(android.content.Context, android.util.AttributeSet, int);
         public void set*(...);
     }

     #保持 native 方法不被混淆
     -keepclasseswithmembernames class * {
         native <methods>;
     }

     #保持自定义控件类不被混淆
     -keepclasseswithmembers class * {
         public <init>(android.content.Context, android.util.AttributeSet);
     }

     #保持自定义控件类不被混淆
     -keepclassmembers class * extends android.app.Activity {
        public void *(android.view.View);
     }

     #保持 Parcelable 不被混淆
     -keep class * implements android.os.Parcelable {
       public static final android.os.Parcelable$Creator *;
     }

     #保持 Serializable 不被混淆
     -keepnames class * implements java.io.Serializable

     #保持 Serializable 不被混淆并且enum 类也不被混淆
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

     #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
     #-keepclassmembers enum * {
     #  public static **[] values();
     #  public static ** valueOf(java.lang.String);
     #}

     -keepclassmembers class * {
         public void *ButtonClicked(android.view.View);
         public <init> (org.json.JSONObject);
     }

     #不混淆资源类
     -keepclassmembers class **.R$* {
         public static <fields>;
         public static final int *;
     }

     #避免混淆泛型 如果混淆报错建议关掉
     #–keepattributes Signature

     #移除log 测试了下没有用还是建议自己定义一个开关控制是否输出日志
     -assumenosideeffects class android.util.Log {
         public static *** v(...);
         public static *** i(...);
         public static *** d(...);
         public static *** w(...);
         public static *** e(...);
     }

     #如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
     #gson
     #-libraryjars libs/gson-2.2.2.jar
     -keepattributes Signature
     # Gson specific classes
     -keep class sun.misc.Unsafe { *; }
     # Application classes that will be serialized/deserialized over Gson
     -keep class com.google.gson.examples.android.model.** { *; }

     #okhttputils
     -dontwarn com.zhy.http.**
     -keep class com.zhy.http.**{*;}

     #okhttp
     -dontwarn okhttp3.**
     -keep class okhttp3.**{*;}

     #okio
     -dontwarn okio.**
     -keep class okio.**{*;}

     #picasso
     #-dontwarn com.squareup.okhttp.**

     #glide
     -keep public class * implements com.bumptech.glide.module.GlideModule
     -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
       **[] $VALUES;
       public *;
     }

     # for DexGuard only
     #-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

     #litepal
     -keep class org.litepal.** { *; }
     -keep class * extends org.litepal.crud.DataSupport { *; }

     # banner 的混淆代码
     #-keep class com.youth.banner.** {
     #    *;
     #}

     # EventBus3.0
     -keepattributes *Annotation*
     -keepclassmembers class ** {
         @org.greenrobot.eventbus.Subscribe <methods>;
     }
     -keep enum org.greenrobot.eventbus.ThreadMode { *; }
        -keepclassmembers enum * {
            public static **[] values();
            public static ** valueOf(java.lang.String);
     }

     # Only required if you use AsyncExecutor
     #-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     #    <init>(java.lang.Throwable);
     #}

     # 不混淆第三方jar包
     #-libraryjars libs/alicloud-android-sdk-httpdns-1.0.10.jar

     #华为
     -keep class com.huawei.hms.**{*;}
     #小米
     -keep class com.anloq.receiver.MiBroadCastReceive {*;}

     ######################### EpSoft ###########################

     -keep class com.epsoft.**{*;}
     -keep class com.itsea.cplusplus.** { *; }

     -keepclassmembers class * extends android.webkit.WebViewClient {
         public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
         public boolean *(android.webkit.WebView, java.lang.String);
     }
     -keepclassmembers class * extends android.webkit.WebViewClient {
         public void *(android.webkit.WebView, java.lang.String);
     }


     #okhttp
     -dontwarn okhttp3.**
     -keep class okhttp3.**{*;}

     #okio
     -dontwarn okio.**
     -keep class okio.**{*;}

     #okgo
     -dontwarn com.lzy.okgo.**
     -keep class com.lzy.okgo.**{*;}
     -keep class com.google.gson.**{*;}
     -keep class com.orhanobut.logger.**{*;}


     -dontwarn com.tencent.bugly.**
     -keep public class com.tencent.bugly.**{*;}

     -dontwarn com.tencent.**
     -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
     -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
     -keep class im.yixin.sdk.api.YXMessage {*;}
     -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

     #友盟
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

     ########################## end ###################################
