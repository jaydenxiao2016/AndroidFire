
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------


#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

# retrofit
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#rxjava
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

#FlycoTabLayout_Lib
-dontwarn com.flyco.tablayout.**
-keep public class com.flyco.tablayout.**{*;}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#butterknife
-dontwarn butterknife.internal.**
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#jcvideoplayer_lib
-dontwarn fm.jiecao.jcvideoplayer_lib.**
-keep public class fm.jiecao.jcvideoplayer_lib.**{*;}


#fab
-dontwarn com.github.clans.**
-keep public class com.github.clans.**{*;}

#updatefun
-dontwarn cn.hugeterry.updatefun.**
-keep public class cn.hugeterry.updatefun.**{*;}

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep public class com.alibaba.fastjson.**{*;}

#gson
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#easyadapter
-dontwarn com.yuyh.easyadapter.**
-keep class com.yuyh.easyadapter.** {*;}

#easyadapter
-dontwarn com.weavey.**
-keep class com.weavey.** {*;}

#zingscan
-dontwarn com.google.**
-keep class com.google.** {*;}

#zingscan
-dontwarn com.yuyh.**
-keep class com.yuyh.** {*;}

#支付宝
-dontwarn com.alipay.**
-keep class com.alipay.android.app.IAliPay{*;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.lib.ResourceMap{*;}

#-------------------------------------------------------------------------


#---------------------------------3.与js互相调用的类------------------------
#TODO 我的工程里没有。。。
#-------------------------------------------------------------------------


#---------------------------------4.反射相关的类和方法-----------------------
-dontwarn com.jaydenxiao.common.commonutils.**
-keep class com.jaydenxiao.common.commonutils.** { *; }

#----------------------------------------------------------------------------


#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
#代码混淆压缩比，在0和7之间，默认为5，一般不需要改
-optimizationpasses 5

#混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames

#指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses

#指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

#不做预校验，preverify是proguard的4个步骤之一，Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify

#有了verbose这句话，混淆后就会生成映射文件，包含有类名->混淆后类名的映射关系，然后使用printmapping指定映射文件的名称
-verbose
-printmapping proguardMapping.txt

#指定混淆时采用的算法，后面的参数是一个过滤器，这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic, !field/*, !class/merging/*

#保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

#避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

#抛出异常时保留代码行号，在第6章异常分析中我们提到过
-keepattributes SourceFile, LineNumberTable

#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
#忽略警告
-dontwarn com.lippi.recorder.utils**
#保留一个完整的包
-keep class com.lippi.recorder.utils.** {
*;
}
-keep class  com.lippi.recorder.utils.AudioRecorder{*;}

#保留了继承自Activity、Application这些类的子类，因为这些子类，都有可能被外部调用
#比如说，第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

#保留在Activity中的方法参数是view的方法，从而我们在layout里面编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void * (android.view.View);
}

#枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保留自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View {
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#对于R（资源）下的所有类及其方法，都不能被混淆
-keep class **.R$* {
    *;
}

#对于带有回调函数onXXEvent的，不能被混淆
-keepclassmembers class * {
    void * (**On*Event);
}

#webview
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

# 内部类混淆配置
-keep class com.manjay.housebox.activity.CityListActivity$*{
        <fields>;
        <methods>;
}
-keepclassmembers class com.manjay.housebox.activity.CityListActivity$*{*;}
-keep class com.manjay.housebox.map.MapActivity$*{
        <fields>;
        <methods>;
}
-keepclassmembers class com.manjay.housebox.map.MapActivity$*{*;}

#----------------------------------------------------------------------------
#忽略警告
-dontwarn com.parse.**