package org.hiperesp.easyapp.core.settings;

public class Settings {
    public static final String EASYAPP_NATIVE_INTERFACE = "EasyAppNativeInterface";
    public static String frameworkName = "EasyApp";
    public static String frameworkVersion = "0.0.30alpha";
    public static String platformName = "Android";
    public static String projectTitle;
    public static String indexPath;
    public static boolean backButtonActionToWebView = true;
    public static void init(){
        projectTitle = "EasyApp Development App";
        indexPath = "file:///android_asset/www/index.html";
    }
}
