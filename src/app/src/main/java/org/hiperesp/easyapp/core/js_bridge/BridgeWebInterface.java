package org.hiperesp.easyapp.core.js_bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import org.hiperesp.easyapp.core.EasyApp;
import org.hiperesp.easyapp.core.settings.Settings;

public class BridgeWebInterface extends BridgeInternalInterface {
    public BridgeWebInterface(EasyApp easyApp, WebView webView) { super(easyApp, webView); }

    @JavascriptInterface
    public String __frameworkName(){ return Settings.frameworkName; }
    @JavascriptInterface
    public String __frameworkVersion(){ return Settings.frameworkVersion; }
    @JavascriptInterface
    public String __getPlatform() { return Settings.platformName; }
    @JavascriptInterface
    public void __requestCameraPhoto(String resolve, String reject){ easyApp.nativeCaller.requestCameraPhoto(new Promise(resolve, reject)); }
    @JavascriptInterface
    public void __requestCameraVideo(String resolve, String reject){ easyApp.nativeCaller.requestCameraVideo(new Promise(resolve, reject)); }
    @JavascriptInterface
    public void __makeToast(String resolve, String reject, String text, boolean isShort){ easyApp.nativeCaller.makeToast(new Promise(resolve, reject), text, isShort); }
}
