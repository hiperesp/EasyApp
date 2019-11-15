package org.hiperesp.easyapp.core.js_bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import org.hiperesp.easyapp.core.EasyApp;
import org.hiperesp.easyapp.core.settings.Settings;

public class BridgeWebInterface extends Bridge {
    public BridgeWebInterface(EasyApp easyApp, WebView webView) {
        super(easyApp, webView);
    }

    private String callbackCameraResolve = "";
    private String callbackCameraReject = "";

    @JavascriptInterface
    public String __frameworkName(){
        return Settings.frameworkName;
    }
    @JavascriptInterface
    public String __frameworkVersion(){
        return Settings.frameworkVersion;
    }
    @JavascriptInterface
    public void __setStatusBarColor(String color){
        easyApp.nativeCaller.setStatusBarColor(color);
    }
    @JavascriptInterface
    public void __setNavigationBarColor(String color){
        easyApp.nativeCaller.setNavigationBarColor(color);
    }
    @JavascriptInterface
    public void __requestCameraPhoto(String callbackResolve, String callbackReject){
        callbackCameraResolve = callbackResolve;
        callbackCameraReject = callbackReject;
        easyApp.nativeCaller.requestCameraPhoto();
    }

    public void callbackCameraFunction(boolean success, String data){
        String callbackFunction;
        if(success) {
            callbackFunction = callbackCameraResolve;
        } else {
            callbackFunction = callbackCameraReject;
        }
        sendScriptToWeb("window."+Settings.easyAppNativeInterface+".__private.callback."+callbackFunction+"(\""+data+"\")");
    }
}
