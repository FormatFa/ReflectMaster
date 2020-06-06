# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5                                             #指定代码压缩级别
-dontusemixedcaseclassnames                                 #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses                            #指定不忽略非公共类库
-dontpreverify                                              #不预校验，如果需要预校验，是-dontoptimize
-ignorewarnings                                             #屏蔽警告
-verbose                                                    #混淆时记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*    #优化

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.v7.** { *; }    #过滤android.support.v7  注意这里v4还是v7要看gradle里面compile了那个扩展包
-keep interface android.support.constraint.** { *; }
-keep class com.alibaba.fastjson.** {*;}    #保持第三方包fastjson不被混淆，否则会报错\
-keep	class	com.baidu.kirin.**	{	*;	}
-keep	class	com.baidu.mobstat.**	{	*;	}
-keep	class	com.baidu.bottom.**	{	*;	}
-keep class com.github.esrrhs.fakescript.** {*;}
-keep class formatfa.android.f.ScriptWindow$rf {*;}
-keep class formatfa.android.f.ScriptWindow$io {*;}
-keep class formatfa.android.f.Entry {
public void handleLoadPackage(de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackagePara );
}

