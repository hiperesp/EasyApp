package org.hiperesp.easyapp.core.js_bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.hiperesp.easyapp.core.js_native_caller.IntentRequestCodeConstants;
import org.hiperesp.easyapp.core.EasyApp;

class Bridge implements IntentRequestCodeConstants {

    Bridge(EasyApp easyApp, WebView webView) {
        this.easyApp = easyApp;
        this.webView = webView;
    }

    EasyApp easyApp;
    private WebView webView;

    void sendScriptToWeb(String script) {
        webView.evaluateJavascript(script, null);
    }

    private int uniqueCallbackId = 0;
    @JavascriptInterface
    public int __getCallbackId(){
        return uniqueCallbackId++;
    }
}
