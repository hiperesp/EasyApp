package org.hiperesp.easyapp.core.settings;

public class Settings {
    public static String projectTitle;
    public static String indexPath;
    public static String frameworkVersion;
    public static String frameworkName;
    public static String easyAppNativeInterface;
    public static void init(){
        projectTitle = "EasyApp Development App";
        indexPath = "file:///android_asset/public_html/index.html";
        frameworkVersion = "0.0.11alpha";
        frameworkName = "EasyApp";
        easyAppNativeInterface = "EasyAppNativeInterface";
    }
}
