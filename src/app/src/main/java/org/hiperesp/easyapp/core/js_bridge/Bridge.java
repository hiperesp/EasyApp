package org.hiperesp.easyapp.core.js_bridge;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import org.hiperesp.easyapp.core.EasyApp;
import org.hiperesp.easyapp.core.settings.Settings;

public class Bridge {

    EasyApp easyApp;
    private WebView webView;

    Bridge(EasyApp easyApp, WebView webView) {
        this.easyApp = easyApp;
        this.webView = webView;
    }

    private int uniqueCallbackId = 0;
    @JavascriptInterface
    public int __getCallbackId(){
        return uniqueCallbackId++;
    }

    public void sendScriptToWeb(String script) {
        webView.evaluateJavascript(script, null);
    }

    public void getWebAnswer(String script, ValueCallback<String> valueCallback) {
        webView.evaluateJavascript(script, valueCallback);
    }

    public void callback(String function) {
        sendScriptToWeb("window."+ Settings.EASYAPP_NATIVE_INTERFACE+".__private.callback."+function+";");
    }

}
