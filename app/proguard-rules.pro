# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep BuildConfig fields for API configuration
-keep class com.fibank.task.BuildConfig {
    public static final java.lang.String EXCHANGE_RATE_API_KEY;
    public static final java.lang.String EXCHANGE_RATE_BASE_URL;
}

# Keep SecureApiConfig class for API configuration
-keep class com.fibank.task.data.config.SecureApiConfig {
    public static final com.fibank.task.data.config.SecureApiConfig INSTANCE;
    public java.lang.String getEXCHANGE_RATES_URL();
}

# Keep serialization classes for API responses
-keep class com.fibank.task.data.model.** { *; }

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